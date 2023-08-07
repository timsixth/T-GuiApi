package pl.timsixth.guilibrary.api.model.action.click;

import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.api.model.MenuItem;
import pl.timsixth.guilibrary.api.model.action.Action;

public interface ClickAction extends Action {
    /**
     * Executes when player click on {@link MenuItem} in GUI
     *
     * @param event    InventoryClickEvent
     * @param menuItem clicked {@link MenuItem}
     */
    void handleClickEvent(InventoryClickEvent event, MenuItem menuItem);
}
