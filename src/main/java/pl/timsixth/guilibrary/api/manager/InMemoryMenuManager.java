package pl.timsixth.guilibrary.api.manager;

import pl.timsixth.guilibrary.api.manager.registration.ActionRegistration;

public class InMemoryMenuManager extends AbstractMenuManager {
    public InMemoryMenuManager(ActionRegistration actionRegistration) {
        super(actionRegistration);
    }
}
