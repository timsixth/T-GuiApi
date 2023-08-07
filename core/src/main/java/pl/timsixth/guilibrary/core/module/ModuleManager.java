package pl.timsixth.guilibrary.core.module;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    /**
     * Registers new module
     *
     * @param module module to register
     */
    public void registerModule(Module module) {
        modules.add(module);
    }

    /**
     * Unregister module
     *
     * @param module module to unregister
     */
    public void unregisterModule(Module module) {
        modules.remove(module);
    }
}
