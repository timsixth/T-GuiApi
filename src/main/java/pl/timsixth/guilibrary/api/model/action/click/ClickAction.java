package pl.timsixth.guilibrary.api.model.action.click;

import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.api.model.MenuItem;
import pl.timsixth.guilibrary.api.model.action.Action;

public interface ClickAction extends Action {

    void handleClickEvent(InventoryClickEvent event, MenuItem menuItem);
}
