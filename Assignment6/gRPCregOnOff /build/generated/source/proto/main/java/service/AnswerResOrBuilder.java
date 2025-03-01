// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/qanda.proto

package service;

public interface AnswerResOrBuilder extends
    // @@protoc_insertion_point(interface_extends:services.AnswerRes)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * true if the request was successful
   * </pre>
   *
   * <code>bool isSuccess = 1;</code>
   * @return The isSuccess.
   */
  boolean getIsSuccess();

  /**
   * <pre>
   * Shows the current question with the provided id from req
   * </pre>
   *
   * <code>.services.Question question = 2;</code>
   * @return Whether the question field is set.
   */
  boolean hasQuestion();
  /**
   * <pre>
   * Shows the current question with the provided id from req
   * </pre>
   *
   * <code>.services.Question question = 2;</code>
   * @return The question.
   */
  service.Question getQuestion();
  /**
   * <pre>
   * Shows the current question with the provided id from req
   * </pre>
   *
   * <code>.services.Question question = 2;</code>
   */
  service.QuestionOrBuilder getQuestionOrBuilder();

  /**
   * <pre>
   *Error message if isSuccess is false, e.g. id does not exists
   * </pre>
   *
   * <code>string error = 3;</code>
   * @return The error.
   */
  java.lang.String getError();
  /**
   * <pre>
   *Error message if isSuccess is false, e.g. id does not exists
   * </pre>
   *
   * <code>string error = 3;</code>
   * @return The bytes for error.
   */
  com.google.protobuf.ByteString
      getErrorBytes();
}
