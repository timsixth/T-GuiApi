package pl.timsixth.gui.libray.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.gui.libray.manager.AbstractMenuManager;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.MenuItem;
import pl.timsixth.gui.libray.model.action.click.ClickAction;
import pl.timsixth.gui.libray.util.ChatUtil;

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
