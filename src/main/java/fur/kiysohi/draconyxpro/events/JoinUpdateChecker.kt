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
        if (KiyoshiUpdateChecker(plugin, 106335).isUpdateAvailable()) {
            if(player.isOp){
                player.sendMessage(Format.color("&7[&9DraconyX&7] &eNew update avaliable (&6${KiyoshiUpdateChecker(plugin, 106335).getLatestVersion()}&e) for DraconyX downloadable jar -> &6https://spigot.kiyoshi.space"))
            }
        } else {
            if(player.isOp){
                player.sendMessage(Format.color("&7[&9DraconyX&7] &aYou are running the latest version of DraconyX &a(&2${KiyoshiUpdateChecker(plugin, 106335).getCurrentVersion()}&a)"))
            }
        }
    }

}