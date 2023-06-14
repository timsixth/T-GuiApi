package pl.timsixth.gui.libray.manager.registration;

import pl.timsixth.gui.libray.model.action.Action;

import java.util.List;
import java.util.Optional;

public interface ActionRegistration {

    void register(Action action);
    void unregister(Action action);
    void register(Action... actions);
    List<Action> getRegistrationActions();
    Optional<Action> getActionByName(String name);
}
