import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File
import java.io.FileReader
import java.io.IOException

object Start {
    @JvmStatic
    fun main(args: Array<String>) {
        val file = File("C:\\minecraft.json")

        if (!file.exists()) {
            Main.main(concat(arrayOf("--version", "mcp", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"), args))
            return
        }

        try {
            FileReader(file).use { reader ->
                val gson = Gson()
                val jsonObject = gson.fromJson(reader, JsonObject::class.java)

                val version = jsonObject["version"].asString
                val username = jsonObject["username"].asString
                val accessToken = jsonObject["accessToken"].asString
                val uuid = jsonObject["uuid"].asString
                val assetsDir = jsonObject["assetsDir"].asString
                val assetIndex = jsonObject["assetIndex"].asString
                val userProperties = jsonObject["userProperties"].toString()

                val finalArgs = arrayOf(
                    version, "mcp",
                    "--username", username,
                    "--accessToken", accessToken,
                    "--uuid", uuid,
                    "--assetsDir", assetsDir,
                    "--assetIndex", assetIndex,
                    "--userProperties", userProperties
                )
                Main.main(concat(finalArgs, args))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun <T> concat(first: Array<T>, second: Array<T>): Array<T> {
        return first + second
    }
}
