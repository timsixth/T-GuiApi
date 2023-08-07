package pl.timsixth.guilibrary.api;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.timsixth.guilibrary.api.listener.InventoryCloseListener;
import pl.timsixth.guilibrary.api.module.Module;

/**
 * Represents processes module
 */
public final class ProcessesModule implements Module {

    private final Plugin plugin;

    @Override
    public String getName() {
        return "processes";
    }

    public ProcessesModule(Plugin plugin) {
        this.plugin = plugin;

        this.registerListeners();
    }

    /**
     * Registers menu listeners
     */
    private void registerListeners() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new InventoryCloseListener(), plugin);
    }
}
