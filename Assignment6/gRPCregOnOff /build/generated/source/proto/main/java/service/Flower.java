// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/flowers.proto

package service;

/**
 * <pre>
 * Entity: Flower
 * </pre>
 *
 * Protobuf type {@code services.Flower}
 */
public final class Flower extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:services.Flower)
    FlowerOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Flower.newBuilder() to construct.
  private Flower(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Flower() {
    name_ = "";
    flowerState_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Flower();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Flower(
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
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
            break;
          }
          case 16: {

            waterTimes_ = input.readInt32();
            break;
          }
          case 24: {

            bloomTime_ = input.readInt32();
            break;
          }
          case 32: {
            int rawValue = input.readEnum();

            flowerState_ = rawValue;
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
    return service.FlowerProto.internal_static_services_Flower_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return service.FlowerProto.internal_static_services_Flower_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            service.Flower.class, service.Flower.Builder.class);
  }

  public static final int NAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object name_;
  /**
   * <code>string name = 1;</code>
   * @return The name.
   */
  @java.lang.Override
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int WATERTIMES_FIELD_NUMBER = 2;
  private int waterTimes_;
  /**
   * <pre>
   * how many times the flower needs to be watered before blooming
   * </pre>
   *
   * <code>int32 waterTimes = 2;</code>
   * @return The waterTimes.
   */
  @java.lang.Override
  public int getWaterTimes() {
    return waterTimes_;
  }

  public static final int BLOOMTIME_FIELD_NUMBER = 3;
  private int bloomTime_;
  /**
   * <pre>
   * shows how many hours the flower will bloom (will go up with care requests, will go down as time goes on)
   * </pre>
   *
   * <code>int32 bloomTime = 3;</code>
   * @return The bloomTime.
   */
  @java.lang.Override
  public int getBloomTime() {
    return bloomTime_;
  }

  public static final int FLOWERSTATE_FIELD_NUMBER = 4;
  private int flowerState_;
  /**
   * <code>.services.State flowerState = 4;</code>
   * @return The enum numeric value on the wire for flowerState.
   */
  @java.lang.Override public int getFlowerStateValue() {
    return flowerState_;
  }
  /**
   * <code>.services.State flowerState = 4;</code>
   * @return The flowerState.
   */
  @java.lang.Override public service.State getFlowerState() {
    @SuppressWarnings("deprecation")
    service.State result = service.State.valueOf(flowerState_);
    return result == null ? service.State.UNRECOGNIZED : result;
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
    }
    if (waterTimes_ != 0) {
      output.writeInt32(2, waterTimes_);
    }
    if (bloomTime_ != 0) {
      output.writeInt32(3, bloomTime_);
    }
    if (flowerState_ != service.State.PLANTED.getNumber()) {
      output.writeEnum(4, flowerState_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
    }
    if (waterTimes_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, waterTimes_);
    }
    if (bloomTime_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, bloomTime_);
    }
    if (flowerState_ != service.State.PLANTED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, flowerState_);
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
    if (!(obj instanceof service.Flower)) {
      return super.equals(obj);
    }
    service.Flower other = (service.Flower) obj;

    if (!getName()
        .equals(other.getName())) return false;
    if (getWaterTimes()
        != other.getWaterTimes()) return false;
    if (getBloomTime()
        != other.getBloomTime()) return false;
    if (flowerState_ != other.flowerState_) return false;
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
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    hash = (37 * hash) + WATERTIMES_FIELD_NUMBER;
    hash = (53 * hash) + getWaterTimes();
    hash = (37 * hash) + BLOOMTIME_FIELD_NUMBER;
    hash = (53 * hash) + getBloomTime();
    hash = (37 * hash) + FLOWERSTATE_FIELD_NUMBER;
    hash = (53 * hash) + flowerState_;
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static service.Flower parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.Flower parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.Flower parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.Flower parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.Flower parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static service.Flower parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static service.Flower parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static service.Flower parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static service.Flower parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static service.Flower parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static service.Flower parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static service.Flower parseFrom(
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
  public static Builder newBuilder(service.Flower prototype) {
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
   * Entity: Flower
   * </pre>
   *
   * Protobuf type {@code services.Flower}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:services.Flower)
      service.FlowerOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return service.FlowerProto.internal_static_services_Flower_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return service.FlowerProto.internal_static_services_Flower_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              service.Flower.class, service.Flower.Builder.class);
    }

    // Construct using service.Flower.newBuilder()
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
      name_ = "";

      waterTimes_ = 0;

      bloomTime_ = 0;

      flowerState_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return service.FlowerProto.internal_static_services_Flower_descriptor;
    }

    @java.lang.Override
    public service.Flower getDefaultInstanceForType() {
      return service.Flower.getDefaultInstance();
    }

    @java.lang.Override
    public service.Flower build() {
      service.Flower result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public service.Flower buildPartial() {
      service.Flower result = new service.Flower(this);
      result.name_ = name_;
      result.waterTimes_ = waterTimes_;
      result.bloomTime_ = bloomTime_;
      result.flowerState_ = flowerState_;
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
      if (other instanceof service.Flower) {
        return mergeFrom((service.Flower)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(service.Flower other) {
      if (other == service.Flower.getDefaultInstance()) return this;
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      if (other.getWaterTimes() != 0) {
        setWaterTimes(other.getWaterTimes());
      }
      if (other.getBloomTime() != 0) {
        setBloomTime(other.getBloomTime());
      }
      if (other.flowerState_ != 0) {
        setFlowerStateValue(other.getFlowerStateValue());
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
      service.Flower parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (service.Flower) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object name_ = "";
    /**
     * <code>string name = 1;</code>
     * @return The name.
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string name = 1;</code>
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string name = 1;</code>
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string name = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <code>string name = 1;</code>
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
    }

    private int waterTimes_ ;
    /**
     * <pre>
     * how many times the flower needs to be watered before blooming
     * </pre>
     *
     * <code>int32 waterTimes = 2;</code>
     * @return The waterTimes.
     */
    @java.lang.Override
    public int getWaterTimes() {
      return waterTimes_;
    }
    /**
     * <pre>
     * how many times the flower needs to be watered before blooming
     * </pre>
     *
     * <code>int32 waterTimes = 2;</code>
     * @param value The waterTimes to set.
     * @return This builder for chaining.
     */
    public Builder setWaterTimes(int value) {
      
      waterTimes_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * how many times the flower needs to be watered before blooming
     * </pre>
     *
     * <code>int32 waterTimes = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearWaterTimes() {
      
      waterTimes_ = 0;
      onChanged();
      return this;
    }

    private int bloomTime_ ;
    /**
     * <pre>
     * shows how many hours the flower will bloom (will go up with care requests, will go down as time goes on)
     * </pre>
     *
     * <code>int32 bloomTime = 3;</code>
     * @return The bloomTime.
     */
    @java.lang.Override
    public int getBloomTime() {
      return bloomTime_;
    }
    /**
     * <pre>
     * shows how many hours the flower will bloom (will go up with care requests, will go down as time goes on)
     * </pre>
     *
     * <code>int32 bloomTime = 3;</code>
     * @param value The bloomTime to set.
     * @return This builder for chaining.
     */
    public Builder setBloomTime(int value) {
      
      bloomTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * shows how many hours the flower will bloom (will go up with care requests, will go down as time goes on)
     * </pre>
     *
     * <code>int32 bloomTime = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearBloomTime() {
      
      bloomTime_ = 0;
      onChanged();
      return this;
    }

    private int flowerState_ = 0;
    /**
     * <code>.services.State flowerState = 4;</code>
     * @return The enum numeric value on the wire for flowerState.
     */
    @java.lang.Override public int getFlowerStateValue() {
      return flowerState_;
    }
    /**
     * <code>.services.State flowerState = 4;</code>
     * @param value The enum numeric value on the wire for flowerState to set.
     * @return This builder for chaining.
     */
    public Builder setFlowerStateValue(int value) {
      
      flowerState_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.services.State flowerState = 4;</code>
     * @return The flowerState.
     */
    @java.lang.Override
    public service.State getFlowerState() {
      @SuppressWarnings("deprecation")
      service.State result = service.State.valueOf(flowerState_);
      return result == null ? service.State.UNRECOGNIZED : result;
    }
    /**
     * <code>.services.State flowerState = 4;</code>
     * @param value The flowerState to set.
     * @return This builder for chaining.
     */
    public Builder setFlowerState(service.State value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      flowerState_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.services.State flowerState = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearFlowerState() {
      
      flowerState_ = 0;
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:services.Flower)
  }

  // @@protoc_insertion_point(class_scope:services.Flower)
  private static final service.Flower DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new service.Flower();
  }

  public static service.Flower getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Flower>
      PARSER = new com.google.protobuf.AbstractParser<Flower>() {
    @java.lang.Override
    public Flower parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Flower(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Flower> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Flower> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public service.Flower getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

