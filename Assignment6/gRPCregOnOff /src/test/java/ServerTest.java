import com.google.protobuf.Empty;
import example.grpcclient.Client;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.json.JSONObject;
import service.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ServerTest {

    ManagedChannel channel;
    private EchoGrpc.EchoBlockingStub blockingStub;
    private JokeGrpc.JokeBlockingStub blockingStub2;
    private FlowersGrpc.FlowersBlockingStub flowerStub;


    @org.junit.Before
    public void setUp() throws Exception {
        // assuming default port and localhost for our testing, make sure Node runs on this port
        channel = ManagedChannelBuilder.forTarget("localhost:8000").usePlaintext().build();

        blockingStub = EchoGrpc.newBlockingStub(channel);
        blockingStub2 = JokeGrpc.newBlockingStub(channel);
        flowerStub = FlowersGrpc.newBlockingStub(channel);
    }

    @org.junit.After
    public void close() throws Exception {
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);

    }


    @Test
    public void parrot() {
        // success case
        ClientRequest request = ClientRequest.newBuilder().setMessage("test").build();
        ServerResponse response = blockingStub.parrot(request);
        assertTrue(response.getIsSuccess());
        assertEquals("test", response.getMessage());

        // error cases
        request = ClientRequest.newBuilder().build();
        response = blockingStub.parrot(request);
        assertFalse(response.getIsSuccess());
        assertEquals("No message provided", response.getError());

        request = ClientRequest.newBuilder().setMessage("").build();
        response = blockingStub.parrot(request);
        assertFalse(response.getIsSuccess());
        assertEquals("No message provided", response.getError());
    }

    // For this test the server needs to be started fresh AND the list of jokes needs to be the initial list
    @Test
    public void joke() {
        // getting first joke
        JokeReq request = JokeReq.newBuilder().setNumber(1).build();
        JokeRes response = blockingStub2.getJoke(request);
        assertEquals(1, response.getJokeCount());
        assertEquals("Did you hear the rumor about butter? Well, I'm not going to spread it!", response.getJoke(0));

        // getting next 2 jokes
        request = JokeReq.newBuilder().setNumber(2).build();
        response = blockingStub2.getJoke(request);
        assertEquals(2, response.getJokeCount());
        assertEquals("What do you call someone with no body and no nose? Nobody knows.", response.getJoke(0));
        assertEquals("I don't trust stairs. They're always up to something.", response.getJoke(1));

        // getting 2 more but only one more on server
        request = JokeReq.newBuilder().setNumber(2).build();
        response = blockingStub2.getJoke(request);
        assertEquals(2, response.getJokeCount());
        assertEquals("How do you get a squirrel to like you? Act like a nut.", response.getJoke(0));
        assertEquals("I am out of jokes...", response.getJoke(1));

        // trying to get more jokes but out of jokes
        request = JokeReq.newBuilder().setNumber(2).build();
        response = blockingStub2.getJoke(request);
        assertEquals(1, response.getJokeCount());
        assertEquals("I am out of jokes...", response.getJoke(0));

        // trying to add joke without joke field
        JokeSetReq req2 = JokeSetReq.newBuilder().build();
        JokeSetRes res2 = blockingStub2.setJoke(req2);
        assertFalse(res2.getOk());

        // trying to add empty joke
        req2 = JokeSetReq.newBuilder().setJoke("").build();
        res2 = blockingStub2.setJoke(req2);
        assertFalse(res2.getOk());

        // adding a new joke (well word)
        req2 = JokeSetReq.newBuilder().setJoke("whoop").build();
        res2 = blockingStub2.setJoke(req2);
        assertTrue(res2.getOk());

        // should have the new "joke" now and return it
        request = JokeReq.newBuilder().setNumber(1).build();
        response = blockingStub2.getJoke(request);
        assertEquals(1, response.getJokeCount());
        assertEquals("whoop", response.getJoke(0));
    }

    @Test
    public void testPlantFlower() {
        // Plant a new flower "Rose"
        FlowerReq request = FlowerReq.newBuilder()
                .setName("Rose")
                .setWaterTimes(3)
                .setBloomTime(6)
                .build();
        FlowerRes response = flowerStub.plantFlower(request);
        assertTrue(response.getIsSuccess());
        assertEquals("Flower planted successfully!", response.getMessage());

        // Error case: Planting the same flower again
        response = flowerStub.plantFlower(request);
        assertFalse(response.getIsSuccess());
        assertEquals("A flower with that name already exists.", response.getError());
    }

    @Test
    public void testWaterFlower() {
        // Plant a flower "Tulip" for testing watering
        FlowerReq plantRequest = FlowerReq.newBuilder()
                .setName("Tulip")
                .setWaterTimes(2)
                .setBloomTime(4)
                .build();
        FlowerRes plantResponse = flowerStub.plantFlower(plantRequest);
        assertTrue(plantResponse.getIsSuccess());

        // Water "Tulip"
        FlowerCare waterRequest = FlowerCare.newBuilder().setName("Tulip").build();
        WaterRes waterResponse = flowerStub.waterFlower(waterRequest);
        assertTrue(waterResponse.getIsSuccess());

        // Error case: Water a non-existent flower
        waterRequest = FlowerCare.newBuilder().setName("NonExistent").build();
        waterResponse = flowerStub.waterFlower(waterRequest);
        assertFalse(waterResponse.getIsSuccess());
        assertEquals("Flower not found.", waterResponse.getError());
    }

    @Test
    public void testViewFlowers() {
        // Plant a flower "Daisy"
        FlowerReq plantRequest = FlowerReq.newBuilder()
                .setName("Daisy")
                .setWaterTimes(2)
                .setBloomTime(5)
                .build();
        FlowerRes plantResponse = flowerStub.plantFlower(plantRequest);
        assertTrue(plantResponse.getIsSuccess());

        // View flowers
        FlowerViewRes viewResponse = flowerStub.viewFlowers(Empty.newBuilder().build());
        assertTrue(viewResponse.getIsSuccess());

        // Check that "Daisy" is in the returned list
        boolean found = viewResponse.getFlowersList()
                .stream()
                .anyMatch(f -> f.getName().equals("Daisy"));
        assertTrue("Flower 'Daisy' should be present in the view.", found);
    }

    @Test
    public void testCareForFlower() {
        // Plant a flower "Lily" for testing care
        FlowerReq plantRequest = FlowerReq.newBuilder()
                .setName("Lily")
                .setWaterTimes(3)
                .setBloomTime(6)
                .build();
        FlowerRes plantResponse = flowerStub.plantFlower(plantRequest);
        assertTrue(plantResponse.getIsSuccess());

        // Water "Lily" three times to transition its state from PLANTED to BLOOMING.
        FlowerCare waterRequest = FlowerCare.newBuilder().setName("Lily").build();
        for (int i = 0; i < 3; i++) {
            WaterRes waterResponse = flowerStub.waterFlower(waterRequest);
            // In a real test, you might check the intermediate state,
            // but here we assume after 3 waterings, it becomes BLOOMING.
        }

        // Now, care for "Lily" (should succeed if the flower is blooming)
        FlowerCare careRequest = FlowerCare.newBuilder().setName("Lily").build();
        CareRes careResponse = flowerStub.careForFlower(careRequest);
        assertTrue(careResponse.getIsSuccess());
        // Optionally, check that the new bloom time increased by 1.
        // Error case: Care for a non-existent flower.
        careRequest = FlowerCare.newBuilder().setName("NonExistent").build();
        careResponse = flowerStub.careForFlower(careRequest);
        assertFalse(careResponse.getIsSuccess());
    }

}