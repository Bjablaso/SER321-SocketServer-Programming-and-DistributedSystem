package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.*;


class EchoImpl extends EchoGrpc.EchoImplBase {
    

    @Override
    public void parrot(ClientRequest req, StreamObserver<ServerResponse> responseObserver) {

        System.out.println("Received from client: " + req.getMessage());
        ServerResponse.Builder response = ServerResponse.newBuilder();
        if (req.getMessage().isEmpty()) {
            response.setIsSuccess(false).setError("No message provided");
        } else {
            response.setIsSuccess(true).setMessage(req.getMessage());
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }     
}