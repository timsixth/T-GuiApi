package pl.timsixth.guilibrary.example.process;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.guilibrary.core.util.ChatUtil;
import pl.timsixth.guilibrary.example.GuiApiPlugin;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.guilibrary.processes.model.impl.AbstractSubGuiProcess;
import pl.timsixth.guilibrary.processes.model.input.WriteableInput;

import java.util.Arrays;
import java.util.Collections;

public class GiveAgeSubProcess extends AbstractSubGuiProcess implements WriteableInput {

    private final GuiApiPlugin guiApiPlugin;
    private final String GUI_NAME = "&a&lType user age";

    public GiveAgeSubProcess(GuiApiPlugin guiApiPlugin) {
        super("GIVE_AGE");
        this.guiApiPlugin = guiApiPlugin;
    }


    @Override
    public String getInventoryDisplayName() {
        return GUI_NAME;
    }

    @Override
    public AnvilGUI.Builder getAnvilInput() {
        return new AnvilGUI.Builder()
                .itemLeft(new ItemStack(Material.PAPER))
                .itemOutput(new ItemStack(Material.PAPER))
                .title(ChatUtil.chatColor(GUI_NAME))
                .onClick((slot, stateSnapshot) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    } else {
                        return Arrays.asList(
                                AnvilGUI.ResponseAction.close(),
                                AnvilGUI.ResponseAction.run(() -> this.transformData("name", this.getDatum("name"))),
                                AnvilGUI.ResponseAction.run(() -> this.transformData("surname", this.getDatum("surname"))),
                                AnvilGUI.ResponseAction.run(() -> this.transformData("age", stateSnapshot.getText())),
                                AnvilGUI.ResponseAction.run(() -> this.setEnded(true)),
                                AnvilGUI.ResponseAction.run(() -> ProcessRunner.runSubProcess(stateSnapshot.getPlayer(), this.nextProcess()))
                        );
                    }
                })
                .preventClose()
                .plugin(guiApiPlugin);
    }
}
