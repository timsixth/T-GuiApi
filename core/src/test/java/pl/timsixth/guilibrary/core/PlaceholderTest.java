package pl.timsixth.guilibrary.core;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PlaceholderTest {

    @Test
    public void shouldReplacePlaceholders() {
        List<String> replacedLore = new ArrayList<>();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{TEST}", "123");
        placeholders.put("{username}", "test");

        int price = 120;

        for (String line : getLore()) {
            String replacedLine = line;

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String target = entry.getKey();
                String replacement = entry.getValue();

                if (!line.contains(target) && !line.contains("{PRICE}")) continue;

                replacedLine = replacedLine.replace(target, replacement)
                        .replace("{PRICE}", String.valueOf(price));
            }

            replacedLore.add(replacedLine);
        }

        assertEquals(replacedLore.toString(), "[123 123 test, Nick: test 2, 112312, Price: 120 123, 12]");
    }

    public List<String> getLore() {
        return Arrays.asList("123 {TEST} {username}", "Nick: {username} 2", "112312", "Price: {PRICE} {TEST}", "12");
    }
}
