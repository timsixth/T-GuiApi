package pl.timsixth.guilibrary.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.Material;

/**
 * Class which represents to fill empty space in GUI
 */
@RequiredArgsConstructor
@Getter
@ToString
public class EmptySlotFilling {

    private final Material emptySlotMaterial;

}
