package pl.timsixth.guilibrary.core.model.pagination;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.core.model.Menu;

import java.util.UUID;

@Getter
@Setter
public class Page extends Menu {

    private Inventory inventory;
    private PaginatedMenu paginatedMenu;
    private UUID playerUUID;

    public Page(int size, String name, String displayName) {
        super(size, name, displayName);
    }
}
