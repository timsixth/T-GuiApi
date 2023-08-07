package pl.timsixth.guilibrary.example.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.guilibrary.core.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.example.GuiApiPlugin;
import pl.timsixth.guilibrary.example.process.CreateUserProcess;

import java.util.Optional;

@RequiredArgsConstructor
public class TestCommand implements CommandExecutor {

    private final AbstractMenuManager menuManager;
    private final GuiApiPlugin guiApiPlugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            Optional<Menu> menuOptional = menuManager.getMenuByName("test");

            if (!menuOptional.isPresent()) return true;

            player.openInventory(menuManager.createMenu(menuOptional.get()));
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("memory_gui")) {
                player.openInventory(menuManager.createMenu(menuManager.getMenuByName("in_memory_gui").get()));
            } else if (args[0].equalsIgnoreCase("process")) {
                ProcessRunner.runProcess(player, new CreateUserProcess(menuManager, guiApiPlugin));
            }
        }

        return false;
    }
}
