package pl.timsixth.guilibrary.api.manager;

import pl.timsixth.guilibrary.api.GUIApi;
import pl.timsixth.guilibrary.api.manager.registration.ActionRegistration;

/**
 * Default menuManager when you don't set anything in {@link GUIApi}
 * Stores menus in memory.
 */
public class InMemoryMenuManager extends AbstractMenuManager {
    public InMemoryMenuManager(ActionRegistration actionRegistration) {
        super(actionRegistration);
    }
}
