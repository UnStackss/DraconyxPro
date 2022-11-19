package fur.kiysohi.draconyxpro.utils

import fur.kiysohi.draconyxpro.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File


object Message {
    private val language = File(Main.plugin.dataFolder.toString() + "/language/language.yml")
    private val languageconfiguration: FileConfiguration = YamlConfiguration.loadConfiguration(language)

    fun getPrefix(): String? {
        return languageconfiguration.getString("Language.Prefix")
    }

    fun reloadConfiguration(): String? {
        return languageconfiguration.getString("Language.ReloadCommand")
    }

    fun accessDenied(): String? {
        return languageconfiguration.getString("Language.AccessDenied")
    }

    fun pluginSpoofMessage(): String? {
        return languageconfiguration.getString("Language.PluginsSpoofMessage")
    }

}
