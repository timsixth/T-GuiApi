package pl.timsixth.guilibrary.core.manager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.pagination.Page;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;
import pl.timsixth.guilibrary.core.util.ChatUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public abstract class Pagination {

    protected final Set<PaginatedMenu> paginatedMenus = new HashSet<>();

    /**
     * Creates paginated inventory
     *
     * @param menu  menu to create paginated inventory
     * @param menus menus list to register all pages
     * @return optional of inventory
     */
    Optional<Inventory> createPaginatedInventory(PaginatedMenu menu, Set<Menu> menus) {
        Optional<Page> pageOptional = menu.firstPage();

        int staticItemsAmount = 0;

        if (!menu.getPages().isEmpty()) staticItemsAmount = menu.getPages().size() * menu.getStaticItems().size();

        if (menu.getPages().isEmpty() || menu.getData().size() > (menu.getPagesItemCount() - staticItemsAmount)) {

            int latestIndex = menu.generatePages();

            int nextIndex = 0;

            for (Page page : menu.getPages()) {
                nextIndex = menu.setPageData(page, nextIndex);
            }

            staticItemsAmount = menu.getPages().size() * menu.getStaticItems().size();

            if (menu.getData().size() > (menu.getPagesItemCount() - staticItemsAmount)) {
                Page page = menu.generateExtraPage(latestIndex + 1);

                menu.setPageData(page, nextIndex);
            }

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

            menus.addAll(menu.getPages());

            return menu.firstPage().map(Page::getInventory);
        }

        return pageOptional.map(Page::getInventory);
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

}
