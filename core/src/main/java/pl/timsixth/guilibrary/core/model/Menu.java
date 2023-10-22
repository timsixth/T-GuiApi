package pl.timsixth.guilibrary.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents one menu
 */
@Getter
public class Menu extends BaseMenu {

    private EmptySlotFilling emptySlotFilling;
    @Setter
    private Set<MenuItem> items;

    public Menu(int size, String name, String displayName, EmptySlotFilling emptySlotFilling) {
        super(size, name, displayName);
        this.emptySlotFilling = emptySlotFilling;
        this.items = new HashSet<>();
    }

    public Menu(int size, String name, String displayName, EmptySlotFilling emptySlotFilling, Set<MenuItem> items) {
        super(size, name, displayName);
        this.emptySlotFilling = emptySlotFilling;
        this.items = items;
    }

    private Menu(MenuBuilder menuBuilder) {
        super(menuBuilder.size, menuBuilder.name, menuBuilder.displayName);
        this.emptySlotFilling = menuBuilder.emptySlotFilling;
        this.items = menuBuilder.items;

    }

    public Menu(int size, String name, String displayName) {
        super(size, name, displayName);
    }

    public static MenuBuilder builder() {
        return new MenuBuilder();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name=" + getName() +
                ", displayName=" + getDisplayName() +
                ", size=" + getSize() +
                ", emptySlotFilling=" + emptySlotFilling +
                ", items=" + items +
                '}';
    }

    public static class MenuBuilder {
        private String name;
        private String displayName;
        private int size;
        private EmptySlotFilling emptySlotFilling;
        private Set<MenuItem> items;


        public MenuBuilder name(String name) {
            this.name = name;

            return this;
        }

        public MenuBuilder displayName(String displayName) {
            this.displayName = displayName;

            return this;
        }

        public MenuBuilder size(int size) {
            this.size = size;

            return this;
        }

        public MenuBuilder items(Set<MenuItem> items) {
            this.items = items;

            return this;
        }

        public MenuBuilder emptySlotFilling(EmptySlotFilling emptySlotFilling) {
            this.emptySlotFilling = emptySlotFilling;

            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}
