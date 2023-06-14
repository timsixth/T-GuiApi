package pl.timsixth.gui.libray.model.action.click;

import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.gui.libray.model.action.Action;

public interface ClickAction extends Action {

    void handleClickEvent(InventoryClickEvent event);
}
