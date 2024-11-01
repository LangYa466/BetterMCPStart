import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import net.minecraft.client.main.Main;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Start {

    public static void main(String[] args) {
        File file = new File("C:\\minecraft.json");

        if (!file.exists()) {
            Main.main(concat(new String[] {"--version", "mcp", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            String username = jsonObject.get("username").getAsString();
            String accessToken = jsonObject.get("accessToken").getAsString();
            String uuid = jsonObject.get("uuid").getAsString();
            String assetsDir = jsonObject.get("assetsDir").getAsString();
            String assetIndex = jsonObject.get("assetIndex").getAsString();
            String userProperties = jsonObject.get("userProperties").toString();

             String[] finalArgs = {
                    "--version" , "mcp",
                    "--username", username,
                    "--accessToken", accessToken,
                    "--uuid", uuid,
                    "--assetsDir", assetsDir,
                    "--assetIndex", assetIndex,
                    "--userProperties", userProperties
            };
            
            Main.main(concat(finalArgs,args));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T[] concat(T[] first, T[] second)
    {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
