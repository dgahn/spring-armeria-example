object TestVersion {
    const val kotest = "4.4.3"
    const val mockk = "1.11.0"
    const val container = "1.15.2"
}

object TestLibs {
    const val runnerJunit5 = "io.kotest:kotest-runner-junit5:${TestVersion.kotest}"
    const val assertionsCore = "io.kotest:kotest-assertions-core:${TestVersion.kotest}"
    const val kotestProperty = "io.kotest:kotest-property:${TestVersion.kotest}"
    const val kotestSpring = "io.kotest:kotest-extensions-spring-jvm:${TestVersion.kotest}"
    const val kotestTestContainer= "io.kotest:kotest-extensions-testcontainers:${TestVersion.kotest}"
    const val mssqlContainer = "org.testcontainers:mssqlserver:${TestVersion.container}"
    const val mockk = "io.mockk:mockk:${TestVersion.mockk}"
}