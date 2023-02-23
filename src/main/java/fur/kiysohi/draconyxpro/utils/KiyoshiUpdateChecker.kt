package fur.kiysohi.draconyxpro.utils

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class KiyoshiUpdateChecker(private val plugin: JavaPlugin, private val resourceId: Int) {

    private val updateUrl = "https://api.spigotmc.org/legacy/update.php?resource=$resourceId"
    private var updateAvailable = false
    private var currentVersion: String? = null
    private var latestVersion: String? = null

    init {
        checkForUpdate()
    }

    private fun checkForUpdate() {
        object : BukkitRunnable() {
            override fun run() {
                try {
                    val connection = URL(updateUrl).openConnection()
                    BufferedReader(InputStreamReader(connection.getInputStream())).use { reader ->
                        latestVersion = reader.readLine()
                        if (latestVersion != null) {
                            val versionArray = latestVersion!!.split("\\.".toRegex()).toTypedArray()
                            val currentArray = plugin.description.version.split("\\.".toRegex()).toTypedArray()
                            val len = Math.min(versionArray.size, currentArray.size)
                            for (i in 0 until len) {
                                val v1 = Integer.parseInt(versionArray[i])
                                val v2 = Integer.parseInt(currentArray[i])
                                if (v1 > v2) {
                                    updateAvailable = true
                                    break
                                } else if (v1 < v2) {
                                    break
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Bukkit.getLogger().warning("[DraconyX] Failed to check for updates: " + e.message)
                }
            }
        }.runTaskAsynchronously(plugin)
    }

    fun isUpdateAvailable(): Boolean {
        return updateAvailable
    }

    fun getCurrentVersion(): String? {
        if (currentVersion == null) {
            currentVersion = plugin.description.version
        }
        return currentVersion
    }

    fun getLatestVersion(): String? {
        return latestVersion
    }

}