package pl.timsixth.guilibrary.core.manager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.core.model.BaseMenu;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.UserMenus;
import pl.timsixth.guilibrary.core.model.pagination.Page;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;
import pl.timsixth.guilibrary.core.util.ChatUtil;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public abstract class Pagination {

    protected final Set<PaginatedMenu> paginatedMenus = new HashSet<>();

    private final List<UserMenus> usersMenus = new ArrayList<>();

    /**
     * Creates paginated inventory
     *
     * @param menu  menu to create paginated inventory
     * @param menus menus list to register all pages
     * @return optional of inventory
     */
    Optional<Inventory> createPaginatedInventory(PaginatedMenu menu, Set<Menu> menus, Player player) {
        Optional<Page> pageOptional = menu.firstPage();

        int staticItemsAmount = 0;

        if (!menu.getPages().isEmpty()) staticItemsAmount = menu.getPages().size() * menu.getStaticItems().size();

        if (menu.getPages().isEmpty() || menu.getData().size() != (menu.getPagesItemCount() - staticItemsAmount)) {
            refreshMenusList(menu, menus);

            int latestIndex = menu.generatePages();

            int nextIndex = 0;

            for (Page page : menu.getPages()) {
                nextIndex = menu.setPageData(page, nextIndex);
            }

            generateExtraPage(menu, latestIndex, nextIndex);

            createInventory(menu);

            if (player != null) {
                for (Page page : menu.getPages()) {
                    if (page.getPlayerUUID() != null) continue;

                    page.setPlayerUUID(player.getUniqueId());
                }
            }

            menus.addAll(menu.getPages());

            return menu.firstPage().map(Page::getInventory);
        }

        return pageOptional.map(Page::getInventory);
    }

    /**
     * Refreshes menus list
     *
     * @param menu  paginated menu to refresh pages
     * @param menus menus to refresh
     */
    private void refreshMenusList(PaginatedMenu menu, Set<Menu> menus) {
        List<Menu> pagesAsMenus = getPagesAsMenus(menu, menus);

        pagesAsMenus.forEach(menus::remove);
    }

    /**
     * Generates extra page
     *
     * @param menu        paginated menu with data to generate extra page
     * @param latestIndex latest page index in list
     * @param nextIndex   latest index in data list
     */
    private void generateExtraPage(PaginatedMenu menu, int latestIndex, int nextIndex) {
        if (menu.getData().size() < menu.getItemsPerPage()) return;

        int staticItemsAmount = menu.getPages().size() * menu.getStaticItems().size();

        if (menu.getData().size() > (menu.getPagesItemCount() - staticItemsAmount)) {
            Page page = menu.generateExtraPage(latestIndex + 1);

            menu.setPageData(page, nextIndex);
        }
    }

    /**
     * Creates inventory with items
     *
     * @param menu paginated menu with data to create inventory
     */
    private void createInventory(PaginatedMenu menu) {
        for (Page page : menu.getPages()) {
            Inventory inventory = Bukkit.createInventory(null, menu.getSize(), ChatUtil.chatColor(page.getDisplayName()));

            for (MenuItem item : page.getItems()) {
                inventory.setItem(item.getSlot(), item.toItemStack());
            }

            page.getItems().addAll(menu.getStaticItems());

            for (MenuItem staticItem : menu.getStaticItems()) {
                inventory.setItem(staticItem.getSlot(), staticItem.toItemStack());
            }

            page.setInventory(inventory);
        }
    }

    /**
     * Gets paginated menu by name
     *
     * @param name paginated menu's name
     * @return Optional of Paginated menu
     */
    public Optional<PaginatedMenu> getPaginatedMenu(String name) {
        return paginatedMenus.stream()
                .filter(menu -> menu.getName().equalsIgnoreCase(name))
                .findAny();
    }

    /**
     * Adds paginated menu
     *
     * @param menu paginated menu to add
     */
    public void addPaginatedMenu(PaginatedMenu menu) {
        paginatedMenus.add(menu);
    }

    /**
     * Gets list of user's menu
     *
     * @param uuid user's uuid
     * @return optional of menu
     */
    private Optional<UserMenus> getUserMenusByUuid(UUID uuid) {
        return usersMenus.stream()
                .filter(userMenus -> userMenus.getUuid().equals(uuid))
                .findAny();
    }

    /**
     * Creates menu for one player
     *
     * @param player player to create menu
     * @param menu   to create paginated inventory
     * @param menus  menus list to register all pages
     * @return optional of inventory
     */
    Optional<Inventory> createPaginatedInventory(Player player, PaginatedMenu menu, Set<Menu> menus) {
        Optional<UserMenus> userMenusOptional = getUserMenusByUuid(player.getUniqueId());

        if (userMenusOptional.isPresent()) {
            UserMenus userMenus = userMenusOptional.get();

            Optional<PaginatedMenu> paginatedMenuOptional = userMenus.getPaginatedMenuByName(menu.getName());
            if (!paginatedMenuOptional.isPresent()) {
                PaginatedMenu paginatedMenu = createPaginatedMenuForPlayer(menu, userMenus);

                return createPaginatedInventory(paginatedMenu, menus, player);
            }

            PaginatedMenu paginatedMenu = paginatedMenuOptional.get();

            userMenus.refreshPaginatedMenu(paginatedMenu, menu.getData());

            return createPaginatedInventory(paginatedMenu, menus, player);
        }

        UserMenus userMenus = new UserMenus(player.getUniqueId());

        PaginatedMenu paginatedMenu = createPaginatedMenuForPlayer(menu, userMenus);

        return createPaginatedInventory(paginatedMenu, menus, player);
    }

    /**
     * Creates menu for one player
     *
     * @param menu      menu to switch to player mode
     * @param userMenus object which stores list of player's menus
     * @return cloned paginated menu
     */
    private PaginatedMenu createPaginatedMenuForPlayer(PaginatedMenu menu, UserMenus userMenus) {
        PaginatedMenu paginatedMenu = userMenus.switchMenuForPlayerMode(menu);

        usersMenus.add(userMenus);

        return paginatedMenu;
    }

    /**
     * Gets pages as menus
     *
     * @param paginatedMenu paginated menu to get pages
     * @param menus         list of menus
     * @return pages as menus
     */
    private List<Menu> getPagesAsMenus(PaginatedMenu paginatedMenu, Set<Menu> menus) {
        List<Menu> pagesAsMenus = new ArrayList<>();

        List<String> menusNames = paginatedMenu.getPages().stream()
                .map(BaseMenu::getName)
                .collect(Collectors.toList());

        menus.forEach(menu -> menusNames.forEach(menuName -> {
            if (menu.getName().equalsIgnoreCase(menuName)) {
                pagesAsMenus.add(menu);
            }
        }));

        return pagesAsMenus;
    }

    public void refreshPaginatedMenuForPlayer(Player player, PaginatedMenu menu, Set<Menu> menus) {
        refreshMenusList(menu, menus);
        //menu.refreshPages();

        menu.getPages().forEach(menus::remove);

        Optional<UserMenus> userMenusOptional = getUserMenusByUuid(player.getUniqueId());

        if (!userMenusOptional.isPresent()) return;

        UserMenus userMenus = userMenusOptional.get();

        Optional<PaginatedMenu> paginatedMenuOptional = userMenus.getPaginatedMenuByName(menu.getName());

        if (!paginatedMenuOptional.isPresent()) return;

        //userMenus.removePaginatedMenu(paginatedMenuOptional.get());
    }
}
