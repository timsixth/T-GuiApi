package pl.timsixth.guilibrary.core.manager;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.core.model.EmptySlotFilling;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.Action;
import pl.timsixth.guilibrary.core.model.action.SectionAction;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.guilibrary.core.model.action.custom.GiveItemsAction;
import pl.timsixth.guilibrary.core.model.action.custom.NoneClickAction;
import pl.timsixth.guilibrary.core.util.ItemBuilder;
import pl.timsixth.guilibrary.core.util.ItemUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Loads menus from YAML files.
 */
public abstract class YAMLMenuManager extends AbstractMenuManager {
    public YAMLMenuManager(ActionRegistration actionRegistration) {
        super(actionRegistration);
    }

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
        setDisabled(slotSection, menuItem);
        setItemFlags(slotSection, menuItem);
        setTextures(slotSection, menuItem);

        if (!menuItem.isDisabled())
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

            if (action instanceof SectionAction) {
                if (enableSectionActions()) {
                    addSectionActions(slotSection, menuItem, action);
                    return;
                }
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
     * Sets price of {@link MenuItem}
     *
     * @param slot     slot section
     * @param menuItem menuItem to set price
     */
    private void setPrice(ConfigurationSection slot, MenuItem menuItem) {
        if (slot.getDouble("price") != 0) {
            menuItem.setPrice(slot.getDouble("price"));
        }
    }

    /**
     * Sets enchants to {@link MenuItem}
     *
     * @param slot     slot section
     * @param menuItem menuItem to set enchants
     */
    private void setEnchants(ConfigurationSection slot, MenuItem menuItem) {
        List<String> enchantsAsString = slot.getStringList("enchants");

        if (enchantsAsString.isEmpty()) return;

        Map<Enchantment, Integer> enchantments = ItemUtil.getEnchantments(enchantsAsString);
        menuItem.setEnchantments(enchantments);
    }

    /**
     * Disables menu item
     * Menu item is enabled by default.
     *
     * @param slot     slot section
     * @param menuItem menuItem to disable
     */
    private void setDisabled(ConfigurationSection slot, MenuItem menuItem) {
        boolean disabled = slot.getBoolean("disabled");
        menuItem.setDisabled(disabled);
    }

    /**
     * Sets textures to menu item
     *
     * @param slot     slot section
     * @param menuItem menuItem to set textures
     */
    private void setTextures(ConfigurationSection slot, MenuItem menuItem) {
        String textures = slot.getString("textures");
        if (textures == null) return;

        menuItem.setTextures(textures);
    }

    /**
     * Sets item's flags
     *
     * @param slot     slot section
     * @param menuItem menuItem to set item flags
     */
    private void setItemFlags(ConfigurationSection slot, MenuItem menuItem) {
        List<String> itemFlagsAsString = slot.getStringList("itemFlags");
        if (itemFlagsAsString.isEmpty()) return;

        List<ItemFlag> itemFlags = itemFlagsAsString.stream()
                .map(ItemFlag::valueOf)
                .collect(Collectors.toList());

        menuItem.setItemFlags(itemFlags);
    }

    /**
     * Enables section actions
     *
     * @return true if enabled, otherwise not enabled
     */
    public boolean enableSectionActions() {
        return false;
    }

    /**
     * Adds custom section-actions
     *
     * @param slotSection slot section
     * @param menuItem    menuItem to set action
     * @param action      action from YAML
     */
    protected void addSectionActions(ConfigurationSection slotSection, MenuItem menuItem, ClickAction action) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Loads items from YAML extra section
     *
     * @param clickActionSection click action section
     * @return give items action with items from section
     */
    protected ClickAction setItemsToGive(ConfigurationSection clickActionSection) {
        if (clickActionSection.getConfigurationSection("items") == null) return new NoneClickAction();

        GiveItemsAction giveItemsAction = new GiveItemsAction();
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
}
