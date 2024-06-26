package pl.timsixth.guilibrary.example.manager;

import lombok.Getter;
import pl.timsixth.guilibrary.example.model.Group;
import pl.timsixth.guilibrary.example.model.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserManager {

    private final List<User> users = new ArrayList<>();

    public UserManager() {
        users.add(new User("12", "2", 12, Group.MODERATOR));
    }

    public User getUser(String username) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(username))
                .findAny()
                .orElse(null);
    }
}
