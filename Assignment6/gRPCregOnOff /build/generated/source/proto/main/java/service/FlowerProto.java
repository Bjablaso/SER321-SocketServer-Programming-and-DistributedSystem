// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/flowers.proto

package service;

public final class FlowerProto {
  private FlowerProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_FlowerReq_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_FlowerReq_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_FlowerRes_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_FlowerRes_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_FlowerViewRes_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_FlowerViewRes_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_FlowerCare_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_FlowerCare_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_WaterRes_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_WaterRes_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_CareRes_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_CareRes_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_services_Flower_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_services_Flower_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026services/flowers.proto\022\010services\032\033goog" +
      "le/protobuf/empty.proto\"@\n\tFlowerReq\022\014\n\004" +
      "name\030\001 \001(\t\022\022\n\nwaterTimes\030\002 \001(\005\022\021\n\tbloomT" +
      "ime\030\003 \001(\005\">\n\tFlowerRes\022\021\n\tisSuccess\030\001 \001(" +
      "\010\022\017\n\007message\030\002 \001(\t\022\r\n\005error\030\003 \001(\t\"T\n\rFlo" +
      "werViewRes\022\021\n\tisSuccess\030\001 \001(\010\022!\n\007flowers" +
      "\030\002 \003(\0132\020.services.Flower\022\r\n\005error\030\003 \001(\t\"" +
      "\032\n\nFlowerCare\022\014\n\004name\030\001 \001(\t\"=\n\010WaterRes\022" +
      "\021\n\tisSuccess\030\001 \001(\010\022\017\n\007message\030\002 \001(\t\022\r\n\005e" +
      "rror\030\003 \001(\t\"O\n\007CareRes\022\021\n\tisSuccess\030\001 \001(\010" +
      "\022\017\n\007message\030\002 \001(\t\022\021\n\tbloomTime\030\003 \001(\005\022\r\n\005" +
      "error\030\004 \001(\t\"c\n\006Flower\022\014\n\004name\030\001 \001(\t\022\022\n\nw" +
      "aterTimes\030\002 \001(\005\022\021\n\tbloomTime\030\003 \001(\005\022$\n\013fl" +
      "owerState\030\004 \001(\0162\017.services.State*,\n\005Stat" +
      "e\022\013\n\007PLANTED\020\000\022\014\n\010BLOOMING\020\001\022\010\n\004DEAD\020\0022\375" +
      "\001\n\007Flowers\0229\n\013plantFlower\022\023.services.Flo" +
      "werReq\032\023.services.FlowerRes\"\000\022@\n\013viewFlo" +
      "wers\022\026.google.protobuf.Empty\032\027.services." +
      "FlowerViewRes\"\000\0229\n\013waterFlower\022\024.service" +
      "s.FlowerCare\032\022.services.WaterRes\"\000\022:\n\rca" +
      "reForFlower\022\024.services.FlowerCare\032\021.serv" +
      "ices.CareRes\"\000B\030\n\007serviceB\013FlowerProtoP\001" +
      "b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.EmptyProto.getDescriptor(),
        });
    internal_static_services_FlowerReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_services_FlowerReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_FlowerReq_descriptor,
        new java.lang.String[] { "Name", "WaterTimes", "BloomTime", });
    internal_static_services_FlowerRes_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_services_FlowerRes_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_FlowerRes_descriptor,
        new java.lang.String[] { "IsSuccess", "Message", "Error", });
    internal_static_services_FlowerViewRes_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_services_FlowerViewRes_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_FlowerViewRes_descriptor,
        new java.lang.String[] { "IsSuccess", "Flowers", "Error", });
    internal_static_services_FlowerCare_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_services_FlowerCare_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_FlowerCare_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_services_WaterRes_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_services_WaterRes_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_WaterRes_descriptor,
        new java.lang.String[] { "IsSuccess", "Message", "Error", });
    internal_static_services_CareRes_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_services_CareRes_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_CareRes_descriptor,
        new java.lang.String[] { "IsSuccess", "Message", "BloomTime", "Error", });
    internal_static_services_Flower_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_services_Flower_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_services_Flower_descriptor,
        new java.lang.String[] { "Name", "WaterTimes", "BloomTime", "FlowerState", });
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
