package pl.timsixth.guilibrary.example;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.guilibrary.core.GUIApi;
import pl.timsixth.guilibrary.core.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.example.action.ChooseUserGroupAction;
import pl.timsixth.guilibrary.example.command.TestCommand;
import pl.timsixth.guilibrary.example.config.ConfigFile;
import pl.timsixth.guilibrary.example.manager.MenuManager;
import pl.timsixth.guilibrary.processes.ProcessesModule;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class GuiApiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigFile configFile = new ConfigFile(this);

        GUIApi guiApi = new GUIApi(this);

        YAMLMenuManager menuManager = new MenuManager(guiApi.getActionRegistration(), configFile);

        guiApi.setMenuManager(menuManager);

        guiApi.registerDefaultActions();
        guiApi.getActionRegistration().register(new ChooseUserGroupAction());

        guiApi.getModuleManager().registerModule(new ProcessesModule(this));

        guiApi.registerMenuListener();

        menuManager.load();

        getCommand("test").setExecutor(new TestCommand(menuManager, this));

        createChooseUserGroup(menuManager);
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
