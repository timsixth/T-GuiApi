package pl.timsixth.guilibrary.example.manager;

import org.bukkit.configuration.ConfigurationSection;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.guilibrary.core.model.action.custom.GiveItemsAction;
import pl.timsixth.guilibrary.example.config.ConfigFile;

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
    public boolean enableSectionActions() {
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
}
