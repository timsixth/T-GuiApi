package pl.timsixth.guilibrary.api.model.process;

public interface GuiProcess {

    String getName();

    boolean isCanceled();

    boolean isEnded();

    boolean isStarted();

    void setCanceled(boolean canceled);

    void setEnded(boolean ended);

    void setStarted(boolean started);
}