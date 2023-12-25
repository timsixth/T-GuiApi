package pl.timsixth.guilibrary.core.model;

import lombok.Getter;
import lombok.ToString;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@ToString
public class UserMenus {

    private final UUID uuid;
    private final List<PaginatedMenu> menus;

    public UserMenus(UUID uuid) {
        this.uuid = uuid;
        this.menus = new ArrayList<>();
    }

    /**
     * Switches menu to player mode. Player is a mode which adds in menu name player's uuid
     *
     * @param menu menu to switch to player mode
     * @return switched menu
     */
    public PaginatedMenu switchMenuForPlayerMode(PaginatedMenu menu) {
        PaginatedMenu clonedMenu = menu.clone();

        String name = clonedMenu.getName();

        clonedMenu.setName(uuid + ";" + name);

        menus.add(clonedMenu);

        return clonedMenu;
    }

    /**
     * Gets paginated menu by name
     *
     * @param name menu's names
     * @return optional of menu
     */
    public Optional<PaginatedMenu> getPaginatedMenuByName(String name) {
        return menus.stream()
                .filter(paginatedMenu -> {
                    String menuName = paginatedMenu.getName().split(";")[1];

                    return menuName.equalsIgnoreCase(name);
                })
                .findAny();
    }

}
