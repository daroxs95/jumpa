plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.jumpa"
version = "1.0.1"

application {
    mainClass.set("org.jumpa.Main")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jogamp.org/deployment/maven/")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("org.bytedeco:javacv-platform:1.5.6")

    // HYPE - Replace "latest.release" with specific version if necessary
    implementation(files("libs/HYPE.jar"))

    // Processing libraries
    implementation(files("libs/processing-4.3_library/core.jar"))
    implementation("org.jogamp.gluegen:gluegen-rt:2.4.0")
    implementation("org.jogamp.gluegen:gluegen-rt:2.4.0:natives-macosx-universal")
    implementation("org.jogamp.jogl:jogl-all:2.4.0")
    implementation("org.jogamp.jogl:jogl-all:2.4.0:natives-macosx-universal")

    implementation("net.compartmental.code:minim:2.2.2")
    implementation("org.bytedeco:ffmpeg-platform:5.1.2-1.5.8")
}

tasks.test {
    useJUnitPlatform()
}
