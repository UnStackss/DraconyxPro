package fur.kiysohi.draconyxpro.events

import fur.kiysohi.draconyxpro.Main.Companion.plugin
import fur.kiysohi.draconyxpro.utils.Format
import fur.kiysohi.draconyxpro.utils.KiyoshiUpdateChecker
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object JoinUpdateChecker: Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        KiyoshiUpdateChecker(plugin, 106335).getVersion { version ->
            if (plugin.description.version == version) {
                if(player.isOp){
                    player.sendMessage(Format.color("&7[&9DraconyX7] &aYou are running the latest version of DraconyX"))
                }
            } else {
                if(player.isOp){
                    player.sendMessage(Format.color("&7[&9DraconyX7] &eNew update avaliable for DraconyX downloadable from &6https://spigot.kiyoshi.space"))
                }
            }
        }
    }

}