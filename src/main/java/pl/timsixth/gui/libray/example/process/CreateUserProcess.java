package pl.timsixth.gui.libray.example.process;

import pl.timsixth.gui.libray.model.process.impl.AbstractMainGuiProcess;

public class CreateUserProcess extends AbstractMainGuiProcess {
    public CreateUserProcess() {
        super("CREATE_USER_PROCESS");
        getGuiProcesses().add(new GiveNameSubProcess());
        getGuiProcesses().add(new GiveSurnameSubProcess());
        getGuiProcesses().add(new GiveAgeSubProcess());
    }
}
