package pl.timsixth.guilibrary.example;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.guilibrary.api.GUIApi;
import pl.timsixth.guilibrary.api.ProcessesModule;
import pl.timsixth.guilibrary.api.listener.InventoryClickListener;
import pl.timsixth.guilibrary.api.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.api.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.api.model.Menu;
import pl.timsixth.guilibrary.api.model.MenuItem;
import pl.timsixth.guilibrary.api.model.action.custom.impl.GiveItemsActionImpl;
import pl.timsixth.guilibrary.api.model.action.custom.impl.NoneClickAction;
import pl.timsixth.guilibrary.api.model.action.custom.impl.SendMessageAction;
import pl.timsixth.guilibrary.example.action.ChooseUserGroupAction;
import pl.timsixth.guilibrary.example.command.TestCommand;
import pl.timsixth.guilibrary.example.config.ConfigFile;
import pl.timsixth.guilibrary.example.manager.MenuManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class GuiApiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigFile configFile = new ConfigFile(this);

        GUIApi guiApi = new GUIApi(this);

        guiApi.getActionRegistration().register(new NoneClickAction(), new SendMessageAction(), new GiveItemsActionImpl(), new ChooseUserGroupAction());

        YAMLMenuManager menuManager = new MenuManager(guiApi.getActionRegistration(), configFile);

        guiApi.setMenuManager(menuManager);

        guiApi.getModuleManager().registerModule(new ProcessesModule(this));

        menuManager.load();

        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(menuManager), this);

        getCommand("test").setExecutor(new TestCommand(menuManager, this));
        createInMemoryGUI(menuManager);
        createChooseUserGroup(menuManager);
    }

    private void createInMemoryGUI(AbstractMenuManager menuManager) {
        SendMessageAction sendMessageAction = new SendMessageAction();
        sendMessageAction.setArgs(Collections.singletonList("&cTest"));

        Set<MenuItem> items2 = new HashSet<>();
        items2.add(MenuItem.builder()
                .displayName("&a&l123")
                .slot(1)
                .action(sendMessageAction)
                .material(Material.DIAMOND)
                .build());

        Menu inMemory2 = Menu.builder()
                .displayName("&a&l2Gui")
                .name("in_memory2")
                .size(27)
                .items(items2)
                .build();

        menuManager.getMenus().add(inMemory2);
    }

    private void createChooseUserGroup(AbstractMenuManager menuManager) {

        ChooseUserGroupAction chooseUserGroupAction = new ChooseUserGroupAction();
        chooseUserGroupAction.setArgs(Collections.singletonList("ADMIN"));

        ChooseUserGroupAction chooseUserGroupAction1 = new ChooseUserGroupAction();
        chooseUserGroupAction1.setArgs(Collections.singletonList("MODERATOR"));

        MenuItem admin = MenuItem.builder()
                .action(chooseUserGroupAction)
                .displayName("&c&lAdmin")
                .material(Material.RED_WOOL)
                .slot(0)
                .build();

        MenuItem mod = MenuItem.builder()
                .action(chooseUserGroupAction1)
                .displayName("&a&lModerator")
                .material(Material.GREEN_WOOL)
                .slot(1)
                .build();

        Set<MenuItem> items = new HashSet<>();
        items.add(admin);
        items.add(mod);

        Menu menu = Menu.builder()
                .name("chooseUserGroup")
                .displayName("&a&lChoose user group")
                .items(items)
                .size(9)
                .build();

        menuManager.getMenus().add(menu);
    }
}
