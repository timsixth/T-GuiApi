package pl.timsixth.guilibrary.example.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.guilibrary.example.GuiApiPlugin;

import java.io.File;

@Getter
public final class ConfigFile {

    private final File guisFile;
    private final YamlConfiguration ymlGuis;
    @Getter(AccessLevel.NONE)
    private final GuiApiPlugin guiApiPlugin;

    public ConfigFile(GuiApiPlugin guiApiPlugin){
        this.guiApiPlugin = guiApiPlugin;

        guisFile = createFile("guis.yml");

        ymlGuis = YamlConfiguration.loadConfiguration(guisFile);
    }

    private File createFile(String name) {
        if (!guiApiPlugin.getDataFolder().exists()) {
            guiApiPlugin.getDataFolder().mkdir();
        }
        File file = new File(guiApiPlugin.getDataFolder(), name);
        if (!file.exists()) {
            guiApiPlugin.saveResource(name, true);
        }
        return file;
    }
}
