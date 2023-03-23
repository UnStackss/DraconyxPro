package fur.kiysohi.draconyxpro.utils

import fur.kiysohi.draconyxpro.Main
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class DraconyXPlaceHolders: PlaceholderExpansion() {
    private val version = Main.plugin.description.version
    override fun getIdentifier(): String {
        return "draconyxpro"
    }

    override fun getAuthor(): String {
        return "MyNameIsKiyoshi"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onPlaceholderRequest(player: Player, identifier: String): String? {
        return when (identifier) {
            "version" -> {
                version
            }
            "f3mark" -> {
                Main.plugin.config.getString("F3Mark.Mark")
            }
            "f3mark_formatted" -> {
                Format.color(Format.hex(Format.color(Main.plugin.config.getString("F3Mark.Mark") + "&r")))
            }
            else -> null
        }
    }
}
