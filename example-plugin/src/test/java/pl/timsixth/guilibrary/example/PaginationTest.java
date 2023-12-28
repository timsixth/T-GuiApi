package pl.timsixth.guilibrary.example;

import org.junit.Before;
import org.junit.Test;
import pl.timsixth.guilibrary.core.model.pagination.Page;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;
import pl.timsixth.guilibrary.example.manager.UserRandomizer;
import pl.timsixth.guilibrary.example.model.Group;
import pl.timsixth.guilibrary.example.model.User;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PaginationTest {

    private PaginatedMenu menu;

    @Before
    public void setUp() {
        menu = new PaginatedMenu(27, "paginatedMenu", "pages");

        List<User> users = new LinkedList<>();

        UserRandomizer userRandomizer = new UserRandomizer();

        for (int i = 0; i < 55; i++) {
            int age = userRandomizer.getRandomAge();
            Group group = userRandomizer.getRandomGroup();

            users.add(new User(userRandomizer.getRandomName(), userRandomizer.getRandomLastName(), age, group));
        }

        menu.setData(users);
        menu.setItemsPerPage(10);
        menu.useDefaultStaticItems();
    }

    @Test
    public void shouldSetData() {
        int staticItemsAmount;

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

        staticItemsAmount = menu.getPages().size() * menu.getStaticItems().size();

        assertEquals(nextIndex, 50);
        assertEquals(menu.getPages().size(), 6);
        assertEquals(staticItemsAmount, 12);
    }

}
