package pl.timsixth.guilibrary.example.model;

import lombok.Data;

@Data
public class User {

    private final String name;
    private final String surname;
    private final int age;
    private final Group group;
}
