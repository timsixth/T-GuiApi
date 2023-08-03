package pl.timsixth.gui.libray.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.gui.libray.manager.registration.ActionRegistration;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.util.ChatUtil;
import pl.timsixth.gui.libray.util.ItemBuilder;

import java.util.*;

@RequiredArgsConstructor
public abstract class AbstractMenuManager {

    protected final ActionRegistration actionRegistration;

    @Getter
    protected final Set<Menu> menus = new HashSet<>();

    /**
     * Gets {@link Menu} by name
     *
     * @param name menu name
     * @return Optional of Menu
     */
    public Optional<Menu> getMenuByName(String name) {
        return menus.stream().
                filter(menu -> menu.getName().equalsIgnoreCase(name))
                .findAny();
    }

    /**
     * Generates inventory based on {@link Menu}
     *
     * @param menu menu to generate inventory
     * @return generated inventory
     */
    public Inventory createMenu(Menu menu) {
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), ChatUtil.chatColor(menu.getDisplayName()));

        if (menu.getEmptySlotFilling() != null) {
            for (int i = 0; i < menu.getSize(); i++) {
                inv.setItem(i, new ItemStack(menu.getEmptySlotFilling().getEmptySlotMaterial(), 1));
            }
        }

        for (MenuItem menuItem : menu.getItems()) {
            List<String> replaceLore = new ArrayList<>();
            for (String line : menuItem.getLore()) {
                replaceLore.add(line.replace("{PRICE}", String.valueOf(menuItem.getPrice())));
            }

            inv.setItem(menuItem.getSlot(), new ItemBuilder(new ItemStack(menuItem.getMaterial(), 1))
                    .setLore(ChatUtil.chatColor(replaceLore))
                    .setName(ChatUtil.chatColor(menuItem.getDisplayName()))
                    .addEnchantments(menuItem.getEnchantments())
                    .toItemStack()
            );
        }
        return inv;
    }
}
