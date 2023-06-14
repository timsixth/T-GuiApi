package pl.timsixth.gui.libray;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.gui.libray.api.GuiApi;
import pl.timsixth.gui.libray.example.ConfigFile;
import pl.timsixth.gui.libray.example.MenuManager;
import pl.timsixth.gui.libray.example.TestCommand;
import pl.timsixth.gui.libray.listener.InventoryClickListener;
import pl.timsixth.gui.libray.manager.AbstractMenuManager;
import pl.timsixth.gui.libray.manager.registration.ActionRegistration;
import pl.timsixth.gui.libray.manager.registration.ActionRegistrationImpl;
import pl.timsixth.gui.libray.model.action.custom.impl.GiveItemsActionImpl;
import pl.timsixth.gui.libray.model.action.custom.impl.NoneClickAction;
import pl.timsixth.gui.libray.model.action.custom.impl.SendMessageAction;

public final class GuiApiPlugin extends JavaPlugin {

    @Getter(AccessLevel.PACKAGE)
    private ActionRegistration actionRegistration;

    private static GuiApi guiApi;

    @Override
    public void onEnable() {
        guiApi = new GuiApiImpl(this);

        ConfigFile configFile = new ConfigFile(this);
        actionRegistration = new ActionRegistrationImpl();
        actionRegistration.register(new NoneClickAction(), new SendMessageAction(), new GiveItemsActionImpl());
        AbstractMenuManager menuManager = new MenuManager(actionRegistration, configFile);
        menuManager.load();
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(menuManager), this);

        getCommand("test").setExecutor(new TestCommand(menuManager));
    }

    public static GuiApi getApi() {
        return guiApi;
    }
}
