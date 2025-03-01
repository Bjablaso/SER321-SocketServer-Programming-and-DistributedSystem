package services;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/food.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FoodOrderingGrpc {

  private FoodOrderingGrpc() {}

  public static final String SERVICE_NAME = "service.FoodOrdering";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      services.RetrieveMenuResponse> getRetrieveMenuMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RetrieveMenu",
      requestType = com.google.protobuf.Empty.class,
      responseType = services.RetrieveMenuResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      services.RetrieveMenuResponse> getRetrieveMenuMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, services.RetrieveMenuResponse> getRetrieveMenuMethod;
    if ((getRetrieveMenuMethod = FoodOrderingGrpc.getRetrieveMenuMethod) == null) {
      synchronized (FoodOrderingGrpc.class) {
        if ((getRetrieveMenuMethod = FoodOrderingGrpc.getRetrieveMenuMethod) == null) {
          FoodOrderingGrpc.getRetrieveMenuMethod = getRetrieveMenuMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, services.RetrieveMenuResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RetrieveMenu"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  services.RetrieveMenuResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FoodOrderingMethodDescriptorSupplier("RetrieveMenu"))
              .build();
        }
      }
    }
    return getRetrieveMenuMethod;
  }

  private static volatile io.grpc.MethodDescriptor<services.PlaceOrderRequest,
      services.PlaceOrderResponse> getPlaceOrderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PlaceOrder",
      requestType = services.PlaceOrderRequest.class,
      responseType = services.PlaceOrderResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<services.PlaceOrderRequest,
      services.PlaceOrderResponse> getPlaceOrderMethod() {
    io.grpc.MethodDescriptor<services.PlaceOrderRequest, services.PlaceOrderResponse> getPlaceOrderMethod;
    if ((getPlaceOrderMethod = FoodOrderingGrpc.getPlaceOrderMethod) == null) {
      synchronized (FoodOrderingGrpc.class) {
        if ((getPlaceOrderMethod = FoodOrderingGrpc.getPlaceOrderMethod) == null) {
          FoodOrderingGrpc.getPlaceOrderMethod = getPlaceOrderMethod =
              io.grpc.MethodDescriptor.<services.PlaceOrderRequest, services.PlaceOrderResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PlaceOrder"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  services.PlaceOrderRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  services.PlaceOrderResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FoodOrderingMethodDescriptorSupplier("PlaceOrder"))
              .build();
        }
      }
    }
    return getPlaceOrderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<services.CheckOrderRequest,
      services.CheckOrderResponse> getCheckOrderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckOrder",
      requestType = services.CheckOrderRequest.class,
      responseType = services.CheckOrderResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<services.CheckOrderRequest,
      services.CheckOrderResponse> getCheckOrderMethod() {
    io.grpc.MethodDescriptor<services.CheckOrderRequest, services.CheckOrderResponse> getCheckOrderMethod;
    if ((getCheckOrderMethod = FoodOrderingGrpc.getCheckOrderMethod) == null) {
      synchronized (FoodOrderingGrpc.class) {
        if ((getCheckOrderMethod = FoodOrderingGrpc.getCheckOrderMethod) == null) {
          FoodOrderingGrpc.getCheckOrderMethod = getCheckOrderMethod =
              io.grpc.MethodDescriptor.<services.CheckOrderRequest, services.CheckOrderResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckOrder"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  services.CheckOrderRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  services.CheckOrderResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FoodOrderingMethodDescriptorSupplier("CheckOrder"))
              .build();
        }
      }
    }
    return getCheckOrderMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FoodOrderingStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FoodOrderingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FoodOrderingStub>() {
        @java.lang.Override
        public FoodOrderingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FoodOrderingStub(channel, callOptions);
        }
      };
    return FoodOrderingStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FoodOrderingBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FoodOrderingBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FoodOrderingBlockingStub>() {
        @java.lang.Override
        public FoodOrderingBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FoodOrderingBlockingStub(channel, callOptions);
        }
      };
    return FoodOrderingBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FoodOrderingFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FoodOrderingFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FoodOrderingFutureStub>() {
        @java.lang.Override
        public FoodOrderingFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FoodOrderingFutureStub(channel, callOptions);
        }
      };
    return FoodOrderingFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FoodOrderingImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Retrieve the menu.
     * </pre>
     */
    public void retrieveMenu(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<services.RetrieveMenuResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRetrieveMenuMethod(), responseObserver);
    }

    /**
     * <pre>
     * Place an order.
     * </pre>
     */
    public void placeOrder(services.PlaceOrderRequest request,
        io.grpc.stub.StreamObserver<services.PlaceOrderResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPlaceOrderMethod(), responseObserver);
    }

    /**
     * <pre>
     * Check order status or history.
     * </pre>
     */
    public void checkOrder(services.CheckOrderRequest request,
        io.grpc.stub.StreamObserver<services.CheckOrderResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckOrderMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRetrieveMenuMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                services.RetrieveMenuResponse>(
                  this, METHODID_RETRIEVE_MENU)))
          .addMethod(
            getPlaceOrderMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                services.PlaceOrderRequest,
                services.PlaceOrderResponse>(
                  this, METHODID_PLACE_ORDER)))
          .addMethod(
            getCheckOrderMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                services.CheckOrderRequest,
                services.CheckOrderResponse>(
                  this, METHODID_CHECK_ORDER)))
          .build();
    }
  }

  /**
   */
  public static final class FoodOrderingStub extends io.grpc.stub.AbstractAsyncStub<FoodOrderingStub> {
    private FoodOrderingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FoodOrderingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FoodOrderingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Retrieve the menu.
     * </pre>
     */
    public void retrieveMenu(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<services.RetrieveMenuResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRetrieveMenuMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Place an order.
     * </pre>
     */
    public void placeOrder(services.PlaceOrderRequest request,
        io.grpc.stub.StreamObserver<services.PlaceOrderResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPlaceOrderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Check order status or history.
     * </pre>
     */
    public void checkOrder(services.CheckOrderRequest request,
        io.grpc.stub.StreamObserver<services.CheckOrderResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckOrderMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FoodOrderingBlockingStub extends io.grpc.stub.AbstractBlockingStub<FoodOrderingBlockingStub> {
    private FoodOrderingBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FoodOrderingBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FoodOrderingBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Retrieve the menu.
     * </pre>
     */
    public services.RetrieveMenuResponse retrieveMenu(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRetrieveMenuMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Place an order.
     * </pre>
     */
    public services.PlaceOrderResponse placeOrder(services.PlaceOrderRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPlaceOrderMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Check order status or history.
     * </pre>
     */
    public services.CheckOrderResponse checkOrder(services.CheckOrderRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckOrderMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FoodOrderingFutureStub extends io.grpc.stub.AbstractFutureStub<FoodOrderingFutureStub> {
    private FoodOrderingFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FoodOrderingFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FoodOrderingFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Retrieve the menu.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<services.RetrieveMenuResponse> retrieveMenu(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRetrieveMenuMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Place an order.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<services.PlaceOrderResponse> placeOrder(
        services.PlaceOrderRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPlaceOrderMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Check order status or history.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<services.CheckOrderResponse> checkOrder(
        services.CheckOrderRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckOrderMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RETRIEVE_MENU = 0;
  private static final int METHODID_PLACE_ORDER = 1;
  private static final int METHODID_CHECK_ORDER = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FoodOrderingImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FoodOrderingImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RETRIEVE_MENU:
          serviceImpl.retrieveMenu((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<services.RetrieveMenuResponse>) responseObserver);
          break;
        case METHODID_PLACE_ORDER:
          serviceImpl.placeOrder((services.PlaceOrderRequest) request,
              (io.grpc.stub.StreamObserver<services.PlaceOrderResponse>) responseObserver);
          break;
        case METHODID_CHECK_ORDER:
          serviceImpl.checkOrder((services.CheckOrderRequest) request,
              (io.grpc.stub.StreamObserver<services.CheckOrderResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FoodOrderingBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FoodOrderingBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return services.FoodProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FoodOrdering");
    }
  }

  private static final class FoodOrderingFileDescriptorSupplier
      extends FoodOrderingBaseDescriptorSupplier {
    FoodOrderingFileDescriptorSupplier() {}
  }

  private static final class FoodOrderingMethodDescriptorSupplier
      extends FoodOrderingBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FoodOrderingMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FoodOrderingGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FoodOrderingFileDescriptorSupplier())
              .addMethod(getRetrieveMenuMethod())
              .addMethod(getPlaceOrderMethod())
              .addMethod(getCheckOrderMethod())
              .build();
        }
      }
    }
    return result;
  }
}
