// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/flowers.proto

package service;

public interface FlowerResOrBuilder extends
    // @@protoc_insertion_point(interface_extends:services.FlowerRes)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool isSuccess = 1;</code>
   * @return The isSuccess.
   */
  boolean getIsSuccess();

  /**
   * <pre>
   * some message telling the client all is good
   * </pre>
   *
   * <code>string message = 2;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <pre>
   * some message telling the client all is good
   * </pre>
   *
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <pre>
   * only given when isSuccess is false, should throw an error if the name of that flower is already taken. Or numbers are way too high, limit to 6. 
   * </pre>
   *
   * <code>string error = 3;</code>
   * @return The error.
   */
  java.lang.String getError();
  /**
   * <pre>
   * only given when isSuccess is false, should throw an error if the name of that flower is already taken. Or numbers are way too high, limit to 6. 
   * </pre>
   *
   * <code>string error = 3;</code>
   * @return The bytes for error.
   */
  com.google.protobuf.ByteString
      getErrorBytes();
}
