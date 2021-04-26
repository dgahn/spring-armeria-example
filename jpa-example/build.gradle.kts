import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.named<ShadowJar>("shadowJar") {
    archiveBaseName.set("shadow")
    archiveFileName.set("launcher.jar")
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "me.dgahn.LauncherKt"
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
    implementation(HibernateLibs.jacksonDataType)
    implementation(DatabaseLibs.mssql)

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.4.4")
    implementation("com.querydsl:querydsl-jpa:4.2.2")
    kapt("com.querydsl:querydsl-apt:4.2.2:jpa")

    testImplementation(SpringLibs.bootStarterTest)
    testImplementation(TestLibs.mssqlContainer)
    testImplementation(TestLibs.kotestTestContainer)
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

//idea {
//    module {
//        val kaptMain = file("${buildDir}/generated/source/kapt/main")
//        sourceDirs.add(kaptMain)
//        generatedSourceDirs.add(kaptMain)
//    }
//}