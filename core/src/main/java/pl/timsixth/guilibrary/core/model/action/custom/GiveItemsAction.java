package pl.timsixth.guilibrary.core.model.action.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractAction;
import pl.timsixth.guilibrary.core.model.action.SectionAction;

import java.util.List;

/**
 * Give items action
 */
public class GiveItemsAction extends AbstractAction implements SectionAction {

    private List<ItemStack> items;

    public GiveItemsAction() {
        super("GIVE_ITEMS");
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        for (ItemStack item : items) {
            player.getInventory().addItem(item);
        }

        event.setCancelled(true);
    }

    /**
     * Sets items
     *
     * @param items items to set
     */
    public void setItems(List<ItemStack> items) {
        this.items = items;
    }
}
