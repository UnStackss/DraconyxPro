package fur.kiysohi.draconyxpro.events

import fur.kiysohi.draconyxpro.Main.Companion.plugin
import fur.kiysohi.draconyxpro.utils.Format
import io.netty.buffer.Unpooled
import net.minecraft.network.PacketDataSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


object F3BrandChange : Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoin(event: PlayerJoinEvent){
        val player = event.player

        if(plugin.config.getBoolean("F3Mark.Status")){
            var channels: MutableSet<String>? = null
            try {
                val playerChannels = player.javaClass.getDeclaredField("channels")
                playerChannels.isAccessible = true
                @Suppress("UNCHECKED_CAST")
                channels = playerChannels[player] as MutableSet<String>
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            if (channels != null && !channels.contains("minecraft:brand")) {
                channels.add("minecraft:brand")
                player.sendPluginMessage(
                    plugin, "minecraft:brand",
                    PacketDataSerializer(Unpooled.buffer()).a(Format.color(Format.hex(Format.color(plugin.config.getString("F3Mark.Mark") + "&r")))).array()
                )
            }
        }
    }
}
