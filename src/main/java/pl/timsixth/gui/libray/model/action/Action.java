package pl.timsixth.gui.libray.model.action;

import java.util.List;

public interface Action {

    ActionType getActionType();

    String getName();

    List<String> getArgs();

    void setArgs(List<String> args);

    boolean hasArgs();
}
