package pl.timsixth.guilibrary.api.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.api.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.api.model.Menu;
import pl.timsixth.guilibrary.api.model.MenuItem;
import pl.timsixth.guilibrary.api.model.action.click.ClickAction;
import pl.timsixth.guilibrary.api.util.ChatUtil;

@RequiredArgsConstructor
public class InventoryClickListener implements Listener {

    private final AbstractMenuManager menuManager;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        for (Menu menu : menuManager.getMenus()) {
            if (event.getView().getTitle().equalsIgnoreCase(ChatUtil.chatColor(menu.getDisplayName()))) {
                for (MenuItem menuItem : menu.getItems()) {
                    if (event.getSlot() == menuItem.getSlot()) {
                        if (menuItem.getAction() instanceof ClickAction) {
                            ClickAction clickAction = (ClickAction) menuItem.getAction();
                            clickAction.handleClickEvent(event, menuItem);
                        }
                    }
                }
                event.setCancelled(true);
            }
        }
    }
}
