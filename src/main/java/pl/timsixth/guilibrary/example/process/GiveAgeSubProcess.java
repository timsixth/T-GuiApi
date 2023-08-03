package pl.timsixth.guilibrary.example.process;

import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.api.model.process.impl.AbstractSubGuiProcess;

public class GiveAgeSubProcess extends AbstractSubGuiProcess {
    public GiveAgeSubProcess() {
        super("GIVE_AGE");
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
