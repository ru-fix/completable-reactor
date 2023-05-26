import de.marcphilipp.gradle.nexus.NexusPublishExtension
import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.properties.ReadOnlyProperty

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Libs.gradle_release_plugin)
        classpath(kotlin("gradle-plugin", version = Vers.kotlin))
        classpath(Libs.shadowPlugin)
        classpath(Libs.asciidoctor)
    }
}

plugins {
    kotlin("jvm") version Vers.kotlin apply false
    signing
    `maven-publish`
    id(Libs.nexus_publish_plugin) version "0.4.0" apply false
    id(Libs.nexus_staging_plugin) version "0.21.2"
    id(Libs.asciidoctor_plugin_id) version Vers.asciidoctor
    id(Libs.dokka_plugin_id) version Vers.dokka_plugin
}

/**
 * Project configuration by properties and environment
 */
fun envConfig() = ReadOnlyProperty<Any?, String?> { _, property ->
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

nexusStaging {
    packageGroup = "ru.fix"
    stagingProfileId = "1f0730098fd259"
    username = "$repositoryUser"
    password = "$repositoryPassword"
    numberOfRetries = 50
    delayBetweenRetriesInMillis = 3_000
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
        plugin(Libs.dokka_plugin_id)
        plugin(Libs.nexus_publish_plugin)
    }

    repositories {
        mavenCentral()
    }

    val sourcesJar by tasks.creating(Jar::class) {
        archiveClassifier.set("sources")
        from("src/main/java")
        from("src/main/kotlin")
    }

    val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
        archiveClassifier.set("javadoc")

        from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
        dependsOn(tasks.dokkaJavadoc)
    }

    val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
        archiveClassifier.set("html-doc")

        from(tasks.dokkaHtml.flatMap { it.outputDirectory })
        dependsOn(tasks.dokkaHtml)
    }

    configure<NexusPublishExtension> {
        repositories {
            sonatype {
                username.set("$repositoryUser")
                password.set("$repositoryPassword")
                useStaging.set(true)
                stagingProfileId.set("1f0730098fd259")
            }
        }
        clientTimeout.set(Duration.of(4, ChronoUnit.MINUTES))
        connectTimeout.set(Duration.of(4, ChronoUnit.MINUTES))
    }

    project.afterEvaluate {
        publishing {

            publications {
                //Internal repository setup
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

                create<MavenPublication>("maven") {
                    from(components["java"])

                    artifact(sourcesJar)
                    artifact(dokkaJavadocJar)
                    artifact(dokkaHtmlJar)

                    pom {
                        name.set("${project.group}:${project.name}")
                        description.set("https://github.com/ru-fix/${rootProject.name}")
                        url.set("https://github.com/ru-fix/${rootProject.name}")
                        licenses {
                            license {
                                name.set("MIT License")
                                url.set("https://github.com/ru-fix/${rootProject.name}/blob/master/License.md")
                            }
                        }
                        developers {
                            developer {
                                id.set("JFix Team")
                                name.set("JFix Team")
                                url.set("https://github.com/ru-fix/")
                            }
                        }
                        scm {
                            url.set("https://github.com/ru-fix/${rootProject.name}")
                            connection.set("https://github.com/ru-fix/${rootProject.name}.git")
                            developerConnection.set("https://github.com/ru-fix/${rootProject.name}.git")
                        }
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
            logger.info("${project.name}: Signing key not provided. Disable signing for  $project.")
            isRequired = false
        }
        sign(publishing.publications)
    }

    configure<JavaPluginExtension> {
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
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

tasks {
    withType<AsciidoctorTask> {
        sourceDir = project.file("asciidoc")
        resources(closureOf<CopySpec> {
            from("asciidoc")
            include("**/*.png")
        })
        doLast {
            copy {
                from(outputDir.resolve("html5"))
                into(project.file("docs"))
                include("**/*.html", "**/*.png")
            }
        }
    }
}