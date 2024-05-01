package pl.timsixth.guilibrary.processes.model;

import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.core.model.Menu;

import java.util.Map;

/**
 * Represents every subprocess
 */
public interface SubGuiProcess extends GuiProcess {
    /**
     * @return next process
     */
    SubGuiProcess nextProcess();

    /**
     * @return previous process
     */
    SubGuiProcess previousProcess();

    /**
     * Gets transformed data from previous process
     *
     * @return transformed data
     */
    Map<String, Object> transformedData();

    /**
     * Gets datum by key
     *
     * @param key id of datum
     * @return datum value
     */
    Object getDatum(String key);

    /**
     * Transforms datum to next process
     *
     * @param key   id of datum
     * @param value value of datum
     */
    void transformData(String key, Object value);

    /**
     * Gets inventory to open.
     * Only {@link Menu}
     *
     * @return inventory
     */
    Inventory getInventory();

    /**
     * Sets next process
     *
     * @param nextProcess next process
     */
    void setPreviousProcess(SubGuiProcess nextProcess);

    /**
     * Sets previous process
     *
     * @param previousProcess previous process
     */
    void setNextProcess(SubGuiProcess previousProcess);

    /**
     * Gets inventory display name.<br>
     *
     * @return inventory display name.
     * @deprecated
     */
    @Deprecated
    String getInventoryDisplayName();
}
