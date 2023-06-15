package pl.timsixth.gui.libray.model.action.custom;

import org.bukkit.inventory.ItemStack;
import pl.timsixth.gui.libray.model.action.SectionAction;

import java.util.List;

/**
 * Give items action
 */
public interface GiveItemsAction extends SectionAction {
    /**
     * Sets items
     *
     * @param items items to set
     */
    void setItems(List<ItemStack> items);

    /**
     *
     * @return list of itemstack
     */

    List<ItemStack> getItems();
}
