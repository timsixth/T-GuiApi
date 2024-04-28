package pl.timsixth.guilibrary.processes;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.timsixth.guilibrary.core.module.Module;
import pl.timsixth.guilibrary.processes.listener.InventoryCloseListener;

/**
 * Represents processes module
 *
 * @deprecated
 */
@Deprecated
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
