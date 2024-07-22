plugins {
    id("java")
}

group = "org.jumpa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(files("libs/HYPE.jar"))
    implementation(files("libs/processing-4.3_library/core.jar"))
    implementation(files("libs/processing-4.3_library/gluegen-rt.jar"))
    implementation(files("libs/processing-4.3_library/jogl-all.jar"))
    implementation(files("libs/minim/minim.jar"))
    implementation(files("libs/minim/jsminim.jar"))
    implementation(files("libs/minim/mp3spi1.9.5.jar"))
    implementation(files("libs/minim/jl1.0.1.jar"))
    implementation(files("libs/minim/tritonus_aos.jar"))
    implementation(files("libs/minim/tritonus_share.jar"))
}

tasks.test {
    useJUnitPlatform()
}