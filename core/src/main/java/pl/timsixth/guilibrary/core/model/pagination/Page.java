package pl.timsixth.guilibrary.core.model.pagination;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.core.model.Menu;

@Getter
@Setter
public class Page extends Menu {

    private Inventory inventory;
    private PaginatedMenu paginatedMenu;

    public Page(int size, String name, String displayName) {
        super(size, name, displayName);
    }
}
