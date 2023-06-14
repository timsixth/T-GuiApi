package pl.timsixth.gui.libray.util;

import lombok.experimental.UtilityClass;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ItemUtil {
    public static Map<Enchantment, Integer> getEnchantments(List<String> enchants) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        for (String enchant : enchants) {
            enchantments.put(Enchantment.getByName(enchant.split(";")[0]), Integer.parseInt(enchant.split(";")[1]));
        }
        return enchantments;
    }
}
