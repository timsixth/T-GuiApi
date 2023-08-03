package pl.timsixth.gui.libray.example.process;

import org.bukkit.inventory.Inventory;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.gui.libray.model.process.impl.AbstractSubGuiProcess;

public class GiveSurnameSubProcess extends AbstractSubGuiProcess {
    public GiveSurnameSubProcess() {
        super("GIVE_SURNAME");
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
