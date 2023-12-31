package pl.timsixth.guilibrary.core.manager.registration;

import pl.timsixth.guilibrary.core.model.action.Action;

import java.util.List;
import java.util.Optional;

public interface ActionRegistration {
    /**
     * Registers new action
     *
     * @param action the action
     */
    void register(Action action);

    /**
     * Unregisters action
     *
     * @param action the action
     */
    void unregister(Action action);

    /**
     * Resisters more than one action
     *
     * @param actions actions to register
     */
    void register(Action... actions);

    /**
     * @return registered actions
     */
    List<Action> getRegistrationActions();

    /**
     * Gets action by name
     *
     * @param name action name
     * @return Optional of Action
     */
    Optional<Action> getActionByName(String name);
}
