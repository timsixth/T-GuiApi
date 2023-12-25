package pl.timsixth.guilibrary.example.manager;

import lombok.Getter;
import pl.timsixth.guilibrary.example.model.Group;
import pl.timsixth.guilibrary.example.model.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserManager {

    private final List<User> userList = new ArrayList<>();

    public UserManager() {
        userList.add(new User("12", "2", 12, Group.MODERATOR));
    }
}
