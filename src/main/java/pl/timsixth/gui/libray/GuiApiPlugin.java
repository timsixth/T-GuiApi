package pl.timsixth.gui.libray;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.gui.libray.example.ConfigFile;
import pl.timsixth.gui.libray.example.MenuManager;
import pl.timsixth.gui.libray.example.TestCommand;
import pl.timsixth.gui.libray.listener.InventoryClickListener;
import pl.timsixth.gui.libray.manager.AbstractMenuManager;
import pl.timsixth.gui.libray.manager.YAMLMenuManager;
import pl.timsixth.gui.libray.manager.registration.ActionRegistration;
import pl.timsixth.gui.libray.manager.registration.ActionRegistrationImpl;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.model.action.custom.impl.GiveItemsActionImpl;
import pl.timsixth.gui.libray.model.action.custom.impl.NoneClickAction;
import pl.timsixth.gui.libray.model.action.custom.impl.SendMessageAction;
import pl.timsixth.gui.libray.util.ChatUtil;

import java.util.*;

public final class GuiApiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigFile configFile = new ConfigFile(this);
        ActionRegistration actionRegistration = new ActionRegistrationImpl();
        actionRegistration.register(new NoneClickAction(), new SendMessageAction(), new GiveItemsActionImpl());
        YAMLMenuManager menuManager = new MenuManager(actionRegistration, configFile);
        menuManager.load();
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(menuManager), this);

        getCommand("test").setExecutor(new TestCommand(menuManager));
        createInMemoryGUI(menuManager);
    }

    private void createInMemoryGUI(AbstractMenuManager menuManager){
        SendMessageAction sendMessageAction = new SendMessageAction();
        sendMessageAction.setArgs(Collections.singletonList("&cTest"));

        Set<MenuItem> items = new HashSet<>();
        items.add(MenuItem.builder()
                .displayName("&aTest")
                .material(Material.ACACIA_LOG)
                .slot(0)
                .action(sendMessageAction)
                .build());


        Menu inMemoryGui = Menu.builder()
                .displayName(ChatUtil.chatColor("&a&lTest in memory gui"))
                .name("in_memory_gui")
                .size(27)
                .items(items)
                .build();

        menuManager.getMenus().add(inMemoryGui);
    }
}
