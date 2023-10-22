package pl.timsixth.guilibrary.core.model.action;

import java.util.List;

/**
 * Represents every action in menu
 */
public interface Action {

    /**
     * @return action type
     * @deprecated for removal in v2.2.0
     */
    @Deprecated
    ActionType getActionType();

    /**
     * @return action name
     */
    String getName();

    /**
     * @return list of action args
     */
    List<String> getArgs();

    /**
     * Sets action args
     *
     * @param args the action args
     */
    void setArgs(List<String> args);

    /**
     * @return true if action have args, otherwise false
     */
    boolean hasArgs();
}
