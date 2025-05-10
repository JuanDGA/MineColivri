package dev.jguevara.minecolivri.database

import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import dev.jguevara.minecolivri.MineColivri
import org.bson.BsonInt64
import org.bson.Document
import org.bukkit.Bukkit

object MongoDBManager {
  private lateinit var plugin: MineColivri
  private lateinit var databaseName: String
  private lateinit var connectionString: String

  fun init(plugin: MineColivri) : Boolean {
    MongoDBManager.plugin = plugin
    connectionString = plugin.config.getString("mongodb.connection_string") ?: ""
    databaseName = plugin.config.getString("mongodb.database_name") ?: ""
    return !(connectionString.isEmpty() || databaseName.isEmpty())
  }

  suspend fun setupConnection(): Boolean {
    val client = MongoClient.create(connectionString = connectionString)
    val database = client.getDatabase(databaseName = databaseName)
    return try {
      val command = Document("ping", BsonInt64(1))
      database.runCommand(command)
      Bukkit.getLogger().info("Plugin connected to MongoDB!")
      true
    } catch (_: MongoException) {
      Bukkit.getLogger().info("Plugin failed to connect to MongoDB!")
      false
    }
  }
}