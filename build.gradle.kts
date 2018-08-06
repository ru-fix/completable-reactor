import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import java.net.URI
import ru.fix.gradle.release.plugin.ReleaseExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.artifacts.dsl.*
import org.gradle.kotlin.dsl.extra
import org.gradle.api.publication.maven.internal.action.MavenInstallAction
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.internal.authentication.DefaultBasicAuthentication
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.version
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

buildscript {
    repositories {
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.gradleReleasePlugin)
        classpath(Libs.dokkaGradlePlugin)
        classpath(Libs.kotlin_stdlib)
        classpath(Libs.kotlin_jdk8)
        classpath(Libs.kotlin_reflect)
    }
}


/**
 * Project configuration by properties and environment
 */
fun envConfig() = object : ReadOnlyProperty<Any?, String?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String? =
            if (ext.has(property.name)) {
                ext[property.name] as? String
            } else {
                System.getenv(property.name)
            }
}

val repositoryUser by envConfig()
val repositoryPassword by envConfig()
val repositoryUrl by envConfig()
val signingKeyId by envConfig()
val signingPassword by envConfig()
val signingSecretKeyRingFile by envConfig()


plugins {
    kotlin("jvm") version "${Vers.kotlin}" apply false
    signing
    `maven-publish`
}

apply {
    plugin("ru.fix.gradle.release")
}

subprojects {
    group = "ru.fix"

    apply {
        plugin("maven-publish")
        plugin("signing")
        plugin("java")
        plugin("org.jetbrains.dokka")
    }

    repositories {
        jcenter()
        mavenCentral()
        mavenLocal()
    }

    val sourcesJar by tasks.creating(Jar::class) {
        classifier = "sources"
        from("src/main/java")
        from("src/main/kotlin")
    }

    val dokkaTask by tasks.creating(DokkaTask::class){
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/dokka"
    }

    val dokkaJar by tasks.creating(Jar::class) {
        classifier = "javadoc"

        from(dokkaTask.outputDirectory)
        dependsOn(dokkaTask)
    }


    publishing {
        repositories {
            maven {
                url = uri("$repositoryUrl")
                if (url.scheme.startsWith("http", true)) {
                    credentials {
                        username = "$repositoryUser"
                        password = "$repositoryPassword"
                    }
                }
            }
        }
        (publications) {
            "maven"(MavenPublication::class) {
                from(components["java"])

                artifact(sourcesJar)
                artifact(dokkaJar)

                pom {
                    name.set("${project.group}:${project.name}")
                    description.set("jfix-stdlib provides common functionality that enhance usability of standard jvm. ")
                    url.set("https://github.com/ru-fix/jfix-stdlib")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("swarmshine")
                            name.set("Kamil Asfandiyarov")
                            url.set("https://github.com/swarmshine")
                        }
                    }
                    scm {
                        url.set("https://github.com/ru-fix/jfix-stdlib")
                        connection.set("https://github.com/ru-fix/jfix-stdlib.git")
                        developerConnection.set("https://github.com/ru-fix/jfix-stdlib.git")
                    }
                }
            }
        }
    }

    configure<SigningExtension> {

        if (!signingKeyId.isNullOrEmpty()) {
            project.ext["signing.keyId"] = signingKeyId
            project.ext["signing.password"] = signingPassword
            project.ext["signing.secretKeyRingFile"] = signingSecretKeyRingFile

            logger.info("Signing key id provided. Sign artifacts for $project.")

            isRequired = true
        } else {
            logger.warn("${project.name}: Signing key not provided. Disable signing for  $project.")
            isRequired = false
        }

        sign(publishing.publications)
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        withType<Test> {
            useJUnitPlatform()

            maxParallelForks = 10

            testLogging {
                events(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
                showStandardStreams = true
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
}