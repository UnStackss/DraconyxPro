package fur.kiysohi.draconyxpro.events

import fur.kiysohi.draconyxpro.Main.Companion.plugin
import fur.kiysohi.draconyxpro.utils.Format
import fur.kiysohi.draconyxpro.utils.Message
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object OPProtection : Listener {

    @EventHandler
    fun onOpPersonEvent(event: PlayerCommandPreprocessEvent){
        val operators: List<String> = plugin.config.getStringList("OpSProtection.Operators")
        val player = event.player
        val cmd = event.message
        if(cmd.startsWith("/op")){
            if(plugin.config.getBoolean("OpSProtection.Status")){
                for(ops in operators){
                    if(!cmd.contains(ops)){
                        event.isCancelled = true
                        player.sendMessage(Format.color(Message.getPrefix() + Message.accessDenied()))
                        if(plugin.config.getBoolean("Sounds.Status")){
                            player.playSound(player.location, Sound.valueOf(plugin.config.getString("Sounds.AccessDenied")!!), 100F, 1F)
                        }
                    } else {
                        if(player.name == ops){
                            event.isCancelled = false
                        }
                    }
                }
            }
        }
    }

}
