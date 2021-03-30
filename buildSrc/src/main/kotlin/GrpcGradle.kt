object GrpcPlugin {
    const val protobuf = "com.google.protobuf"
    const val grpc = "grpc"
    const val grpcKt = "grpckt"
}

object GrpcVersion {
    const val protobuf = "0.8.15"
    const val protoc = "3.15.0"
    const val grpcKotlin = "1.0.0"
    const val grpc = "1.36.0"
}

object GrpcLibs {
    const val protobufJava = "com.google.protobuf:protobuf-java:${GrpcVersion.protoc}"
    const val protobufJavaUtil = "com.google.protobuf:protobuf-java-util:${GrpcVersion.protoc}"
    const val grpcKotlinStub = "io.grpc:grpc-kotlin-stub:${GrpcVersion.grpcKotlin}"
    const val grpcStub = "io.grpc:grpc-stub:${GrpcVersion.grpc}"
    const val grpcProtobuf = "io.grpc:grpc-protobuf:${GrpcVersion.grpc}"
    const val grpcNettyShaded = "io.grpc:grpc-netty-shaded:${GrpcVersion.grpc}"
}

object ProtobufOption {
    const val generatedFilesBaseDir = "build/generated/source"
    const val protocArtifact = "com.google.protobuf:protoc:${GrpcVersion.protoc}"
    const val protocGenGrpcJava = "grpc"
    const val protocGenGrpcJavaArtifact = "io.grpc:protoc-gen-grpc-java:${GrpcVersion.grpc}"
    const val protocGenGrpcKt = "grpckt"
    const val protocGenGrpcKtArtifact = "io.grpc:protoc-gen-grpc-kotlin:${GrpcVersion.grpcKotlin}:jdk7@jar"
}

object GrpcSourceSet {
    const val java = "build/generated/source/main/java"
    const val grpcJava = "build/generated/source/main/grpc"
    const val grpcKt = "build/generated/source/main/grpckt"
}
