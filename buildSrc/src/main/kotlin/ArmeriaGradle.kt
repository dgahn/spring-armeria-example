object ArmeriaVersion {
    const val armeria = "1.5.0"
    const val nettyBom = "4.1.58.Final"
}

object ArmeriaLibs {
    const val armeriaBom = "com.linecorp.armeria:armeria-bom:${ArmeriaVersion.armeria}"
    const val nettyBom = "io.netty:netty-bom:${ArmeriaVersion.nettyBom}"
    const val springWebfluxStarter = "com.linecorp.armeria:armeria-spring-boot2-webflux-starter"
    const val grpc = "com.linecorp.armeria:armeria-grpc:${ArmeriaVersion.armeria}"
}