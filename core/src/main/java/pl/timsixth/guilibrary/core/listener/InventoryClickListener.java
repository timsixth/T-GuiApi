package pl.timsixth.guilibrary.core.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.core.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.guilibrary.core.util.ChatUtil;

import java.util.HashSet;

@RequiredArgsConstructor
public class InventoryClickListener implements Listener {

    private final AbstractMenuManager menuManager;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        for (Menu menu : new HashSet<>(menuManager.getMenus())) {
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
