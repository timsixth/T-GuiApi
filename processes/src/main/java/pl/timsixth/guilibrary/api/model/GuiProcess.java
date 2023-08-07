package pl.timsixth.guilibrary.api.model;

/**
 * Represents GUIProcess.<br>
 * GUIProcess is process to one player.<br>
 * In process, you can run subprocesses, every subprocess runs GUI where you can make what you want.<br>
 * You can use two types of GUI Anvil and Menu, in AnvilGUI has input to write some text.<br>
 * Menu is a simple minecraft inventory.<br>
 * Library uses AnvilGUI library to make writeable inputs in anvils.
 */
public interface GuiProcess {
    /**
     * Gets process name
     *
     * @return process name
     */
    String getName();

    /**
     * Checks that process is canceled
     *
     * @return canceled true otherwise false
     */
    boolean isCanceled();

    /**
     * Checks that process is ended
     *
     * @return canceled true otherwise false
     */
    boolean isEnded();

    /**
     * Checks that process is started
     *
     * @return canceled true otherwise false
     */
    boolean isStarted();

    /**
     * Sets new value of canceled
     *
     * @param canceled param changes value of 'canceled' field
     */
    void setCanceled(boolean canceled);

    /**
     * Sets new value of ended
     *
     * @param ended param changes value of 'ended' field
     */
    void setEnded(boolean ended);

    /**
     * Sets new value of started
     *
     * @param started param changes value of 'started' field
     */
    void setStarted(boolean started);
}
