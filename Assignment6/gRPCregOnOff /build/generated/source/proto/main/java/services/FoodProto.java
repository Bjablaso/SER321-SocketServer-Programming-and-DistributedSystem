// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/food.proto

package services;

public final class FoodProto {
  private FoodProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_RetrieveMenuResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_RetrieveMenuResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_PlaceOrderRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_PlaceOrderRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_PlaceOrderResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_PlaceOrderResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_CheckOrderRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_CheckOrderRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_CheckOrderResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_CheckOrderResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_CustomerX_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_CustomerX_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_OrderDetails_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_OrderDetails_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_OrderItem_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_OrderItem_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_FoodItem_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_FoodItem_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023services/food.proto\022\007service\032\033google/p" +
      "rotobuf/empty.proto\"[\n\024RetrieveMenuRespo" +
      "nse\022\037\n\004menu\030\001 \003(\0132\021.service.FoodItem\022\023\n\013" +
      "isSucessful\030\002 \001(\010\022\r\n\005error\030\003 \001(\t\"y\n\021Plac" +
      "eOrderRequest\022!\n\005items\030\001 \003(\0132\022.service.O" +
      "rderItem\022\030\n\020delivery_address\030\002 \001(\t\022\025\n\rcu" +
      "stomer_name\030\003 \001(\t\022\020\n\010order_id\030\004 \001(\005\"d\n\022P" +
      "laceOrderResponse\022\021\n\tisSuccess\030\001 \001(\010\022\r\n\005" +
      "error\030\002 \001(\t\022,\n\rorder_details\030\003 \001(\0132\025.ser" +
      "vice.OrderDetails\";\n\021CheckOrderRequest\022\024" +
      "\n\014customerName\030\001 \001(\t\022\020\n\010order_id\030\002 \001(\005\"\\" +
      "\n\022CheckOrderResponse\022\021\n\tisSuccess\030\001 \001(\010\022" +
      "\r\n\005error\030\002 \001(\t\022$\n\005order\030\003 \001(\0132\025.service." +
      "OrderDetails\"G\n\tCustomerX\022\014\n\004name\030\001 \001(\t\022" +
      ",\n\rcustomerOrder\030\002 \003(\0132\025.service.OrderDe" +
      "tails\"\230\001\n\014OrderDetails\022\020\n\010order_id\030\001 \001(\005" +
      "\022$\n\006status\030\002 \001(\0162\024.service.OrderStatus\022!" +
      "\n\005items\030\003 \003(\0132\022.service.OrderItem\022\023\n\013tot" +
      "al_price\030\004 \001(\001\022\030\n\020delivery_address\030\005 \001(\t" +
      "\"-\n\tOrderItem\022\016\n\006itemId\030\001 \001(\t\022\020\n\010quantit" +
      "y\030\002 \001(\005\"b\n\010FoodItem\022\016\n\006itemId\030\001 \001(\t\022\014\n\004n" +
      "ame\030\002 \001(\t\022\023\n\013description\030\003 \001(\t\022\r\n\005price\030" +
      "\004 \001(\001\022\024\n\014foodcategory\030\005 \001(\t*7\n\013OrderStat" +
      "us\022\n\n\006PLACED\020\000\022\r\n\tDELIVERED\020\001\022\r\n\tCANCELL" +
      "ED\020\0022\343\001\n\014FoodOrdering\022E\n\014RetrieveMenu\022\026." +
      "google.protobuf.Empty\032\035.service.Retrieve" +
      "MenuResponse\022E\n\nPlaceOrder\022\032.service.Pla" +
      "ceOrderRequest\032\033.service.PlaceOrderRespo" +
      "nse\022E\n\nCheckOrder\022\032.service.CheckOrderRe" +
      "quest\032\033.service.CheckOrderResponseB\027\n\010se" +
      "rvicesB\tFoodProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.EmptyProto.getDescriptor(),
        });
    internal_static_service_RetrieveMenuResponse_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_service_RetrieveMenuResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_RetrieveMenuResponse_descriptor,
        new java.lang.String[] { "Menu", "IsSucessful", "Error", });
    internal_static_service_PlaceOrderRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_service_PlaceOrderRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_PlaceOrderRequest_descriptor,
        new java.lang.String[] { "Items", "DeliveryAddress", "CustomerName", "OrderId", });
    internal_static_service_PlaceOrderResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_service_PlaceOrderResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_PlaceOrderResponse_descriptor,
        new java.lang.String[] { "IsSuccess", "Error", "OrderDetails", });
    internal_static_service_CheckOrderRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_service_CheckOrderRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_CheckOrderRequest_descriptor,
        new java.lang.String[] { "CustomerName", "OrderId", });
    internal_static_service_CheckOrderResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_service_CheckOrderResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_CheckOrderResponse_descriptor,
        new java.lang.String[] { "IsSuccess", "Error", "Order", });
    internal_static_service_CustomerX_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_service_CustomerX_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_CustomerX_descriptor,
        new java.lang.String[] { "Name", "CustomerOrder", });
    internal_static_service_OrderDetails_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_service_OrderDetails_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_OrderDetails_descriptor,
        new java.lang.String[] { "OrderId", "Status", "Items", "TotalPrice", "DeliveryAddress", });
    internal_static_service_OrderItem_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_service_OrderItem_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_OrderItem_descriptor,
        new java.lang.String[] { "ItemId", "Quantity", });
    internal_static_service_FoodItem_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_service_FoodItem_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_FoodItem_descriptor,
        new java.lang.String[] { "ItemId", "Name", "Description", "Price", "Foodcategory", });
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
