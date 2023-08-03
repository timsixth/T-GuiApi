package pl.timsixth.guilibrary.api.model;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents one menu
 */
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Menu {

    private int size;
    private String name;
    private String displayName;
    private EmptySlotFilling emptySlotFilling;
    @Setter
    private Set<MenuItem> items;

    public Menu(int size, String name, String displayName, EmptySlotFilling emptySlotFilling) {
        this.size = size;
        this.name = name;
        this.displayName = displayName;
        this.emptySlotFilling = emptySlotFilling;
        this.items = new HashSet<>();
    }

    public Menu(int size, String name, String displayName) {
        this.size = size;
        this.name = name;
        this.displayName = displayName;
    }
}
