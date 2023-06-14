package pl.timsixth.gui.libray.model.action.custom.impl;

import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.gui.libray.model.action.AbstractAction;
import pl.timsixth.gui.libray.model.action.ActionType;
import pl.timsixth.gui.libray.model.action.click.ClickAction;

public class NoneClickAction extends AbstractAction implements ClickAction {
    public NoneClickAction() {
        super("NONE", ActionType.CLICK);
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
