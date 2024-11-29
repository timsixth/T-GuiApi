package pl.timsixth.guilibrary.core.model;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.timsixth.guilibrary.core.model.action.Action;
import pl.timsixth.guilibrary.core.util.ChatUtil;
import pl.timsixth.guilibrary.core.util.ItemBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private double price;
    private String localizedName;
    private ItemStack item;
    private Menu parentMenu;
    private boolean disabled;
    private String textures;
    private List<ItemFlag> itemFlags;

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
                .addItemFlags(getItemFlags())
                .toItemStack();
    }

    /**
     * Creates skull item from {@link MenuItem} data
     *
     * @return created skull item
     */
    public ItemStack toSkullItem() {
        return toSkullItem(Collections.emptyMap());
    }

    /**
     * Creates skull item from {@link MenuItem} data with placeholders
     *
     * @param placeholders placeholders to replace placeholders
     * @return created skull item
     */
    public ItemStack toSkullItem(Map<String, String> placeholders) {
        ItemStack item = toItemStack(placeholders);

        if (item.getType() != Material.PLAYER_HEAD) return item;

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", textures));
        Field field;
        try {
            field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            try {
                //This code is inspired by DecentHolograms, I got some code that I needed
                Class<?> resolvableProfileClass = Class.forName("net.minecraft.world.item.component.ResolvableProfile");

                Constructor<?> constructor = resolvableProfileClass.getConstructor(GameProfile.class);

                Method method = meta.getClass().getDeclaredMethod("setProfile", constructor.getDeclaringClass());
                method.setAccessible(true);

                method.invoke(meta, constructor.newInstance(profile));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                     ClassNotFoundException | InstantiationException ex) {
                Bukkit.getLogger().severe(ex.getMessage());
            }
        }

        item.setItemMeta(meta);

        return item;
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

    /**
     * Gets List of item flags
     *
     * @return list of item flags
     */
    public List<ItemFlag> getItemFlags() {
        if (itemFlags == null) return new ArrayList<>();
        return itemFlags;
    }

    /**
     * Checks that menu item is skull item
     *
     * @return true if menu item is skull item otherwise false
     */
    public boolean isSkullItem() {
        return textures != null;
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
