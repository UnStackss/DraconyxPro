@file:Suppress("MemberVisibilityCanBePrivate")

package fur.kiysohi.draconyxpro.utils

import org.bukkit.entity.Player
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.InputStreamReader
import java.math.BigInteger
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class MojangAPI {
    companion object {

        fun isPremium(player: Player, name: String): Boolean {
            val actualUUIDStr = player.uniqueId.toString()
            if(actualUUIDStr == getUUID(name).toString()){
                return true
            }
            return false
        }

        @Suppress("unused")
        fun getMd5(input: String): String {
            return try {
                val md = MessageDigest.getInstance("MD5")
                val messagedigest = md.digest(input.toByteArray())
                val no = BigInteger(1, messagedigest)
                val hashtext = StringBuilder(no.toString(16))
                while (hashtext.length < 32) {
                    hashtext.insert(0, "0")
                }
                hashtext.toString()
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            }
        }

        @Throws(Exception::class)
        fun getUUID(name: String): UUID {
            val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
            @Suppress("unused") val uuid = (JSONParser().parse(InputStreamReader(url.openStream())) as JSONObject)["id"] as String
            val realUUID =
                uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(
                    16,
                    20
                ) + "-" + uuid.substring(20, 32)
            return UUID.fromString(realUUID)
        }

        @Suppress("unused")
        @Throws(Exception::class)
        fun getNAME(name: String): Any? {
            val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
            return (JSONParser().parse(InputStreamReader(url.openStream())) as JSONObject)["name"]
        }
    }
}
