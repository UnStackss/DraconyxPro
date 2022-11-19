package fur.kiysohi.draconyxpro.events

import fur.kiysohi.draconyxpro.Main.Companion.plugin
import fur.kiysohi.draconyxpro.utils.Format
import fur.kiysohi.draconyxpro.utils.Message.accessDenied
import fur.kiysohi.draconyxpro.utils.Message.getPrefix
import fur.kiysohi.draconyxpro.utils.Message.pluginSpoofMessage
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

object BlockedCMDs : Listener {

    @EventHandler
    fun onPlayerExecuteCommand(event: PlayerCommandPreprocessEvent){
        val blockedCommands: List<String> = plugin.config.getStringList("bCommands")
        val player = event.player
        val command = event.message
        if(!player.hasPermission("draconyx.commands.bypass")){
            for(cmds in blockedCommands){
                if(command == cmds){
                    event.isCancelled = true
                    if(plugin.config.getBoolean("PluginsSpoof.Status")){
                        if(cmds == "/plugins" || cmds == "/pl"
                            || cmds == "/bukkit:plugins" || cmds == "/bukkit:pl" || cmds == "/pluginlist"){
                            player.sendMessage(Format.color(pluginSpoofMessage()))
                        } else {
                            player.sendMessage(Format.color(getPrefix() + accessDenied()))
                            if(plugin.config.getBoolean("Sounds.Status")){
                                player.playSound(player.location, Sound.valueOf(plugin.config.getString("Sounds.AccessDenied")!!), 100F, 1F)
                            }
                        }
                    } else {
                        player.sendMessage(Format.color(getPrefix() + accessDenied()))
                        if(plugin.config.getBoolean("Sounds.Status")){
                            player.playSound(player.location, Sound.valueOf(plugin.config.getString("Sounds.AccessDenied")!!), 100F, 1F)
                        }
                    }
                }
            }
        }
    }
}
