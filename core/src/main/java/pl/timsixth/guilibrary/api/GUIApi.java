package pl.timsixth.guilibrary.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.timsixth.guilibrary.api.listener.InventoryClickListener;
import pl.timsixth.guilibrary.api.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.api.manager.InMemoryMenuManager;
import pl.timsixth.guilibrary.api.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.api.manager.registration.ActionRegistrationImpl;
import pl.timsixth.guilibrary.api.module.ModuleManager;

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
    @Setter(AccessLevel.NONE)
    private ModuleManager moduleManager;

    /**
     * In this constructor GUIApi initializes.
     * registerListeners method calls in constructor.
     * Sets default values to menuManager and actionRegistration fields.<br/>
     * Default value for menuManager is {@link InMemoryMenuManager}
     *
     * @param plugin given plugin which uses this library
     */
    public GUIApi(Plugin plugin) {
        this.plugin = plugin;
        this.actionRegistration = new ActionRegistrationImpl();
        this.menuManager = new InMemoryMenuManager(actionRegistration);
        this.moduleManager = new ModuleManager();

        this.registerListeners();
    }

    /**
     * Registers menu listeners
     */
    private void registerListeners() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new InventoryClickListener(menuManager), plugin);
    }
}
