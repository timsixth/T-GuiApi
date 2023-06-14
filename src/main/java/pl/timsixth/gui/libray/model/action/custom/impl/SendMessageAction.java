package pl.timsixth.gui.libray.model.action.custom.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.gui.libray.model.action.AbstractAction;
import pl.timsixth.gui.libray.model.action.ActionType;
import pl.timsixth.gui.libray.model.action.click.ClickAction;
import pl.timsixth.gui.libray.model.action.exception.ActionArgumentsException;
import pl.timsixth.gui.libray.util.ChatUtil;

public class SendMessageAction extends AbstractAction implements ClickAction {

    public SendMessageAction() {
        super( "SEND_MESSAGE",ActionType.CLICK);
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        sendMessage(player,getMessage());

        event.setCancelled(true);
    }
    private String getMessage() {
        if (!hasArgs()){
            throw new ActionArgumentsException("List of action arguments is empty (Action = " +getName()+" )");
        }

        return ChatUtil.chatColor(getArgs().get(0));
    }

    private void sendMessage(Player player,String message) {
        player.sendMessage(message);
    }
}
