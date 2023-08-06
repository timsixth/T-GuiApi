package pl.timsixth.guilibrary.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.timsixth.guilibrary.api.listener.InventoryClickListener;
import pl.timsixth.guilibrary.api.listener.InventoryCloseListener;
import pl.timsixth.guilibrary.api.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.api.manager.InMemoryMenuManager;
import pl.timsixth.guilibrary.api.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.api.manager.registration.ActionRegistrationImpl;

@Setter
@Getter
public final class GUIApi {

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private final Plugin plugin;
    private AbstractMenuManager menuManager;
    private ActionRegistration actionRegistration;

    public GUIApi(Plugin plugin) {
        this.plugin = plugin;
        this.actionRegistration = new ActionRegistrationImpl();
        this.menuManager = new InMemoryMenuManager(actionRegistration);

        this.registerListeners();
    }

    private void registerListeners() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new InventoryClickListener(menuManager), plugin);
        pluginManager.registerEvents(new InventoryCloseListener(), plugin);
    }
}
