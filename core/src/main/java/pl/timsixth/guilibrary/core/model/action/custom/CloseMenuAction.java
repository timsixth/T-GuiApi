package pl.timsixth.guilibrary.core.model.action.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractAction;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;

/**
 * Close menu action.
 * You can use this action when you want to close current inventory for player.
 */
public class CloseMenuAction extends AbstractAction implements ClickAction {
    public CloseMenuAction() {
        super("CLOSE_MENU");
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        if (event.isShiftClick()) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getWhoClicked();

        player.closeInventory();
    }
}
