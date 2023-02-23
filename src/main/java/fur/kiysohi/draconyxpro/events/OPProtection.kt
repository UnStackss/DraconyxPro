package fur.kiysohi.draconyxpro.events

import org.bukkit.event.Listener

object OPProtection : Listener {

//    @EventHandler
//    fun onOpPersonEvent(event: PlayerCommandPreprocessEvent){
//        val operators: List<String> = plugin.config.getStringList("OpSProtection.Operators")
//        val player = event.player
//        val cmd = event.message
//        if(cmd.startsWith("/op")){
//            if(plugin.config.getBoolean("OpSProtection.Status")){
//                for(ops in operators){
//                    if(!cmd.contains(ops)){
//                        event.isCancelled = true
//                        player.sendMessage(Format.color(Message.getPrefix() + Message.accessDenied()))
//                        if(plugin.config.getBoolean("Sounds.Status")){
//                            player.playSound(player.location, Sound.valueOf(plugin.config.getString("Sounds.AccessDenied")!!), 100F, 1F)
//                        }
//                    } else {
//                        if(player.name == ops){
//                            event.isCancelled = false
//                        }
//                    }
//                }
//            }
//        }
//    }

}
