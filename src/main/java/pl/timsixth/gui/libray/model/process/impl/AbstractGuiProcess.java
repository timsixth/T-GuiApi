package pl.timsixth.gui.libray.model.process.impl;

import lombok.Data;
import pl.timsixth.gui.libray.model.process.GuiProcess;
@Data
public abstract class AbstractGuiProcess implements GuiProcess {

    private final String name;
    private boolean ended;
    private boolean started;
    private boolean canceled;

}
