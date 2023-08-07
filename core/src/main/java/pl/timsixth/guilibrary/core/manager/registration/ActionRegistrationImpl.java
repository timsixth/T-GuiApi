package pl.timsixth.guilibrary.core.manager.registration;

import pl.timsixth.guilibrary.core.model.action.Action;

import java.util.*;

/**
 * Implementation of {@link ActionRegistration}
 */
public class ActionRegistrationImpl implements ActionRegistration {

    private final Map<String, Action> actions = new HashMap<>();

    @Override
    public void register(Action action) {
        actions.put(action.getName(), action);
    }

    @Override
    public void unregister(Action action) {
        actions.remove(action.getName(),action);
    }

    @Override
    public void register(Action... actions) {
        if (actions.length == 0) {
            throw new IllegalStateException("You must add at least one action");
        }

        for (Action action : actions) {
            this.actions.put(action.getName(), action);
        }
    }

    @Override
    public List<Action> getRegistrationActions() {
        return new ArrayList<>(actions.values());
    }

    @Override
    public Optional<Action> getActionByName(String name) {
        return Optional.ofNullable(actions.get(name));
    }
}
