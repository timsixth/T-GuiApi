package pl.timsixth.guilibrary.example;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import pl.timsixth.guilibrary.core.model.pagination.Page;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;
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

        Faker faker = new Faker();

        for (int i = 0; i < 51; i++) {
            int age = faker.number().numberBetween(10, 60);
            Group group = faker.options().option(Group.ADMIN, Group.MODERATOR);

            users.add(new User(faker.name().name(), faker.name().lastName(), age, group));
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
