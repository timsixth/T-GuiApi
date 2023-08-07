package pl.timsixth.guilibrary.api.model.action;

import java.util.List;

/**
 * Represents every action in menu
 */
public interface Action {
    /**
     * @return action type
     */
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
