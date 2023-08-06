package pl.timsixth.guilibrary.api.model.process.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.api.model.process.SubGuiProcess;

import java.util.HashMap;
import java.util.Map;
@Setter
public abstract class AbstractSubGuiProcess extends AbstractGuiProcess implements SubGuiProcess {

    private SubGuiProcess nextProcess;
    private SubGuiProcess previousProcess;
    private final Map<String, Object> transformedData;
    @Getter
    private Inventory inventory;

    public AbstractSubGuiProcess(String name) {
        super(name);
        transformedData = new HashMap<>();
    }

    @Override
    public SubGuiProcess nextProcess() {
        return nextProcess;
    }

    @Override
    public SubGuiProcess previousProcess() {
        return previousProcess;
    }

    @Override
    public Map<String, Object> transformedData() {
        return transformedData;
    }

    @Override
    public Object getDatum(String key) {
        return transformedData.get(key);
    }

    @Override
    public void transformData(String key, Object value) {
        nextProcess.transformedData().put(key, value);
    }
}
