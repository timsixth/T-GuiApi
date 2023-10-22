package pl.timsixth.guilibrary.core.model.action;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Temple method of Action
 * More information in {@link Action}
 */
@ToString
public abstract class AbstractAction implements Action {

    private final String name;
    @Deprecated
    private ActionType actionType;
    private List<String> args;

    @Deprecated
    public AbstractAction(String name, ActionType actionType) {
        this.name = name;
        this.actionType = actionType;
    }

    public AbstractAction(String name) {
        this.name = name;
    }

    @Deprecated
    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getArgs() {
        return args;
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public boolean hasArgs() {
        if (args == null) args = new ArrayList<>();

        return !args.isEmpty();
    }
}
