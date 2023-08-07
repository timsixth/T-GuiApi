package pl.timsixth.guilibrary.example.process;

import pl.timsixth.guilibrary.example.GuiApiPlugin;
import pl.timsixth.guilibrary.api.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.api.model.impl.AbstractMainGuiProcess;

public class CreateUserProcess extends AbstractMainGuiProcess {

    public CreateUserProcess(AbstractMenuManager menuManager, GuiApiPlugin guiApiPlugin) {
        super("CREATE_USER_PROCESS");
        getGuiProcesses().add(new GiveNameSubProcess(guiApiPlugin));
        getGuiProcesses().add(new GiveSurnameSubProcess(guiApiPlugin));
        getGuiProcesses().add(new GiveAgeSubProcess(guiApiPlugin));
        getGuiProcesses().add(new ChooseUserGroupSubProcess(menuManager));
    }
}
