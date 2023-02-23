package fur.kiysohi.draconyxpro.events

import fur.kiysohi.draconyxpro.Main
import fur.kiysohi.draconyxpro.utils.Format
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object JoinUpdateChecker: Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (Main.updateChecker.isUpdateAvailable()) {
            if(player.isOp){
                player.sendMessage(Format.color("&7[&9DraconyX&7] &eNew update avaliable (&6${Main.updateChecker.getLatestVersion()}&e) for DraconyX downloadable jar -> &6https://spigot.kiyoshi.space"))
            }
        } else {
            if(player.isOp){
                player.sendMessage(Format.color("&7[&9DraconyX&7] &aYou are running the latest version of DraconyX"))
            }
        }
    }

}