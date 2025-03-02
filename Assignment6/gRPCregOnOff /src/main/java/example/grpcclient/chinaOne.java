package example.grpcclient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import services.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class chinaOne extends FoodOrderingGrpc.FoodOrderingImplBase {
    private List<FoodItem> manuView;
    private Map<String, FoodItem> manuArchive;
    private Map<String, CustomerX> customersOrder;
    private Map<Integer, OrderDetails> orderHistory;


    public chinaOne() {
         this.manuView =new ArrayList<>();
         this.customersOrder = new HashMap<>();
         this.manuArchive = new HashMap<>();
         this.orderHistory = new HashMap<>();
         createManue();
    }

    @Override
    public void placeOrder(PlaceOrderRequest request, StreamObserver<PlaceOrderResponse> responseObserver) {
        String name = request.getCustomerName();
        String deliveryAddress = request.getDeliveryAddress();
        List<OrderItem> orderItems = request.getItemsList();
        int orderId = request.getOrderId();

        // Check if customer exists; if not, create a new one.
        CustomerX customer;
        if (customersOrder.containsKey(name)) {
            customer = customersOrder.get(name);
            // Check if this order id already exists for the customer.
            boolean orderFound = customer.getCustomerOrderList()
                    .stream()
                    .anyMatch(o -> o.getOrderId() == orderId);
            if (orderFound) {
                PlaceOrderResponse resp = PlaceOrderResponse.newBuilder()
                        .setIsSuccess(false)
                        .setError("Order already exists")
                        .build();
                responseObserver.onNext(resp);
                responseObserver.onCompleted();
                return;
            }
        } else {
            // New customer if not found.
            customer = CustomerX.newBuilder()
                    .setName(name)
                    .build();
        }

        // Calculate total price for the order.
        double totalPrice = orderItems.stream().mapToDouble(item -> {
            FoodItem foodItem = manuArchive.get(item.getItemId());
            if (foodItem != null) {
                return foodItem.getPrice() * item.getQuantity();
            } else {
                return 0.0;
            }
        }).sum();

        // Create a new OrderDetails object.
        OrderDetails newOrder = OrderDetails.newBuilder()
                .setOrderId(orderId)
                .setStatus(OrderStatus.PLACED)
                // Add all order items to the order details.
                .addAllItems(orderItems)
                .setTotalPrice(totalPrice)
                .setDeliveryAddress(deliveryAddress)
                .build();

        // Update the customer's order list.
        CustomerX updatedCustomer = CustomerX.newBuilder(customer)
                .addCustomerOrder(newOrder)
                .build();

        // Save the updated customer back to the map.
        customersOrder.put(name, updatedCustomer);

        // Build and send a successful response.
        PlaceOrderResponse response = PlaceOrderResponse.newBuilder()
                .setIsSuccess(true)
                .setOrderDetails(newOrder)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void checkOrder(CheckOrderRequest request, StreamObserver<CheckOrderResponse> responseObserver) {
        String customerName = request.getCustomerName();
        int orderId = request.getOrderId();

        // Check if the customer exists.
        if (!customersOrder.containsKey(customerName)) {
            CheckOrderResponse response = CheckOrderResponse.newBuilder()
                    .setIsSuccess(false)
                    .setError("Customer not found")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        CustomerX customer = customersOrder.get(customerName);

        // Find the order by its ID.
        Optional<OrderDetails> matchingOrderOpt = customer.getCustomerOrderList()
                .stream()
                .filter(o -> o.getOrderId() == orderId)
                .findFirst();

        if (matchingOrderOpt.isPresent()) {
            OrderDetails orderDetails = matchingOrderOpt.get();

            // Simulate processing delay.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update order status to indicate it's ready (using DELIVERED as a proxy for "ready").
            OrderDetails updatedOrder = OrderDetails.newBuilder(orderDetails)
                    .setStatus(OrderStatus.DELIVERED) // Change to READY if your enum is updated.
                    .build();

            // Remove the order from the customer's order list.
            List<OrderDetails> updatedOrders = customer.getCustomerOrderList()
                    .stream()
                    .filter(o -> o.getOrderId() != orderId)
                    .collect(Collectors.toList());

            CustomerX updatedCustomer = CustomerX.newBuilder(customer)
                    .clearCustomerOrder()
                    .addAllCustomerOrder(updatedOrders)
                    .build();

            // Update the customer's entry in the map.
            customersOrder.put(customerName, updatedCustomer);

            // Optionally, add the updated order to an order history (not shown here).

            // Build and send a response with the updated order details.
            CheckOrderResponse response = CheckOrderResponse.newBuilder()
                    .setIsSuccess(true)
                    .setOrder(updatedOrder)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            // Order not found; return an error response.
            CheckOrderResponse response = CheckOrderResponse.newBuilder()
                    .setIsSuccess(false)
                    .setError("Order not found")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }


    @Override
    public void retrieveMenu(Empty request, StreamObserver<RetrieveMenuResponse> responseObserver) {

          if(manuView.isEmpty()){
              RetrieveMenuResponse response = RetrieveMenuResponse.newBuilder()
                              .setIsSucessful(false)
                               .setError("Could not load manu")
                               .build();
              responseObserver.onNext(response);
              responseObserver.onCompleted();
              return;
          }

          RetrieveMenuResponse response = RetrieveMenuResponse.newBuilder()
                  .setIsSucessful(true)
                  .addAllMenu(manuView)
                  .build();

          responseObserver.onNext(response);
          responseObserver.onCompleted();
    }

    public void createManue(){
        ObjectMapper mapper = new ObjectMapper();
        List<FoodItem> foodItems = new ArrayList<>();


        try {
           File manu = new File("src/main/resources/manu.json");
            System.out.println("Does file exit"+ manu.exists());


            JsonNode rootNode = mapper.readTree(manu);
            JsonNode menuArray = rootNode.get("menu");

            if (menuArray != null && menuArray.isArray()) {
                for (JsonNode categoryNode : menuArray) {
                    String categoryName = categoryNode.get("category").asText();

                    JsonNode itemsArray = categoryNode.get("items");

                    for (JsonNode itemNode : itemsArray) {

                        String idStr = itemNode.get("id").asText();
//                        String numericPart = idStr.replaceAll("\\D+", "");
//                        int id = numericPart.isEmpty() ? 0 : Integer.parseInt(numericPart);

                        String name = itemNode.get("name").asText();
                        String description = itemNode.get("description").asText();
                        double price = itemNode.get("price").asDouble();

                        FoodItem food = FoodItem.newBuilder()
                                .setItemId(idStr)
                                .setName(name)
                                .setDescription(description)
                                .setPrice(price)
                                .setFoodcategory(categoryName)
                                .build();

                       manuView.add(food);
                       manuArchive.put(idStr, food);

                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }


    }
}
