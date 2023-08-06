package pl.timsixth.guilibrary.api.model.process.impl;

import lombok.Data;
import pl.timsixth.guilibrary.api.model.process.GuiProcess;

@Data
public abstract class AbstractGuiProcess implements GuiProcess {

    private final String name;
    private boolean ended;
    private boolean started;
    private boolean canceled;

    @Override
    public void setEnded(boolean ended) {
        this.started = false;
        this.ended = ended;
    }
}
