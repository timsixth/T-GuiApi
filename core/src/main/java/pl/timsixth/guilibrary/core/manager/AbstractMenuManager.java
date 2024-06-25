package pl.timsixth.guilibrary.core.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;
import pl.timsixth.guilibrary.core.util.ChatUtil;

import java.util.*;

@RequiredArgsConstructor
public abstract class AbstractMenuManager extends Pagination {

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
        return createMenu(menu, Collections.emptyMap());
    }

    /**
     * Generates inventory based on {@link Menu}
     *
     * @param menu         menu to generate inventory
     * @param placeholders placeholders to replace placeholders
     * @return generated inventory
     */
    public Inventory createMenu(Menu menu, Map<String, String> placeholders) {
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), ChatUtil.chatColor(menu.getDisplayName()));

        if (menu.getEmptySlotFilling() != null) {
            for (int i = 0; i < menu.getSize(); i++) {
                inv.setItem(i, new ItemStack(menu.getEmptySlotFilling().getEmptySlotMaterial(), 1));
            }
        }

        for (MenuItem menuItem : menu.getItems()) {
            if (menuItem.isDisabled()) continue;

            if (menuItem.isSkullItem()) {
                inv.setItem(menuItem.getSlot(), menuItem.toSkullItem(placeholders));
                continue;
            }
            inv.setItem(menuItem.getSlot(), menuItem.toItemStack(placeholders));
        }

        return inv;
    }

    /**
     * Generates inventory based on {@link PaginatedMenu}
     *
     * @param menu menu to generate inventory
     * @return generated inventory
     */
    public Optional<Inventory> createPaginatedMenu(PaginatedMenu menu) {
        return super.createPaginatedInventory(menu, menus);
    }

    /**
     * Generates inventory based on {@link PaginatedMenu} for one player
     *
     * @param menu   menu to generate inventory
     * @param player player to generate inventory
     * @return generated inventory
     */
    public Optional<Inventory> createPaginatedMenu(Player player, PaginatedMenu menu) {
        return super.createPaginatedInventory(player, menu, menus);
    }
}
