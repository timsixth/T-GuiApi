package pl.timsixth.gui.libray.example.process;

import org.bukkit.inventory.Inventory;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.process.impl.AbstractSubGuiProcess;

public class GiveAgeSubProcess extends AbstractSubGuiProcess {
    public GiveAgeSubProcess() {
        super("GIVE_AGE");
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
