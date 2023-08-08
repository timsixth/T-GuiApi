package pl.timsixth.guilibrary.core.model.action.custom;

import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractAction;
import pl.timsixth.guilibrary.core.model.action.ActionType;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;

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
