object LintPlugin {
    const val kotlinLinter = "org.jmailen.kotlinter"
    const val detekt = "io.gitlab.arturbosch.detekt"
}

object LintVersion {
    const val kotlinLinter = "3.4.0"
    const val detekt = "1.15.0"
}

object LintExclude {
    const val grpc = "**/**GrpcKt.kt"
}
