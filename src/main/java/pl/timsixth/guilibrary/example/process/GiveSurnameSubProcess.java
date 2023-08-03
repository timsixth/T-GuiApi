package pl.timsixth.guilibrary.example.process;

import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.api.model.process.impl.AbstractSubGuiProcess;

public class GiveSurnameSubProcess extends AbstractSubGuiProcess {
    public GiveSurnameSubProcess() {
        super("GIVE_SURNAME");
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
