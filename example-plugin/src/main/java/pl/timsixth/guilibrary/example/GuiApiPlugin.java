package pl.timsixth.guilibrary.example;

import com.github.javafaker.Faker;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.guilibrary.core.GUIApi;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;
import pl.timsixth.guilibrary.example.action.ChooseUserGroupAction;
import pl.timsixth.guilibrary.example.action.OpenPaginatedMenuAction;
import pl.timsixth.guilibrary.example.command.TestGuiCommand;
import pl.timsixth.guilibrary.example.config.ConfigFile;
import pl.timsixth.guilibrary.example.manager.MenuManager;
import pl.timsixth.guilibrary.example.model.Group;
import pl.timsixth.guilibrary.example.model.User;
import pl.timsixth.guilibrary.processes.ProcessesModule;

import java.util.*;

@Getter
public final class GuiApiPlugin extends JavaPlugin {

    private YAMLMenuManager menuManager;

    @Override
    public void onEnable() {
        ConfigFile configFile = new ConfigFile(this);

        GUIApi guiApi = new GUIApi(this);

        menuManager = new MenuManager(guiApi.getActionRegistration(), configFile);

        guiApi.setMenuManager(menuManager);

        guiApi.registerDefaultActions();
        guiApi.getActionRegistration().register(new ChooseUserGroupAction(), new OpenPaginatedMenuAction());

        guiApi.getModuleManager().registerModule(new ProcessesModule(this));

        guiApi.registerMenuListener();

        menuManager.load();

        getCommand("testgui").setExecutor(new TestGuiCommand(menuManager, this));

        createChooseUserGroup();
        createPagination();
    }

    private void createChooseUserGroup() {

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

    private void createPagination() {
        PaginatedMenu paginatedMenu = new PaginatedMenu(27, "paginatedMenu", "pages");

        List<User> users = getUsers();

        paginatedMenu.setData(users);
        paginatedMenu.setItemsPerPage(10);
        paginatedMenu.useDefaultStaticItems();

        menuManager.getPaginatedMenus().add(paginatedMenu);
    }

    public List<User> getUsers() {
        List<User> users = new LinkedList<>();

        Faker faker = new Faker();

        for (int i = 0; i < 55; i++) {
            int age = faker.number().numberBetween(10, 60);
            Group group = faker.options().option(Group.ADMIN, Group.MODERATOR);

            users.add(new User(faker.name().name(), faker.name().lastName(), age, group));
        }

        return users;
    }
}
