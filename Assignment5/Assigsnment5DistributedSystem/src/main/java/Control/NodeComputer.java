package Control;

import Entity.Node;
import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NodeComputer extends Thread {
    private Node newNode;
    private Leader leaderComputer;
    private BlockingQueue<Request> taskQueue;
    private int counter;


    public NodeComputer( Node newNode,  Leader leaderComputer) {
        this.newNode = newNode;
        this.leaderComputer = leaderComputer;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.counter = 0;
    }

    @Override
    public void run() {
        while (true) {
            try{

                Request request = taskQueue.take();// take from queue

                if(request.getOperationType() == Request.OperationType.INFORM){// check if task is completed
                    newNode.finalSum(counter, request.getFinalSum());// log thread running state
                    break; // exit loop when task is complete
                }

                Response.Builder response = taskProcesser(request); //build response
                leaderComputer.receiveNodeResponse(response); // reply back to leader





            }catch (InterruptedException e){
                System.out.println(newNode.getId() + " was interrupted.");
                break;
            }

        }
    }

    public Response.Builder taskProcesser(Request req)  {
        Response.Builder respond = Response.newBuilder();
        List<Integer> recievingData;
        List<Integer> sendingData;

        switch (req.getOperationType()){
            case CALCULATE :
                   recievingData =req.getPortionList();
                    int partialSum = calculation(recievingData);

                     sendingData = newNode.finddatahistory(counter);

                    respond.setResponseType(Response.ResponseType.PARTIAL_SUM)
                            .setSenderId(newNode.getId())
                            .addAllPortion(sendingData)
                            .setPartialSum(partialSum);
                break;
            case VERIFY_SUM:
                    recievingData = req.getPortionList();
                    int nummber = req.getPartialSum();

                    if(!verifySum(recievingData, nummber )){
                        respond.setResponseType(Response.ResponseType.CONSENSUS_RESULT)
                                .setSenderId(newNode.getId())
                                .setAccepted(false)
                                .setErrorMessage("Result doesn't match");
                    }

                    respond.setResponseType(Response.ResponseType.VERIFY_RESULT)
                            .setSenderId(newNode.getId())
                            .setAccepted(true);

            default :
                System.out.println("Error Here");
        }

        return respond;
    }

    public int calculation(List<Integer> array){
        int sum = 0;


        newNode.datahistory(counter, array);
        counter++;

        if(array.size() == 1){
            sum = array.getFirst();

            return sum;
        }

        for (Integer integer : array) {
            sum += integer;
        }

        return sum;
    }

    public boolean verifySum(List<Integer> array, int sum){
        int mySum = 0;

        if(array.size() == 1){
            mySum = array.getFirst();
        }else {
            for (Integer integer : array) {
                mySum += integer;
            }
        }

        return mySum == sum;
    }

    public void assignTask(Request request) {
        try {
            taskQueue.put(request);
        } catch (InterruptedException e) {
            System.out.println(newNode.getId() + " failed to receive task.");
        }
    }


    public Node getNewNode() {
        return newNode;
    }


}
