plugins {
    java
    id("org.springframework.boot") version "3.1.0-RC2"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "one.tsv"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.maven.apache.org/maven2/") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.register("dockerImage") {
    dependsOn("clean")
    dependsOn("bootJar")
    tasks["bootJar"].mustRunAfter("clean")
    doLast {
        exec {
            commandLine("docker", "build", "-t", "shortinator/app", ".")
        }
    }
}