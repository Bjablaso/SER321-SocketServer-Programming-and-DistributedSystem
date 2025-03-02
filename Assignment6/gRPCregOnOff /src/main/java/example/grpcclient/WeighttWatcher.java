package example.grpcclient;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import service.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.util.Timestamps;

public class WeighttWatcher extends WeightTrackerGrpc.WeightTrackerImplBase {
    Map<String, Customer> Watch = new HashMap<>();

    @Override
    public void addWeight(AddWeightRequest request, StreamObserver<AddWeightResponse> responseObserver) {
        Weight newWeight = request.getWeight();
        String name = newWeight.getName();
        double weight = newWeight.getWeightNum();

        if (Watch.containsKey(name)) {

            Watch.get(name).updateCurrentWeight(weight);
            AddWeightResponse response = AddWeightResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }


        Customer customer = new Customer(name, weight);
        Watch.put(name, customer);

        AddWeightResponse response = AddWeightResponse.newBuilder()
                .setIsSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



    @Override
    public void getWeight(GetWeightRequest request, StreamObserver<GetWeightResponse> responseObserver) {
        String name = request.getName();
        LocalDateTime localDateTime;
        ZoneId zoneId;
        Instant instant;
        Timestamp timestamp;
        Weight weight;
        GetWeightResponse response;


        if(!Watch.containsKey(name)){
             response = GetWeightResponse.newBuilder()
                    .setIsSuccess(false)
                    .setError("User " + name + " weight is not being tracked ")
                     .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        Customer customer = Watch.get(name);

        List<WeightHistory> history = customer.getWeightHistory();// get customer weight history

        if(history.isEmpty()){

             localDateTime = customer.getCurrentTime();

             zoneId = ZoneId.systemDefault();
             instant = localDateTime.atZone(zoneId).toInstant();

             timestamp = Timestamps.fromMillis(instant.toEpochMilli());


             weight = Weight.newBuilder()
                    .setName(customer.getName())
                    .setWeightNum(customer.getCurrentWeight())
                    .setTimeStamp(timestamp)
                    .build();

             response = GetWeightResponse.newBuilder()
                    .setIsSuccess(false)
                    .addWeight(weight)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }


        List<Weight>  overSend = new ArrayList<>();

         localDateTime = customer.getCurrentTime();

         zoneId = ZoneId.systemDefault();
         instant = localDateTime.atZone(zoneId).toInstant();

         timestamp = Timestamps.fromMillis(instant.toEpochMilli());


         weight = Weight.newBuilder()
                .setName(customer.getName())
                .setWeightNum(customer.getCurrentWeight())
                .setTimeStamp(timestamp)
                .build();

         overSend.add(weight);

        for(WeightHistory h : history){
            localDateTime = h.getCurrentDateTime();
            zoneId = ZoneId.systemDefault();
            instant = localDateTime.atZone(zoneId).toInstant();

            timestamp = Timestamps.fromMillis(instant.toEpochMilli());

            Weight newWeight = Weight.newBuilder()
                    .setName(name)
                    .setWeightNum(h.getWeight())
                    .setTimeStamp(timestamp)
                    .build();


            overSend.add(newWeight);
        }

        response = GetWeightResponse.newBuilder()
                .setIsSuccess(true)
                .addAllWeight(overSend)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();


    }

    @Override
    public void getBMI(BMIRequest request, StreamObserver<BMIResponse> responseObserver) {
        double weight = request.getWeight();
        double height = request.getHeight();
        String units = request.getUnits();

        double bmi;


        if (units.equalsIgnoreCase("metric")) {

            bmi = weight / (height * height);
        } else if (units.equalsIgnoreCase("imperial")) {

            bmi = (weight / (height * height)) * 703;
        } else {

            BMIResponse response = BMIResponse.newBuilder()
                    .setIsSuccess(false)
                    .setError("Unrecognized units: " + units)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }


        BMIResponse response = BMIResponse.newBuilder()
                .setIsSuccess(true)
                .setBMI(bmi)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
