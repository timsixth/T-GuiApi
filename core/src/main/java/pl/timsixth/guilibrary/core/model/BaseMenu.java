package pl.timsixth.guilibrary.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class BaseMenu {

    private int size;
    private String name;
    private String displayName;
}
