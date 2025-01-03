package pl.timsixth.guilibrary.example.action;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractClickAction;

public class DependencyInjectionAction extends AbstractClickAction {

    private final String text;

    public DependencyInjectionAction(String text) {
        super("DEPENDENCY_INJECTION");
        this.text = text;
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Bukkit.getLogger().info(text);
    }
}
