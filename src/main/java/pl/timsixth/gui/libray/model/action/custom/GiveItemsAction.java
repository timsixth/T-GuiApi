package pl.timsixth.gui.libray.model.action.custom;

import org.bukkit.inventory.ItemStack;
import pl.timsixth.gui.libray.model.action.SectionAction;

import java.util.List;

public interface GiveItemsAction extends SectionAction {

    void setItems(List<ItemStack> items);
    List<ItemStack> getItems();
}
