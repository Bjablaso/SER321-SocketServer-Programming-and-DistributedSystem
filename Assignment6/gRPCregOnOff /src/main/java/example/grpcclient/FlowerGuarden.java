package example.grpcclient;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import service.*;

import java.util.HashMap;
import java.util.Map;

public class FlowerGuarden extends FlowersGrpc.FlowersImplBase {
      Map<String, Flower> gardenSoil = new HashMap<>();


    @Override
    public void plantFlower(FlowerReq request, StreamObserver<FlowerRes> responseObserver) {
        String flowerName = request.getName();
        int waterTimes = request.getWaterTimes();
        int bloomTime = request.getBloomTime();

        if (flowerName == null || flowerName.trim().isEmpty()) {
            // If the name is empty or null, return an error
            FlowerRes response = FlowerRes.newBuilder()
                    .setIsSuccess(false)
                    .setMessage("Flower not planted.")
                    .setError("Flower name cannot be empty.")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

            return;
        }

        if (gardenSoil.containsKey(flowerName)) {
            // If it exists, return an error
            FlowerRes response = FlowerRes.newBuilder()
                    .setIsSuccess(false)
                    .setMessage("Flower not planted.")
                    .setError("A flower with that name already exists.")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        Flower newFlower = Flower.newBuilder()
                .setName(flowerName)
                .setWaterTimes(waterTimes)
                .setBloomTime(bloomTime)
                .setFlowerState(State.PLANTED)
                .build();

        gardenSoil.put(flowerName, newFlower);

        FlowerRes response = FlowerRes.newBuilder()
                .setIsSuccess(true)
                .setMessage("Flower planted successfully!")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void viewFlowers(Empty request, StreamObserver<FlowerViewRes> responseObserver) {
                FlowerViewRes.Builder responseBuilder = FlowerViewRes.newBuilder();

                if(gardenSoil.isEmpty()) {
                    responseBuilder.setIsSuccess(false)
                            .setError("No flowers found in the garden.");
                    responseObserver.onNext(responseBuilder.build());

                    responseObserver.onNext(responseBuilder.build());
                    responseObserver.onCompleted();
                    return;
                }

        responseBuilder.setIsSuccess(true);
        for (Flower flower : gardenSoil.values()) {

            responseBuilder.addFlowers(flower);
        }

        FlowerViewRes response = responseBuilder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void waterFlower(FlowerCare request, StreamObserver<WaterRes> responseObserver) {
        String flowerName = request.getName();


        Flower existing = gardenSoil.get(flowerName);

        if (existing == null) {
            // Flower not found
            WaterRes res = WaterRes.newBuilder()
                    .setIsSuccess(false)
                    .setError("Flower not found.")
                    .build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }


        if (existing.getFlowerState() == State.BLOOMING || existing.getFlowerState() == State.DEAD) {
            WaterRes res = WaterRes.newBuilder()
                    .setIsSuccess(false)
                    .setError("Flower is either blooming or dead; cannot water.")
                    .build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }

        int newWaterTimes = existing.getWaterTimes() -1;

        State newState = newWaterTimes <= 0 ? State.BLOOMING : State.PLANTED;

        Flower updatedFlower = existing.toBuilder()
                .setWaterTimes(Math.max(0, newWaterTimes)) // don’t go below zero
                .setFlowerState(newState)
                .build();


        gardenSoil.put(flowerName, existing);

        WaterRes res = WaterRes.newBuilder()
                .setIsSuccess(true)
                .setMessage("Flower watered successfully.")
                .build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void careForFlower(FlowerCare request, StreamObserver<CareRes> responseObserver) {
        String flowerName = request.getName();

        Flower existing = gardenSoil.get(flowerName);
        if (existing == null) {
            CareRes res = CareRes.newBuilder()
                    .setIsSuccess(false)
                    .setError("Error no Flower Found or Flower")
                            .build();

            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }

        if (existing.getFlowerState() == State.PLANTED) {
            CareRes res = CareRes.newBuilder()
                    .setIsSuccess(false)
                    .setError("Error : Flower not care for.")
                    .build();

            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }

        int newbloomTime = existing.getBloomTime() + 1;

        Flower newFlower = existing.toBuilder()
                .setBloomTime(newbloomTime)
                .build();

        gardenSoil.put(flowerName, existing);

        CareRes careRes = CareRes.newBuilder()
                .setIsSuccess(true)
                 .setMessage("Flower " + newFlower.getName() +
                                        " has been care For")
                 .setBloomTime(newbloomTime)
                 .build();


        responseObserver.onNext(careRes);
        responseObserver.onCompleted();

    }

}
