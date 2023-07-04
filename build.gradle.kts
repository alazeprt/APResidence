plugins {
    id("java")
}

group = "com.alazeprt"
version = "1.0.5"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    implementation("org.jetbrains:annotations:24.0.1")
}

tasks.test {
    useJUnitPlatform()
}