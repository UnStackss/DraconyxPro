package fur.kiysohi.draconyxpro.utils

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player


@Suppress("unused")
object PlaceholderUtils {
    fun replacePlaceholders(msg: String?, player: Player?): String {
        return PlaceholderAPI.setPlaceholders(player, msg!!)
    }
}
