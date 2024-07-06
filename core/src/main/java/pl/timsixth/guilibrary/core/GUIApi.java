package pl.timsixth.guilibrary.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.timsixth.guilibrary.core.listener.InventoryClickListener;
import pl.timsixth.guilibrary.core.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.core.manager.InMemoryMenuManager;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistrationImpl;
import pl.timsixth.guilibrary.core.model.action.custom.*;

/**
 * Class to initialize GUI library
 */
@Setter
@Getter
public final class GUIApi {

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private final Plugin plugin;
    private AbstractMenuManager menuManager;
    private ActionRegistration actionRegistration;

    /**
     * In this constructor GUIApi initializes.
     * registerListeners method calls in constructor.
     * Sets default values to menuManager and actionRegistration fields.
     * Default value for menuManager is {@link InMemoryMenuManager}
     *
     * @param plugin given plugin which uses this library
     */
    public GUIApi(Plugin plugin) {
        this.plugin = plugin;
        this.actionRegistration = new ActionRegistrationImpl();
        this.menuManager = new InMemoryMenuManager(actionRegistration);
    }

    /**
     * Registers menu listeners
     */
    public void registerMenuListener() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new InventoryClickListener(menuManager), plugin);
    }

    /**
     * Registers default actions
     */
    public void registerDefaultActions() {
        this.actionRegistration.register(new NoneClickAction()
                , new CloseMenuAction()
                , new SendMessageAction()
                , new PreviousPageAction()
                , new NextPageAction()
        );

        if (menuManager instanceof YAMLMenuManager) {
            YAMLMenuManager yamlMenuManager = (YAMLMenuManager) menuManager;

            if (yamlMenuManager.enableSectionActions())
                this.actionRegistration.register(new GiveItemsAction());
        }

    }
}
