package pl.timsixth.guilibrary.api.model.input;

import net.wesjd.anvilgui.AnvilGUI;

/**
 * Represents anvil input
 */
public interface WriteableInput {
    /**
     * Gets anvil input.<br>
     * Configures settings of anvil.<br>
     * Library uses AnvilGUI to create interactive anvils
     *
     * @return Builder to create anvil input
     */
    AnvilGUI.Builder getAnvilInput();
}
