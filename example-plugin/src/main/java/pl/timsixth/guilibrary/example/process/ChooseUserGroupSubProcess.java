package pl.timsixth.guilibrary.example.process;

import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.api.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.api.model.Menu;
import pl.timsixth.guilibrary.api.model.impl.AbstractSubGuiProcess;

public class ChooseUserGroupSubProcess extends AbstractSubGuiProcess {

    private final AbstractMenuManager menuManager;

    private final Menu menu;

    public ChooseUserGroupSubProcess(AbstractMenuManager menuManager) {
        super("CHOOSE_USER_GROUP");
        this.menuManager = menuManager;
        this.menu = menuManager.getMenuByName("chooseUserGroup").get();
    }

    @Override
    public String getInventoryDisplayName() {
        return menu.getDisplayName();
    }

    @Override
    public Inventory getInventory() {
        return menuManager.createMenu(menu);
    }
}
