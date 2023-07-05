package pl.timsixth.gui.libray.model.action.custom.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.model.action.AbstractAction;
import pl.timsixth.gui.libray.model.action.ActionType;
import pl.timsixth.gui.libray.model.action.click.ClickAction;
import pl.timsixth.gui.libray.util.ChatUtil;

/**
 * Send message action
 */
public class SendMessageAction extends AbstractAction implements ClickAction {

    public SendMessageAction() {
        super( "SEND_MESSAGE",ActionType.CLICK);
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        player.sendMessage(getMessage());

        event.setCancelled(true);
    }
    private String getMessage() {
        if (!hasArgs()){
            throw new IllegalStateException("List of action arguments is empty (Action = " +getName()+" )");
        }

        return ChatUtil.chatColor(getArgs().get(0));
    }
}
