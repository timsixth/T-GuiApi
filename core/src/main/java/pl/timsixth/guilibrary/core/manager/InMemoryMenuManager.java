package pl.timsixth.guilibrary.core.manager;

import pl.timsixth.guilibrary.core.GUIApi;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;

/**
 * Default menuManager when you don't set anything in {@link GUIApi}
 * Stores menus in memory.
 */
public class InMemoryMenuManager extends AbstractMenuManager {
    public InMemoryMenuManager(ActionRegistration actionRegistration) {
        super(actionRegistration);
    }
}
