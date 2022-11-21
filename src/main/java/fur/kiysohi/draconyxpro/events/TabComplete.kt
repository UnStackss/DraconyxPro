package fur.kiysohi.draconyxpro.events

import fur.kiysohi.draconyxpro.Main.Companion.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandSendEvent


object TabComplete : Listener {

    @EventHandler
    fun onPlayerTab(event: PlayerCommandSendEvent) {
        val player = event.player
        val blockedTabCommands: List<String> = plugin.config.getStringList("bTabCommands")
        for(tabcmds in blockedTabCommands) {
            if(!player.hasPermission("draconyx.tab.bypass")){
                event.commands.remove(tabcmds)
            }
        }
    }
}
