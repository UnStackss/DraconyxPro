package fur.kiysohi.draconyxpro.events

import fur.kiysohi.draconyxpro.Main.Companion.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandSendEvent


object TabComplete : Listener {

    @EventHandler
    fun onPlayerTab(event: PlayerCommandSendEvent) {
        val blockedTabCommands: List<String> = plugin.config.getStringList("bTabCommands")
        for(tabcmds in blockedTabCommands) {
            event.commands.remove(tabcmds)
        }
    }
}
