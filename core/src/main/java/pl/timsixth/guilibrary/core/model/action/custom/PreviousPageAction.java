package pl.timsixth.guilibrary.core.model.action.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractAction;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.guilibrary.core.model.pagination.Page;

import java.util.Optional;

/**
 * Previous page action
 */
public class PreviousPageAction extends AbstractAction implements ClickAction {
    public PreviousPageAction() {
        super("PREVIOUS_ACTION");
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Menu menu = menuItem.getParentMenu();
        Player player = (Player) event.getWhoClicked();

        if (menu instanceof Page) {

            Page page = (Page) menu;

            Optional<Page> pageOptional = page.getPaginatedMenu().previousPage();

            pageOptional.ifPresent(previousPage -> player.openInventory(previousPage.getInventory()));
        }
    }
}
