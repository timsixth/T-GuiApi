package pl.timsixth.gui.libray.model.process.impl;

import lombok.Setter;
import pl.timsixth.gui.libray.model.process.SubGuiProcess;

import java.util.HashMap;
import java.util.Map;
@Setter
public abstract class AbstractSubGuiProcess extends AbstractGuiProcess implements SubGuiProcess {

    private SubGuiProcess nextProcess;
    private SubGuiProcess previousProcess;
    private final Map<String, Object> transformedData;

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
