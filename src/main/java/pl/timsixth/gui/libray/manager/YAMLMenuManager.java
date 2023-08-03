package pl.timsixth.gui.libray.manager;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import pl.timsixth.gui.libray.manager.registration.ActionRegistration;
import pl.timsixth.gui.libray.model.EmptySlotFilling;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.model.action.Action;
import pl.timsixth.gui.libray.model.action.SectionAction;
import pl.timsixth.gui.libray.model.action.click.ClickAction;
import pl.timsixth.gui.libray.model.action.custom.impl.NoneClickAction;
import pl.timsixth.gui.libray.util.ItemUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class YAMLMenuManager extends AbstractMenuManager{
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
     * Enables section actions
     *
     * @return true if enabled, otherwise not enabled
     */
    protected boolean enableSectionActions() {
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
}
