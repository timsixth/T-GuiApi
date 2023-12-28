package pl.timsixth.guilibrary.example.manager;

import pl.timsixth.guilibrary.example.model.Group;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class UserRandomizer {

    public Group getRandomGroup() {
        return Group.values()[ThreadLocalRandom.current().nextInt(Group.values().length)];
    }

    public int getRandomAge() {
        return ThreadLocalRandom.current().nextInt(10, 61);
    }

    public String getRandomName() {
        List<String> names = Arrays.asList("Name1", "Name2", "Name3");

        return names.get(ThreadLocalRandom.current().nextInt(names.size()));
    }

    public String getRandomLastName() {
        List<String> lastnames = Arrays.asList("Lastname1", "Lastname2", "Lastname3");

        return lastnames.get(ThreadLocalRandom.current().nextInt(lastnames.size()));
    }

}
