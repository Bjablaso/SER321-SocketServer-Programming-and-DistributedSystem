// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/weightTracker.proto

package service;

public interface BMIResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:services.BMIResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool isSuccess = 1;</code>
   * @return The isSuccess.
   */
  boolean getIsSuccess();

  /**
   * <code>string error = 2;</code>
   * @return The error.
   */
  java.lang.String getError();
  /**
   * <code>string error = 2;</code>
   * @return The bytes for error.
   */
  com.google.protobuf.ByteString
      getErrorBytes();

  /**
   * <code>double BMI = 3;</code>
   * @return The bMI.
   */
  double getBMI();
}
