package pl.timsixth.guilibrary.example.manager;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.guilibrary.core.model.action.custom.GiveItemsAction;
import pl.timsixth.guilibrary.core.model.action.custom.impl.GiveItemsActionImpl;
import pl.timsixth.guilibrary.core.model.action.custom.impl.NoneClickAction;
import pl.timsixth.guilibrary.core.util.ItemBuilder;
import pl.timsixth.guilibrary.core.util.ItemUtil;
import pl.timsixth.guilibrary.example.config.ConfigFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuManager extends YAMLMenuManager {

    private final ConfigFile configFile;

    public MenuManager(ActionRegistration actionRegistration, ConfigFile configFile) {
        super(actionRegistration);
        this.configFile = configFile;
    }

    @Override
    public void load() {
        load(configFile.getYmlGuis());
    }

    @Override
    protected boolean enableSectionActions() {
        return true;
    }

    @Override
    protected void addSectionActions(ConfigurationSection slotSection, MenuItem menuItem, ClickAction action) {
        ConfigurationSection clickActionSection = slotSection.getConfigurationSection("click_action");

        if (action instanceof GiveItemsAction) {
            ClickAction giveItemsClickAction = setItemsToGive(clickActionSection);
            menuItem.setAction(giveItemsClickAction);
        }
    }

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
}
