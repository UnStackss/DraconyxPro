package fur.kiysohi.draconyxpro.utils

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.function.Consumer


class KiyoshiUpdateChecker(private val plugin: JavaPlugin, private val resourceId: Int) {
    fun getVersion(consumer: Consumer<String?>) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            try {
                URL("https://api.spigotmc.org/legacy/update.php?resource= $resourceId").openStream()
                    .use { inputStream ->
                        Scanner(inputStream).use { scanner ->
                            if (scanner.hasNext()) {
                                consumer.accept(scanner.next())
                            }
                        }
                    }
            } catch (exception: IOException) {
                plugin.logger.info(Format.color("&7[&9Draconyx&7] &cUnable to check for updates: &4") + exception.message)
            }
        })
    }
}