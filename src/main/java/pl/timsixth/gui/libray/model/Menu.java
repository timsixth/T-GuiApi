package pl.timsixth.gui.libray.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class Menu {

    private final int size;
    private final String name;
    private final String displayName;
    private EmptySlotFilling emptySlotFilling;
    @Setter
    private Set<MenuItem> items;

    public Menu(int size, String name, String displayName, EmptySlotFilling emptySlotFilling) {
        this.size = size;
        this.name = name;
        this.displayName = displayName;
        this.emptySlotFilling = emptySlotFilling;
        items = new HashSet<>();
    }
}
