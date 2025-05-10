plugins {
  kotlin("jvm") version "2.1.20"
  id("com.gradleup.shadow") version "8.3.0"
  id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "dev.jguevara"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
    name = "spigotmc-repo"
  }
  maven("https://oss.sonatype.org/content/groups/public/") {
    name = "sonatype"
  }
}

dependencies {
  implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.22.0")
  implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.22.0")
  implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
  runServer {
    // Configure the Minecraft version for our task.
    // This is the only required configuration besides applying the plugin.
    // Your plugin's jar (or shadowJar if present) will be used automatically.
    minecraftVersion("1.21")
  }
}

val targetJavaVersion = 21
kotlin {
  jvmToolchain(targetJavaVersion)
}

tasks.build {
  dependsOn("shadowJar")
}

tasks.processResources {
  val props = mapOf("version" to version)
  inputs.properties(props)
  filteringCharset = "UTF-8"
  filesMatching("plugin.yml") {
    expand(props)
  }
}
