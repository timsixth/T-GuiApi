package pl.timsixth.guilibrary.example.process;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.guilibrary.GuiApiPlugin;
import pl.timsixth.guilibrary.api.manager.process.ProcessRunner;
import pl.timsixth.guilibrary.api.model.process.impl.AbstractSubGuiProcess;
import pl.timsixth.guilibrary.api.model.process.input.WriteableInput;
import pl.timsixth.guilibrary.api.util.ChatUtil;

import java.util.Arrays;
import java.util.Collections;

public class GiveSurnameSubProcess extends AbstractSubGuiProcess implements WriteableInput {

    private final GuiApiPlugin guiApiPlugin;
    private final String GUI_NAME = "&a&lType user surname";

    public GiveSurnameSubProcess(GuiApiPlugin guiApiPlugin) {
        super("GIVE_SURNAME");
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
                                AnvilGUI.ResponseAction.run(() -> this.transformData("surname", stateSnapshot.getText())),
                                AnvilGUI.ResponseAction.run(() -> this.setEnded(true)),
                                AnvilGUI.ResponseAction.run(() -> ProcessRunner.runSubProcess(stateSnapshot.getPlayer(), this.nextProcess()))
                        );
                    }
                })
                .preventClose()
                .plugin(guiApiPlugin);
    }
}
