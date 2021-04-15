import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

apply(plugin = GrpcPlugin.protobuf)

configurations.forEach {
    if (it.name.toLowerCase().contains("proto")) {
        it.attributes.attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "java-runtime"))
    }
}

dependencies {
    compileOnly(JavaLibs.javaxAnnotation)

    api(GrpcLibs.protobufJava)
    api(GrpcLibs.protobufJavaUtil)
    api(GrpcLibs.grpcKotlinStub)
    api(GrpcLibs.grpcStub)
    api(GrpcLibs.grpcProtobuf)
    api(GrpcLibs.grpcNettyShaded)
}

protobuf {
    generatedFilesBaseDir = "$projectDir/${ProtobufOption.generatedFilesBaseDir}"
    protoc {
        artifact = ProtobufOption.protocArtifact
    }
    plugins {
        id(ProtobufOption.protocGenGrpcJava) {
            artifact = ProtobufOption.protocGenGrpcJavaArtifact
        }
        id(ProtobufOption.protocGenGrpcKt) {
            artifact = ProtobufOption.protocGenGrpcKtArtifact
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id(ProtobufOption.protocGenGrpcJava)
                id(ProtobufOption.protocGenGrpcKt)
            }
            it.generateDescriptorSet = true
            it.descriptorSetOptions.includeSourceInfo = true
            it.descriptorSetOptions.includeImports = true
            it.descriptorSetOptions.path =
                "${buildDir}/resources/META-INF/armeria/grpc/service-name.dsc"
        }

    }
}

sourceSets {
    main {
        java.srcDir(GrpcSourceSet.java)
        java.srcDir(GrpcSourceSet.grpcJava)
        java.srcDir(GrpcSourceSet.grpcKt)
    }
}
