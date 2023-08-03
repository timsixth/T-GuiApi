package pl.timsixth.gui.libray.model.process;

import java.util.List;

public interface MainGuiProcess extends GuiProcess{

    List<SubGuiProcess> getGuiProcesses();

    SubGuiProcess getSubGuiProcess(String name);

    void setGuiProcesses(List<SubGuiProcess> guiProcesses);
}
