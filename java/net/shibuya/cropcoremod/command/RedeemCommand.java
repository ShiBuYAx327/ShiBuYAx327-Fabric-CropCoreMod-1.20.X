package net.shibuya.cropcoremod.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.shibuya.cropcoremod.item.ModItems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RedeemCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("redeem")
                .then(CommandManager.argument("code", StringArgumentType.string())
                        .executes(context -> {
                            String code = StringArgumentType.getString(context, "code");
                            ServerCommandSource source = context.getSource();

                            try {

                                URL url = new URL("http://127.0.0.1:3000/verifycode");
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setDoOutput(true);
                                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                conn.setRequestProperty("Accept", "application/json");


                                String json = String.format("{\"code\":\"%s\",\"player\":\"%s\"}",
                                        code, source.getName());


                                try (OutputStream os = conn.getOutputStream()) {
                                    os.write(json.getBytes());
                                    os.flush();
                                }

                                int status = conn.getResponseCode();


                                BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(
                                                status >= 200 && status < 300
                                                        ? conn.getInputStream()
                                                        : conn.getErrorStream(),
                                                "UTF-8"
                                        )
                                );
                                StringBuilder response = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    response.append(line);
                                }
                                reader.close();

                                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();

                                boolean success = jsonResponse.has("success") && jsonResponse.get("success").getAsBoolean();
                                String message = jsonResponse.has("message") ? jsonResponse.get("message").getAsString() : "";
                                String itemName = jsonResponse.has("item") ? jsonResponse.get("item").getAsString() : "";


                                if (success) {
                                    ItemStack item = getItemFromName(itemName);
                                    if (!item.isEmpty()) {
                                        source.getPlayer().giveItemStack(item);
                                        source.sendFeedback(() -> Text.literal("คุณได้รับไอเทม: " + itemName), false);
                                    }
                                } else {
                                    source.sendError(Text.literal(message.isEmpty()
                                            ? "โค้ดไม่ถูกต้อง หรือถูกใช้ไปแล้ว" : message));
                                }

                                conn.disconnect();

                            } catch (Exception e) {
                                e.printStackTrace();
                                source.sendError(Text.literal("เกิดข้อผิดพลาดในการเชื่อมต่อกับเซิร์ฟเวอร์"));
                            }

                            return 1;
                        })));
    }

    private static ItemStack getItemFromName(String name) {
        switch (name.toLowerCase()) {
            case "diamond sword":
                return new ItemStack(Items.DIAMOND_SWORD);
            case "iron pickaxe":
                return new ItemStack(Items.IRON_PICKAXE);
            case "golden apple":
                return new ItemStack(Items.GOLDEN_APPLE);
            case "starlight hammer":
                return new ItemStack(ModItems.PINK_GARNET_HAMMER);
            case "pink garnet":
                return new ItemStack(ModItems.PINK_GARNET);
            case "chisel":
                return new ItemStack(ModItems.CHISEL);
            case "cauliflower":
                return new ItemStack(ModItems.CAULIFLOWER);
            case "iron stick":
                return new ItemStack(ModItems.IRON_STICK);
            default:
                return ItemStack.EMPTY;
        }
    }
}
