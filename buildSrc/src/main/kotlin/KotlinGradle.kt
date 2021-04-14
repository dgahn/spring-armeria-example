object KotlinPlugin {
    const val spring = "plugin.spring"
    const val idea = "idea"
    const val kotlin = "kotlin"
    const val jvm = "jvm"
    const val noarg = "org.jetbrains.kotlin.plugin.noarg"
    const val allOpen = "org.jetbrains.kotlin.plugin.allopen"
    const val kapt = "org.jetbrains.kotlin.kapt"
}

object KotlinVersion {
    const val kotlin = "1.4.32"
    const val coroutine = "1.4.3"
}

object KotlinLibs {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinVersion.kotlin}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${KotlinVersion.coroutine}"
}

object KotlinCompile {
    const val strict = "-Xjsr305=strict"
    const val jvmTarget = "11"
}

object AllOpen {
    val annotationList =
        listOf(
            "javax.persistence.Entity",
            "javax.persistence.MappedSuperclass",
            "javax.persistence.Embeddable",
            "org.springframework.stereotype.Component"
        )
}
