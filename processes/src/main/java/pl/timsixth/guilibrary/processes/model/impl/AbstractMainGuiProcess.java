package pl.timsixth.guilibrary.processes.model.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.timsixth.guilibrary.processes.model.MainGuiProcess;
import pl.timsixth.guilibrary.processes.model.SubGuiProcess;
import pl.timsixth.guilibrary.processes.exception.SubGuiProcessNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Template method for {@link MainGuiProcess}
 */
@Getter
@Setter
@ToString(callSuper = true)
public abstract class AbstractMainGuiProcess extends AbstractGuiProcess implements MainGuiProcess {

    private List<SubGuiProcess> guiProcesses;

    public AbstractMainGuiProcess(String name) {
        super(name);
        guiProcesses = new ArrayList<>();
    }

    @Override
    public SubGuiProcess getSubGuiProcess(String name) {
        return guiProcesses.stream()
                .filter(subGuiProcess -> subGuiProcess.getName().equalsIgnoreCase(name))
                .findAny().orElseThrow(() -> new SubGuiProcessNotFoundException(name));
    }

    @Override
    public Optional<SubGuiProcess> currentProcess() {
        return guiProcesses.stream()
                .filter(subGuiProcess -> !subGuiProcess.isEnded())
                .findFirst();
    }
}
