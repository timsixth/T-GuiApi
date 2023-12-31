package pl.timsixth.guilibrary.example.model;

import lombok.Data;
import org.bukkit.Material;
import pl.timsixth.guilibrary.core.model.Generable;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.custom.SendMessageAction;

import java.util.Arrays;
import java.util.Collections;

@Data
public class User implements Generable {

    private final String name;
    private final String surname;
    private final int age;
    private final Group group;

    @Override
    public MenuItem getGeneratedItem(int slot) {
        MenuItem menuItem = new MenuItem(slot, Material.PLAYER_HEAD, name + " " + surname, Arrays.asList("Age: " + age, "Group: " + group.getColor() + group));

        SendMessageAction action = new SendMessageAction();
        action.setArgs(Collections.singletonList("&cTest"));
        menuItem.setAction(action);

        return menuItem;
    }
}
