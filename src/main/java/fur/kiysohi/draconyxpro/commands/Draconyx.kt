package fur.kiysohi.draconyxpro.commands

import fur.kiysohi.draconyxpro.Main
import fur.kiysohi.draconyxpro.Main.Companion.plugin
import fur.kiysohi.draconyxpro.utils.Format
import fur.kiysohi.draconyxpro.utils.Message.accessDenied
import fur.kiysohi.draconyxpro.utils.Message.getPrefix
import fur.kiysohi.draconyxpro.utils.Message.reloadConfiguration
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.*


object DraconyX : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val version = plugin.description.version
        val authors = plugin.description.authors
        val apiversion = plugin.description.apiVersion
        val description = plugin.description.description
        val status = plugin.isEnabled
        val soft = plugin.description.softDepend
        if(sender !is Player){
            sender.sendMessage(Format.color(getPrefix() + "&cConsole Commands Blcoked For Safety!"))
            return true
        }

        @Suppress("USELESS_CAST") val player = sender as Player

        if(args.isNotEmpty()){
            when (args[0]){
                "reload" -> {
                    if(player.hasPermission("draconyx.admin.reload")){
                        plugin.reloadConfig()
                        player.sendMessage(Format.color(getPrefix() + reloadConfiguration()))
                        player.playSound(player.location, Sound.ITEM_TRIDENT_RETURN, 100f ,1f)
                    } else {
                        player.sendMessage(Format.color(getPrefix() + accessDenied()))
                        player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 100f ,1f)
                    }
                }
                "updatecheck" -> {
                    if(player.hasPermission("draconyx.admin.update")){
                        if (Main.updateChecker.isUpdateAvailable()) {
                            player.sendMessage(Format.color("&7[&9DraconyX&7] &eNew update avaliable (&6${Main.updateChecker.getLatestVersion()}&e) for DraconyX downloadable jar -> &6https://spigot.kiyoshi.space"))
                        } else {
                            player.sendMessage(Format.color("&7[&9DraconyX&7] &aYou are running the latest version of DraconyX"))
                        }
                    } else {
                        player.sendMessage(Format.color(getPrefix() + accessDenied()))
                        player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 100f ,1f)
                    }
                }
            }
        } else {
            player.sendMessage(Format.color("&9&l────────[DraconyX PRO]────────"))
            player.sendMessage(Format.color("&9Running DraconyX Version &7(&a${version}&7)"))
            player.sendMessage(Format.color("&9Plugin Status &7(&a${status}&7)").replace("true", "Enabled"))
            player.sendMessage(Format.color("&9Description &7(&a${description}&7)"))
            player.sendMessage(Format.color("&9Created By &7${authors}").replace("[", "").replace("]", ""))
            player.sendMessage(Format.color("&9GitHub &ahttps://github.kiyoshi.space"))
            player.sendMessage(Format.color("&9Discord &ahttps://discord.kiyoshi.space"))
            player.sendMessage(Format.color("&9Spigot &ahttps://spigot.kiyoshi.space"))
            player.sendMessage(Format.color("&9ApiVersion &7(&a${apiversion}&7)"))
            player.sendMessage(Format.color("&9Soft-Depends &7${soft}"))
            player.sendMessage(Format.color("&9&l─────────────────────────"))
            player.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 100F, 1F)
        }
        return true
    }


    private fun a(s: MutableList<String>, arg: String, test: String) {
        if (test.startsWith(arg.lowercase(Locale.getDefault()))) s.add(test)
    }


    override fun onTabComplete(
        arg0: CommandSender,
        arg1: Command,
        arg2: String,
        args: Array<String?>
    ): List<String>? {
        val s: List<String> = ArrayList()
        if (arg1.name == "draconyx") {
            if (args.size == 1) {
                a(s as MutableList<String>, args[0]!!, "I Like Kiyoshi (Kiyoshi#6985) On Discord ;3")
                a(s, args[0]!!, "reload")
                a(s, args[0]!!, "updatecheck")
                return s
            }
        }
        return null
    }


}
