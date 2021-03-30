import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.named<ShadowJar>("shadowJar") {
    archiveBaseName.set("shadow")
    archiveFileName.set("launcher.jar")
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "me.dgahn.app.LauncherKt"
    }
    doFirst {
        copy {
            from("../common-entity/build/resources/META-INF/armeria/grpc/service-name.dsc")
            into("build/resources/main/META-INF/armeria/grpc/")
        }
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

dependencies {
    implementation(project(":protobuf"))

    implementation(EncryptLibs.jBCrypt)
    implementation(SpringLibs.bootStarterValidation)
    implementation(platform(ArmeriaLibs.armeriaBom))
    implementation(platform(ArmeriaLibs.nettyBom))
    implementation(ArmeriaLibs.springWebfluxStarter)

    testImplementation(SpringLibs.bootStarterTest)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "SOURCEFILE"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = (0.8).toBigDecimal()
            }

            excludes = listOf(
                "*/AccountGrpcServices.kt",
                "*/AccountEntities.kt",
                "*/AccountInjectionModules.kt",
                "*/*RouteService.kt"
            )
        }
    }
}