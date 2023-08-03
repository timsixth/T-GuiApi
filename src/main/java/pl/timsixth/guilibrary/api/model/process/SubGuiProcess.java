package pl.timsixth.guilibrary.api.model.process;

import org.bukkit.inventory.Inventory;

import java.util.Map;

public interface SubGuiProcess extends GuiProcess {

    SubGuiProcess nextProcess();

    SubGuiProcess previousProcess();

    Map<String, Object> transformedData();

    Object getDatum(String key);

    void transformData(String key, Object value);

    Inventory getInventory();

    void setPreviousProcess(SubGuiProcess nextProcess);

    void setNextProcess(SubGuiProcess previousProcess);
}
