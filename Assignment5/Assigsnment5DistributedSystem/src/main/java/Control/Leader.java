package Control;

import Entity.Node;
import buffers.RequestProtos.*;
import buffers.ResponseProtos. *;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.*;

public class Leader implements Runnable {
    private List<NodeComputer> activeNodes;
    private Map<NodeComputer, List<Integer>> assignedTasks;
    private Map<NodeComputer, Integer> pendingResults;
    private Map<NodeComputer, Boolean> consensusResults;
    private ExecutorService threadPool;
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;
    private String currentUserid = null;
    private int divisiblePart = 3;
    private long nodeDelay = 1000;

    public Leader(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.assignedTasks = new HashMap<>();
        this.pendingResults = new HashMap<>();
        this.consensusResults = new HashMap<>();
        this.activeNodes = new ArrayList<>();
        this.threadPool = Executors.newFixedThreadPool(3); // 3 worker nodes

        try {
            this.in = clientSocket.getInputStream();
            this.out = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println("Failed to initialize I/O streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            spawnInstance();
        } catch (IOException e) {
            System.out.println("Client " + currentUserid + " could not connect to server: " + e.getMessage());
            exitAndClose(in, out, clientSocket);
        }
    }

    public void spawnInstance() throws IOException {
        try {
            while (true) {
                Request op = Request.parseDelimitedFrom(in);
                if (op == null) {
                    System.out.println("Received null request, closing connection...");
                    break;
                }

                System.out.println("Got request: " + op.toString());
                Response response;

                switch (op.getOperationType()) {
                    case CLIENTNAME:
                        response = nameRequest(op);
                        response.writeDelimitedTo(out);
                        System.out.println("Client acknowledged, waiting for data...");
                        break;

                    case DATA:
                        if (!op.hasNumbers()) {
                            response = error(0, "Try again");
                        } else {
                            startThread(op);
                            response = finalizeComputation();
                        }
                        response.writeDelimitedTo(out);
                        return;  // Exit after processing data
                    default:
                        response = error(2, "Unsupported request");
                        response.writeDelimitedTo(out);
                        break;
                }
            }
        } catch (Exception e) {
            Response error = error(0, "Unexpected server error: " + e.getMessage());
            error.writeDelimitedTo(out);
        } finally {
            System.out.println("Client ID " + currentUserid + " disconnected.");
            exitAndClose(in, out, clientSocket);
        }
    }

    private Response nameRequest(Request req) {
        Response.Builder resp = Response.newBuilder();
        if (req.getSenderId().isBlank()) {
            resp.setResponseType(Response.ResponseType.ACKNOWLEDGE)
                    .setAccepted(false)
                    .setErrorMessage("Client name missing");
        } else {
            this.currentUserid = req.getSenderId();
            resp.setResponseType(Response.ResponseType.ACKNOWLEDGE)
                    .setAccepted(true);
        }
        return resp.build();
    }

    private void startThread(Request req) {
        List<Integer> dataList = toDigitList(Integer.parseInt(req.getNumbers()));

        int size = dataList.size();
        int part1Size = size / divisiblePart;
        int part2Size = size / divisiblePart;
        int part3Size = size - (part1Size + part2Size);

        List<Integer> firstPart = new ArrayList<>(dataList.subList(0, part1Size));
        List<Integer> secondPart = new ArrayList<>(dataList.subList(part1Size, part1Size + part2Size));
        List<Integer> thirdPart = new ArrayList<>(dataList.subList(part1Size + part2Size, size));

        NodeComputer node1 = new NodeComputer(new Node("firstThread"), this);
        NodeComputer node2 = new NodeComputer(new Node("secondThread"), this);
        NodeComputer node3 = new NodeComputer(new Node("thirdThread"), this);

        activeNodes.add(node1);
        activeNodes.add(node2);
        activeNodes.add(node3);

        List<Callable<Response>> tasks = Arrays.asList(
                () -> {
                    node1.assignTask(threadTask(firstPart));
                    return node1.call();
                },
                () -> {
                    node2.assignTask(threadTask(secondPart));
                    return node2.call();
                },
                () -> {
                    node3.assignTask(threadTask(thirdPart));
                    return node3.call();
                }
        );

        try {
            List<Future<Response>> results = threadPool.invokeAll(tasks, 10, TimeUnit.SECONDS);

            for (int i = 0; i < results.size(); i++) {
                Response response = results.get(i).get();
                pendingResults.put(activeNodes.get(i), response.getPartialSum());
            }
        } catch (Exception e) {
            System.err.println("Error processing tasks: " + e.getMessage());
        }

        startVerification();
    }

    private void startVerification() {
        List<Callable<Response>> verificationTasks = Arrays.asList(
                () -> {
                    activeNodes.get(1).assignTask(verifyTask(activeNodes.get(0), pendingResults.get(activeNodes.get(0))));
                    return activeNodes.get(1).call();
                },
                () -> {
                    activeNodes.get(2).assignTask(verifyTask(activeNodes.get(1), pendingResults.get(activeNodes.get(1))));
                    return activeNodes.get(2).call();
                },
                () -> {
                    activeNodes.get(0).assignTask(verifyTask(activeNodes.get(2), pendingResults.get(activeNodes.get(2))));
                    return activeNodes.get(0).call();
                }
        );

        try {
            List<Future<Response>> results = threadPool.invokeAll(verificationTasks);
            for (int i = 0; i < results.size(); i++) {
                Response response = results.get(i).get();
                consensusResults.put(activeNodes.get(i), response.getAccepted());
            }
        } catch (Exception e) {
            System.err.println("Error processing verification tasks: " + e.getMessage());
        }
    }

    private Response finalizeComputation() {
        boolean allVerified = consensusResults.values().stream().allMatch(v -> v);
        int finalSum = pendingResults.values().stream().mapToInt(Integer::intValue).sum();

        for (NodeComputer node : activeNodes) {
            node.assignTask(broadcastFinalResult(finalSum));
        }

        return Response.newBuilder()
                .setResponseType(Response.ResponseType.FINAL_SUM)
                .setAccepted(allVerified)
                .setFinalSum(finalSum)
                .build();
    }

    private Request threadTask(List<Integer> dataPortion) {
        return Request.newBuilder()
                .setOperationType(Request.OperationType.CALCULATE)
                .setSenderId(this.currentUserid)
                .addAllPortion(dataPortion)
                .setDelay(nodeDelay)
                .build();
    }

    private Request verifyTask(NodeComputer node, int partialSum) {
        return Request.newBuilder()
                .setOperationType(Request.OperationType.VERIFY_SUM)
                .setSenderId(node.getNewNode().getId())
                .addAllPortion(node.getNewNode().findDataHistory(0))
                .setPartialSum(partialSum)
                .setDelay(nodeDelay)
                .build();
    }

    private Response error(int err, String field) {
        String message = field;
        int type = err;

        Response.Builder resp = Response.newBuilder();

        switch (type) {
            case 1:
                message += "\nError: required field missing or empty";
                break;
            case 2:
                message += "\nError: request not supported";
                break;
            default:
                message += "\nError: cannot process your request";
                type = 0;
                break;
        }

        resp.setResponseType(Response.ResponseType.ERROR)
                .setErrorType(type)
                .setErrorMessage(message);

        return resp.build();
    }


    private Request broadcastFinalResult(int finalSum) {
        return Request.newBuilder()
                .setOperationType(Request.OperationType.INFORM)
                .setPartialSum(finalSum)
                .build();
    }

    private List<Integer> toDigitList(int number) {
        List<Integer> digits = new ArrayList<>();
        while (number > 0) {
            digits.add(0, number % 10);
            number /= 10;
        }
        return digits;
    }

    private void exitAndClose(InputStream in, OutputStream out, Socket socket) {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}