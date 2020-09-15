plugins {
    kotlin("jvm") version "1.3.72"
}

group = "com.maplewing"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/exposed/") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("org.jetbrains.exposed:exposed:0.17.7")
    implementation("org.postgresql:postgresql:42.2.16")
    implementation("redis.clients:jedis:3.3.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.+")
}
