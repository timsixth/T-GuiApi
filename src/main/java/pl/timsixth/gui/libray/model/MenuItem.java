package pl.timsixth.gui.libray.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.timsixth.gui.libray.model.action.Action;

import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class MenuItem {

    private final int slot;
    private final Material material;
    private final String displayName;
    private final List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private Action action;
    private int price;
    private int materialDataId;
}
