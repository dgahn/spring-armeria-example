plugins {
    idea
    jacoco
    kotlin(KotlinPlugin.jvm) version KotlinVersion.kotlin
    kotlin(KotlinPlugin.spring) version KotlinVersion.kotlin
    id(SpringPlugin.boot) version SpringVersion.boot
    id(SpringPlugin.dependencyManagement) version SpringVersion.dependencyManagement
    id(KotlinPlugin.noarg) version KotlinVersion.kotlin
    id(KotlinPlugin.allOpen) version KotlinVersion.kotlin
    id(GrpcPlugin.protobuf) version GrpcVersion.protobuf
    id(LintPlugin.kotlinLinter) version LintVersion.kotlinLinter
    id(LintPlugin.detekt) version LintVersion.detekt
    id(DocPlugin.dokka) version DocVersion.dokka
    id(ShadowJarPlugin.shadowJar) version ShadowJarVersion.shadowJar
}

allprojects {
    group = ProjectGroup.dgahn
    version = ProjectVersion.dgahn

    repositories {
        maven(RepositoryUrl.osgeo)
        maven(RepositoryUrl.geotools)
        maven(RepositoryUrl.gradlePlugin)
        google()
        jcenter()
        mavenCentral()
    }

    apply(plugin=KotlinPlugin.kotlin)
    apply(plugin=KotlinPlugin.idea)
    // @Entity 클래스 바이트 코드 생성 시 인자 없는 기본 생성자 제네레이션을 위한 레퍼런스
    apply(plugin=KotlinPlugin.noarg)
    apply(plugin=KotlinPlugin.allOpen)
    // 코틀린 정적 분석 도구
    apply(plugin=LintPlugin.detekt)
    apply(plugin=LintPlugin.kotlinLinter)
    apply(plugin=DocPlugin.dokka)
    apply(plugin=DocPlugin.jacoco)
    apply(plugin=ShadowJarPlugin.shadowJar)

    jacoco {
        toolVersion = DocVersion.jacoco
    }

    tasks.jacocoTestReport {
        reports {
            html.isEnabled = true
            xml.isEnabled = true
            csv.isEnabled = false
        }
    }

    tasks.test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
        jacoco {
            enabled = true
        }
    }

    tasks {
        formatKotlinMain {
            exclude(listOf(LintExclude.grpc))
        }

        lintKotlinMain {
            exclude(listOf(LintExclude.grpc))
        }

        compileKotlin {
            dependsOn(formatKotlin)
            kotlinOptions {
                freeCompilerArgs = listOf(KotlinCompile.strict)
                jvmTarget = KotlinCompile.jvmTarget
            }
            dependsOn(processResources)
        }

        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf(KotlinCompile.strict)
                jvmTarget = KotlinCompile.jvmTarget
            }
        }
    }

    configurations.forEach {
        if (it.name.toLowerCase().contains("proto")) {
            it.attributes.attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "java-runtime"))
        }
    }

    allOpen {
        AllOpen.annotationList.forEach {
            annotation(it)
        }
    }

    noArg {
        AllOpen.annotationList.forEach {
            annotation(it)
        }
    }

    dependencies {
        implementation(LogLibs.kotlinLogging)
        implementation(LogLibs.logback)
        implementation(LogLibs.logback)

        implementation(KotlinLibs.kotlinStdlib)
        implementation(KotlinLibs.kotlinReflect)
        implementation(KotlinLibs.coroutine)

        implementation(SpringLibs.bootStarterWebflux)
        implementation(SpringLibs.bootStarterJpa)
        implementation(ArmeriaLibs.grpc)

        implementation(DatabaseLibs.mssql)

        testImplementation(TestLibs.runnerJunit5)
        testImplementation(TestLibs.assertionsCore)
        testImplementation(TestLibs.kotestProperty)
        testImplementation(TestLibs.kotestSpring)
        testImplementation(TestLibs.kotestTestContainer)
        testImplementation(TestLibs.mockk)
        testImplementation(TestLibs.mssqlContainer)

        dokkaHtmlPlugin(DocLibs.dokka)
    }

    kotlinter {
        ignoreFailures = false
        indentSize = 4
        reporters = arrayOf("checkstyle", "plain")
        experimentalRules = false
        disabledRules = arrayOf("ForbiddenComment")
    }
}
