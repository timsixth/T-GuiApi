package pl.timsixth.guilibrary.example.action;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractAction;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;
import pl.timsixth.guilibrary.core.util.ChatUtil;
import pl.timsixth.guilibrary.example.GuiApiPlugin;

import java.util.Optional;

public class OpenPaginatedMenuAction extends AbstractAction implements ClickAction {

    private final GuiApiPlugin guiApiPlugin = GuiApiPlugin.getInstance();
    private final YAMLMenuManager menuManager = guiApiPlugin.getMenuManager();

    public OpenPaginatedMenuAction() {
        super("OPEN_PAGINATED_MENU");
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        Optional<PaginatedMenu> menuOptional = menuManager.getPaginatedMenu("paginatedMenu");

        if (!menuOptional.isPresent()) {
            player.sendMessage(ChatUtil.chatColor("&cMenu does not exists!"));
            event.setCancelled(true);
            return;
        }

        PaginatedMenu menu = menuOptional.get();

        menu.setData(guiApiPlugin.getUsers());

        menuManager.createPaginatedMenu(menu)
                .ifPresent(player::openInventory);

        event.setCancelled(true);
    }
}
