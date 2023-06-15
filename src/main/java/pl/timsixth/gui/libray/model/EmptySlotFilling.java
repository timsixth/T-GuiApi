package pl.timsixth.gui.libray.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

/**
 * Class which represents to fill empty space in GUI
 */
@RequiredArgsConstructor
@Getter
public class EmptySlotFilling {

    private final int emptySlotId;
    private final Material emptySlotMaterial;

}
