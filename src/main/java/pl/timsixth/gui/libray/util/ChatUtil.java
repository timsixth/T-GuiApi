package pl.timsixth.gui.libray.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ChatUtil {

    public static String chatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> chatColor(List<String> texts) {
        List<String> strings = new ArrayList<>();
        for (String text : texts) {
            strings.add(chatColor(text));
        }
        return strings;
    }
}
