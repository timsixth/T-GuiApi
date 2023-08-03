package pl.timsixth.guilibrary.example.process;

import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.api.model.process.impl.AbstractSubGuiProcess;

public class GiveNameSubProcess extends AbstractSubGuiProcess {
    public GiveNameSubProcess() {
        super("GIVE_NAME");
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
