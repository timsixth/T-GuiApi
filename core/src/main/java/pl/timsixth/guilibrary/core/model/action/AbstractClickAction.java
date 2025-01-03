package pl.timsixth.guilibrary.core.model.action;

import pl.timsixth.guilibrary.core.model.action.click.ClickAction;

public abstract class AbstractClickAction extends AbstractAction implements ClickAction {
    public AbstractClickAction(String name) {
        super(name);
    }
}
