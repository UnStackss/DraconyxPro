package fur.kiysohi.draconyxpro.utils


import com.google.common.io.ByteStreams
import fur.kiysohi.draconyxpro.Main.Companion.plugin
import org.bukkit.entity.Player


object BungeeAPI {
    fun sendPlayerToServer(player: Player, server: String?) {
        @Suppress("UnstableApiUsage") val out = ByteStreams.newDataOutput()
        try {
            out.writeUTF("Connect")
            out.writeUTF(server!!)
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
            player.sendMessage(Format.color("&cAn error occurred while trying to send you to the server, please try again later, or contact an administrator, if the problem persists, please report it to the developer (Kiyoshi#6985), thank you, and sorry for the inconvenience, have a nice day!"))
        }
    }
}
