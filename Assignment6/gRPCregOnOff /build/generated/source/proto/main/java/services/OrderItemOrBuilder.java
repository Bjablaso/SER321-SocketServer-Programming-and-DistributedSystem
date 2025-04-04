// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/food.proto

package services;

public interface OrderItemOrBuilder extends
    // @@protoc_insertion_point(interface_extends:service.OrderItem)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * ID of the food item.
   * </pre>
   *
   * <code>string itemId = 1;</code>
   * @return The itemId.
   */
  java.lang.String getItemId();
  /**
   * <pre>
   * ID of the food item.
   * </pre>
   *
   * <code>string itemId = 1;</code>
   * @return The bytes for itemId.
   */
  com.google.protobuf.ByteString
      getItemIdBytes();

  /**
   * <pre>
   * Quantity ordered.
   * </pre>
   *
   * <code>int32 quantity = 2;</code>
   * @return The quantity.
   */
  int getQuantity();
}
