package pl.timsixth.guilibrary.api.model;

import lombok.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.timsixth.guilibrary.api.model.action.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents one item in Menu
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class MenuItem {

    private int slot;
    private Material material;
    private String displayName;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private Action action;
    private int price;

    public MenuItem(int slot, Material material, String displayName, List<String> lore) {
        this.slot = slot;
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        if (enchantments == null) return new HashMap<>();
        return enchantments;
    }

    public List<String> getLore() {
        if (lore == null) return new ArrayList<>();
        return lore;
    }
}
