package pl.timsixth.gui.libray.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.gui.libray.manager.registration.ActionRegistration;
import pl.timsixth.gui.libray.model.EmptySlotFilling;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.model.action.Action;
import pl.timsixth.gui.libray.model.action.click.ClickAction;
import pl.timsixth.gui.libray.model.action.custom.GiveItemsAction;
import pl.timsixth.gui.libray.model.action.custom.impl.GiveItemsActionImpl;
import pl.timsixth.gui.libray.model.action.custom.impl.NoneClickAction;
import pl.timsixth.gui.libray.util.ChatUtil;
import pl.timsixth.gui.libray.util.ItemBuilder;
import pl.timsixth.gui.libray.util.ItemUtil;

import java.util.*;

@RequiredArgsConstructor
public abstract class AbstractMenuManager {

    private final ActionRegistration actionRegistration;

    @Getter
    private final Set<Menu> menus = new HashSet<>();

    /**
     * Loads every guis
     */
    public abstract void load();

    /**
     * Loads single gui from YAML
     *
     * @param yamlConfiguration the yamlConfiguration
     */
    protected void load(YamlConfiguration yamlConfiguration) {
        ConfigurationSection guis = yamlConfiguration.getConfigurationSection("guis");
        for (String guiName : guis.getKeys(false)) {
            ConfigurationSection guiSection = guis.getConfigurationSection(guiName);
            if (guiSection.getConfigurationSection("empty_slots") == null) {
                Menu menu = new Menu(guiSection.getInt("size"), guiName, guiSection.getString("displayname"));
                createMenu(guiSection, menu);
            } else {
                ConfigurationSection emptySlots = guiSection.getConfigurationSection("empty_slots");
                Menu menu = new Menu(guiSection.getInt("size"), guiName, guiSection.getString("displayname"),
                        new EmptySlotFilling(
                                Material.getMaterial(emptySlots.getString("material"))
                        ));
                createMenu(guiSection, menu);
            }
        }
    }

    /**
     * Creates new menu from guiSection
     *
     * @param guiSection section to set slots
     * @param menu       menu to create
     */
    private void createMenu(ConfigurationSection guiSection, Menu menu) {
        ConfigurationSection slots = guiSection.getConfigurationSection("slots");
        Set<MenuItem> menuItemSet = new HashSet<>();
        for (String slotNumber : slots.getKeys(false)) {
            ConfigurationSection slot = slots.getConfigurationSection(slotNumber);
            createMenuItem(menuItemSet, slotNumber, slot);
        }
        menu.setItems(menuItemSet);
        menus.add(menu);
    }

    /**
     * Creates new menu item
     *
     * @param menuItemSet menuItems
     * @param slotNumber  slot to set menuItem
     * @param slotSection slot section
     */
    private void createMenuItem(Set<MenuItem> menuItemSet, String slotNumber, ConfigurationSection slotSection) {
        MenuItem menuItem = new MenuItem(
                Integer.parseInt(slotNumber),
                Material.getMaterial(slotSection.getString("material")),
                slotSection.getString("displayname"),
                slotSection.getStringList("lore")
        );
        setPrice(slotSection, menuItem);
        setEnchants(slotSection, menuItem);
        setAction(slotSection, menuItem);

        menuItemSet.add(menuItem);
    }

    /**
     * Sets click action on {@link MenuItem}
     *
     * @param slotSection slot section
     * @param menuItem    menuItem to set action
     */
    private void setAction(ConfigurationSection slotSection, MenuItem menuItem) {
        if (slotSection.getConfigurationSection("click_action") != null) {
            ConfigurationSection clickActionSection = slotSection.getConfigurationSection("click_action");

            String name = clickActionSection.getString("type");

            Optional<Action> actionOptional = actionRegistration.getActionByName(name);
            if (!actionOptional.isPresent()) {
                menuItem.setAction(new NoneClickAction());
                return;
            }

            ClickAction action = (ClickAction) actionOptional.get();

            if (action instanceof GiveItemsAction) {
                ClickAction giveItemsClickAction = setItemsToGive(clickActionSection);
                menuItem.setAction(giveItemsClickAction);
                return;
            }

            ClickAction clickAction;
            try {
                clickAction = action.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            clickAction.setArgs(clickActionSection.getStringList("args"));

            menuItem.setAction(clickAction);
        }
    }

    /**
     * Sets items to give when it exists
     *
     * @param clickActionSection action section
     * @return set click action
     */
    private ClickAction setItemsToGive(ConfigurationSection clickActionSection) {
        if (clickActionSection.getConfigurationSection("items") == null) return new NoneClickAction();

        GiveItemsAction giveItemsAction = new GiveItemsActionImpl();
        ConfigurationSection itemsSection = clickActionSection.getConfigurationSection("items");
        List<ItemStack> items = new ArrayList<>();
        for (String materialName : itemsSection.getKeys(false)) {
            ConfigurationSection materialSection = itemsSection.getConfigurationSection(materialName);
            ItemStack item = new ItemBuilder(new ItemStack(Material.getMaterial(materialName), materialSection.getInt("amount"))).toItemStack();

            if (materialSection.getInt("id") != 0) {
                item = new ItemBuilder(new ItemStack(Material.getMaterial(materialName), materialSection.getInt("amount"))).toItemStack();
            }
            if (materialSection.getStringList("enchants") != null) {
                Map<Enchantment, Integer> enchantments = ItemUtil.getEnchantments(materialSection.getStringList("enchants"));
                item = new ItemBuilder(new ItemStack(Material.getMaterial(materialName), materialSection.getInt("amount")))
                        .addEnchantments(enchantments)
                        .toItemStack();
            }
            items.add(item);
        }
        giveItemsAction.setItems(items);

        return giveItemsAction;
    }

    /**
     * Sets price of {@link MenuItem}
     *
     * @param slot     slot section
     * @param menuItem menuItem to set price
     */
    private void setPrice(ConfigurationSection slot, MenuItem menuItem) {
        if (slot.getInt("price") != 0) {
            menuItem.setPrice(slot.getInt("price"));
        }
    }

    /**
     * Sets enchants to {@link MenuItem}
     *
     * @param slot     slot section
     * @param menuItem menuItem to set enchants
     */
    private void setEnchants(ConfigurationSection slot, MenuItem menuItem) {
        if (slot.getStringList("enchants") != null) {
            Map<Enchantment, Integer> enchantments = ItemUtil.getEnchantments(slot.getStringList("enchants"));
            menuItem.setEnchantments(enchantments);
        }
    }

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
            List<String> lore = menuItem.getLore();
            List<String> replaceLore = new ArrayList<>();
            for (String line : lore) {
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
