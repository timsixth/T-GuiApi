package pl.timsixth.guilibrary.processes.model;

import java.util.List;
import java.util.Optional;

/**
 * Represents main process
 */
public interface MainGuiProcess extends GuiProcess {
    /**
     * Gets list of subprocesses
     *
     * @return list of subprocesses
     */
    List<SubGuiProcess> getGuiProcesses();

    /**
     * Gets subprocess by name
     *
     * @param name subprocess name
     * @return subprocess
     */
    SubGuiProcess getSubGuiProcess(String name);

    /**
     * Sets list of subprocesses
     *
     * @param guiProcesses new list of subprocesses
     */
    void setGuiProcesses(List<SubGuiProcess> guiProcesses);

    /**
     * Gets currentProcess
     *
     * @return Optional of subporocess
     */
    Optional<SubGuiProcess> currentProcess();
}
