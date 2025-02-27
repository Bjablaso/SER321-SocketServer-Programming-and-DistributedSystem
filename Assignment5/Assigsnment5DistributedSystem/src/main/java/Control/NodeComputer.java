package Control;

import Entity.Node;
import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class NodeComputer implements Callable<Response> {
    private Node newNode;
    private Leader leaderComputer;
    private Request assignedTask;
    private long delay;
    private int processCounter = 0;

    public NodeComputer(Node newNode, Leader leaderComputer) {
        this.newNode = newNode;
        this.leaderComputer = leaderComputer;
    }

    @Override
    public Response call() throws Exception {
        return taskProcessor();
    }

    public Response taskProcessor() throws InterruptedException {
        Response.Builder respond = Response.newBuilder();
        List<Integer> receivingData;
        List<Integer> sendingData;

        switch (assignedTask.getOperationType()) {
            case CALCULATE:
                receivingData = assignedTask.getPortionList();
                int partialSum = calculateSum(receivingData);
                delay = assignedTask.getDelay();
                Thread.sleep(delay);
                sendingData = newNode.findDataHistory(processCounter);
                processCounter++;
                respond.setResponseType(Response.ResponseType.PARTIAL_SUM)
                        .setSenderId(newNode.getId())
                        .addAllPortion(sendingData)
                        .setPartialSum(partialSum);
                break;
            case VERIFY_SUM:
                boolean isValid;
                receivingData = assignedTask.getPortionList();
                int number = assignedTask.getPartialSum();
                Thread.sleep(delay);


                if (newNode.isIsfualty()) {
                    System.out.println("Node " + newNode.getId() + " is faulty, marking verification as failed.");
                    isValid = false;
                } else {
                    isValid = verifySum(receivingData, number);
                }

                respond.setResponseType(Response.ResponseType.VERIFY_RESULT)
                        .setSenderId(newNode.getId())
                        .setAccepted(isValid);
                break;
            case INFORM:
                int sum = assignedTask.getFinalSum();
                Thread.sleep(delay);
                newNode.finalSum(processCounter, sum);
                break;
            default:
                respond.setResponseType(Response.ResponseType.ERROR)
                        .setErrorMessage("Invalid request");
        }
        return respond.build();
    }

    public int calculateSum(List<Integer> array) {
        newNode.dataHistory(processCounter, array);
        return array.stream().mapToInt(Integer::intValue).sum();
    }

    public boolean verifySum(List<Integer> array, int sum) {
        return array.stream().mapToInt(Integer::intValue).sum() == sum;
    }

    public void assignTask(Request task) {
        this.assignedTask = task;
    }

    public Node getNewNode() {
        return newNode;
    }

    public void setFualtyNode(boolean fualtyNode) {
        this.newNode.setIsfualty(fualtyNode);
    }
}