package pl.timsixth.gui.libray.example.process;

import org.bukkit.inventory.Inventory;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.process.impl.AbstractSubGuiProcess;

public class GiveNameSubProcess extends AbstractSubGuiProcess {
    public GiveNameSubProcess() {
        super("GIVE_NAME");
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
