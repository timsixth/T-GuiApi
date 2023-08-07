package pl.timsixth.guilibrary.api.model.action.custom.impl;

import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.api.model.MenuItem;
import pl.timsixth.guilibrary.api.model.action.AbstractAction;
import pl.timsixth.guilibrary.api.model.action.ActionType;
import pl.timsixth.guilibrary.api.model.action.click.ClickAction;

/**
 * This class is setting when plugin can not find action by name
 */
public class NoneClickAction extends AbstractAction implements ClickAction {
    public NoneClickAction() {
        super("NONE", ActionType.CLICK);
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        event.setCancelled(true);
    }
}
