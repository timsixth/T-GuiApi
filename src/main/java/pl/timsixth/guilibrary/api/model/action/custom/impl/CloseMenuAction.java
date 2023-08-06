package pl.timsixth.guilibrary.api.model.action.custom.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.api.model.MenuItem;
import pl.timsixth.guilibrary.api.model.action.AbstractAction;
import pl.timsixth.guilibrary.api.model.action.click.ClickAction;

public class CloseMenuAction extends AbstractAction implements ClickAction {
    public CloseMenuAction() {
        super("CLOSE_MENU");
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        player.closeInventory();
    }
}
