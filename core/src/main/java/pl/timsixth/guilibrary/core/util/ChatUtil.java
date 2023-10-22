package pl.timsixth.guilibrary.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Replace hexColor to 'and' symbol in text
     *
     * @param text the text to translate
     * @return colored text
     */
    public static String hexColor(String text) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String hexCode = text.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] charArray = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char character : charArray) {
                builder.append("&").append(character);
            }

            text = text.replace(hexCode, builder.toString());
            matcher = pattern.matcher(text);
        }
        return chatColor(text);
    }

    /**
     * Replace hexColor to 'and' symbol in list of strings
     *
     * @param texts the list of texts
     * @return colored texts
     */
    public static List<String> hexColor(List<String> texts) {
        List<String> strings = new ArrayList<>();

        texts.forEach(text -> strings.add(hexColor(text)));

        return chatColor(strings);
    }
}
