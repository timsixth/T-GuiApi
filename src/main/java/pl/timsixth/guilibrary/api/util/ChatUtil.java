package pl.timsixth.guilibrary.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ChatUtil {
    /**
     * Replaces colors in text
     *
     * @param text the text
     * @return colored text
     */
    public static String chatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Replaces colors in list of texts
     *
     * @param texts the list of texts
     * @return colored texts
     */
    public static List<String> chatColor(List<String> texts) {
        List<String> strings = new ArrayList<>();
        for (String text : texts) {
            strings.add(chatColor(text));
        }
        return strings;
    }
}
