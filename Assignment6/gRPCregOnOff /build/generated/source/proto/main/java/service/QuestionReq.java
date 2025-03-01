// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/qanda.proto

package service;

/**
 * <pre>
 * REQUEST: Add an question
 * </pre>
 *
 * Protobuf type {@code services.QuestionReq}
 */
public final class QuestionReq extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:services.QuestionReq)
    QuestionReqOrBuilder {
private static final long serialVersionUID = 0L;
  // Use QuestionReq.newBuilder() to construct.
  private QuestionReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private QuestionReq() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new QuestionReq();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private QuestionReq(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            service.Question.Builder subBuilder = null;
            if (question_ != null) {
              subBuilder = question_.toBuilder();
            }
            question_ = input.readMessage(service.Question.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(question_);
              question_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (com.google.protobuf.UninitializedMessageException e) {
      throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return service.QandAProto.internal_static_services_QuestionReq_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return service.QandAProto.internal_static_services_QuestionReq_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            service.QuestionReq.class, service.QuestionReq.Builder.class);
  }

  public static final int QUESTION_FIELD_NUMBER = 1;
  private service.Question question_;
  /**
   * <pre>
   * Question object which will not include an answer yet
   * </pre>
   *
   * <code>.services.Question question = 1;</code>
   * @return Whether the question field is set.
   */
  @java.lang.Override
  public boolean hasQuestion() {
    return question_ != null;
  }
  /**
   * <pre>
   * Question object which will not include an answer yet
   * </pre>
   *
   * <code>.services.Question question = 1;</code>
   * @return The question.
   */
  @java.lang.Override
  public service.Question getQuestion() {
    return question_ == null ? service.Question.getDefaultInstance() : question_;
  }
  /**
   * <pre>
   * Question object which will not include an answer yet
   * </pre>
   *
   * <code>.services.Question question = 1;</code>
   */
  @java.lang.Override
  public service.QuestionOrBuilder getQuestionOrBuilder() {
    return getQuestion();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (question_ != null) {
      output.writeMessage(1, getQuestion());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (question_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getQuestion());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof service.QuestionReq)) {
      return super.equals(obj);
    }
    service.QuestionReq other = (service.QuestionReq) obj;

    if (hasQuestion() != other.hasQuestion()) return false;
    if (hasQuestion()) {
      if (!getQuestion()
          .equals(other.getQuestion())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasQuestion()) {
      hash = (37 * hash) + QUESTION_FIELD_NUMBER;
      hash = (53 * hash) + getQuestion().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static service.QuestionReq parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.QuestionReq parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.QuestionReq parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.QuestionReq parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.QuestionReq parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.QuestionReq parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.QuestionReq parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static service.QuestionReq parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static service.QuestionReq parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static service.QuestionReq parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static service.QuestionReq parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static service.QuestionReq parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(service.QuestionReq prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * REQUEST: Add an question
   * </pre>
   *
   * Protobuf type {@code services.QuestionReq}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:services.QuestionReq)
      service.QuestionReqOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return service.QandAProto.internal_static_services_QuestionReq_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return service.QandAProto.internal_static_services_QuestionReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              service.QuestionReq.class, service.QuestionReq.Builder.class);
    }

    // Construct using service.QuestionReq.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (questionBuilder_ == null) {
        question_ = null;
      } else {
        question_ = null;
        questionBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return service.QandAProto.internal_static_services_QuestionReq_descriptor;
    }

    @java.lang.Override
    public service.QuestionReq getDefaultInstanceForType() {
      return service.QuestionReq.getDefaultInstance();
    }

    @java.lang.Override
    public service.QuestionReq build() {
      service.QuestionReq result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public service.QuestionReq buildPartial() {
      service.QuestionReq result = new service.QuestionReq(this);
      if (questionBuilder_ == null) {
        result.question_ = question_;
      } else {
        result.question_ = questionBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof service.QuestionReq) {
        return mergeFrom((service.QuestionReq)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(service.QuestionReq other) {
      if (other == service.QuestionReq.getDefaultInstance()) return this;
      if (other.hasQuestion()) {
        mergeQuestion(other.getQuestion());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      service.QuestionReq parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (service.QuestionReq) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private service.Question question_;
    private com.google.protobuf.SingleFieldBuilderV3<
        service.Question, service.Question.Builder, service.QuestionOrBuilder> questionBuilder_;
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     * @return Whether the question field is set.
     */
    public boolean hasQuestion() {
      return questionBuilder_ != null || question_ != null;
    }
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     * @return The question.
     */
    public service.Question getQuestion() {
      if (questionBuilder_ == null) {
        return question_ == null ? service.Question.getDefaultInstance() : question_;
      } else {
        return questionBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     */
    public Builder setQuestion(service.Question value) {
      if (questionBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        question_ = value;
        onChanged();
      } else {
        questionBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     */
    public Builder setQuestion(
        service.Question.Builder builderForValue) {
      if (questionBuilder_ == null) {
        question_ = builderForValue.build();
        onChanged();
      } else {
        questionBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     */
    public Builder mergeQuestion(service.Question value) {
      if (questionBuilder_ == null) {
        if (question_ != null) {
          question_ =
            service.Question.newBuilder(question_).mergeFrom(value).buildPartial();
        } else {
          question_ = value;
        }
        onChanged();
      } else {
        questionBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     */
    public Builder clearQuestion() {
      if (questionBuilder_ == null) {
        question_ = null;
        onChanged();
      } else {
        question_ = null;
        questionBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     */
    public service.Question.Builder getQuestionBuilder() {
      
      onChanged();
      return getQuestionFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     */
    public service.QuestionOrBuilder getQuestionOrBuilder() {
      if (questionBuilder_ != null) {
        return questionBuilder_.getMessageOrBuilder();
      } else {
        return question_ == null ?
            service.Question.getDefaultInstance() : question_;
      }
    }
    /**
     * <pre>
     * Question object which will not include an answer yet
     * </pre>
     *
     * <code>.services.Question question = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        service.Question, service.Question.Builder, service.QuestionOrBuilder> 
        getQuestionFieldBuilder() {
      if (questionBuilder_ == null) {
        questionBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            service.Question, service.Question.Builder, service.QuestionOrBuilder>(
                getQuestion(),
                getParentForChildren(),
                isClean());
        question_ = null;
      }
      return questionBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:services.QuestionReq)
  }

  // @@protoc_insertion_point(class_scope:services.QuestionReq)
  private static final service.QuestionReq DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new service.QuestionReq();
  }

  public static service.QuestionReq getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<QuestionReq>
      PARSER = new com.google.protobuf.AbstractParser<QuestionReq>() {
    @java.lang.Override
    public QuestionReq parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new QuestionReq(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<QuestionReq> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<QuestionReq> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public service.QuestionReq getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

