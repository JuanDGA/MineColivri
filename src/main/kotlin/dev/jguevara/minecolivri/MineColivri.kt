package dev.jguevara.minecolivri

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import dev.jguevara.minecolivri.database.MongoDBManager

class MineColivri : SuspendingJavaPlugin() {
  fun disable() {
    this.server.pluginManager.disablePlugin(this)
  }

  override suspend fun onEnableAsync() {
    var result = MongoDBManager.init(this)
    if (!result) return disable()
    result = MongoDBManager.setupConnection()
    if (!result) return disable()
  }
}
