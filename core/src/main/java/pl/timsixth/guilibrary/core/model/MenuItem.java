package pl.timsixth.guilibrary.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.guilibrary.core.model.action.Action;
import pl.timsixth.guilibrary.core.util.ChatUtil;
import pl.timsixth.guilibrary.core.util.ItemBuilder;

import java.util.*;

/**
 * Represents one item in Menu
 */
@Getter
@Setter
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
    private String localizedName;
    private ItemStack item;
    private Menu parentMenu;
    private boolean disabled;

    public MenuItem(int slot, Material material, String displayName, List<String> lore) {
        this.slot = slot;
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
    }

    public MenuItem(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
    }

    /**
     * Creates item stack from {@link MenuItem} data
     *
     * @return created item stack
     */
    public ItemStack toItemStack() {
        return toItemStack(Collections.emptyMap());
    }

    /**
     * Creates item stack from {@link MenuItem} data with placeholders
     *
     * @param placeholders placeholders to replace placeholders
     * @return created item stack
     */
    public ItemStack toItemStack(Map<String, String> placeholders) {
        if (material == null) return item;

        List<String> replacedLore = new ArrayList<>();

        if (placeholders.isEmpty()) {
            for (String line : getLore()) {
                replacedLore.add(line.replace("{PRICE}", String.valueOf(price)));
            }
        }

        for (String line : getLore()) {
            if (placeholders.isEmpty()) break;

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

        return new ItemBuilder(new ItemStack(material, 1))
                .setLore(ChatUtil.hexColor(replacedLore))
                .setName(ChatUtil.hexColor(displayName))
                .addEnchantments(getEnchantments())
                .toItemStack();
    }

    /**
     * Gets Map of Enchantment and level
     *
     * @return map of Enchantment and level
     */
    public Map<Enchantment, Integer> getEnchantments() {
        if (enchantments == null) return new HashMap<>();
        return enchantments;
    }

    /**
     * Gets List of lore lines
     *
     * @return list of lore lines
     */
    public List<String> getLore() {
        if (lore == null) return new ArrayList<>();
        return lore;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "slot=" + slot +
                ", material=" + material +
                ", displayName='" + displayName + '\'' +
                ", lore=" + lore +
                ", enchantments=" + enchantments +
                ", action=" + action +
                ", price=" + price +
                ", localizedName='" + localizedName + '\'' +
                ", item=" + item +
                '}';
    }
}
