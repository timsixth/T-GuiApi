package pl.timsixth.guilibrary.example.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.core.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.core.model.Menu;
import pl.timsixth.guilibrary.core.model.pagination.PaginatedMenu;
import pl.timsixth.guilibrary.example.GuiApiPlugin;
import pl.timsixth.guilibrary.example.process.CreateUserProcess;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class TestGuiCommand implements CommandExecutor {

    private final AbstractMenuManager menuManager;
    private final GuiApiPlugin guiApiPlugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            Optional<Menu> menuOptional = menuManager.getMenuByName("test");

            menuOptional.ifPresent(menu -> player.openInventory(menuManager.createMenu(menu)));

            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("memory_gui")) {
                menuManager.getMenuByName("in_memory_gui").ifPresent(menu -> player.openInventory(menuManager.createMenu(menu)));
            } else if (args[0].equalsIgnoreCase("process")) {
                ProcessRunner.runProcess(player, new CreateUserProcess(menuManager, guiApiPlugin));
            } else if (args[0].equalsIgnoreCase("page")) {
                Optional<PaginatedMenu> menuOptional = menuManager.getPaginatedMenu("paginatedMenu");

                if (!menuOptional.isPresent()) {
                    return true;
                }

                PaginatedMenu paginatedMenu = menuOptional.get();

                Optional<Inventory> paginatedInventory = menuManager.createPaginatedMenu(paginatedMenu);

                paginatedInventory.ifPresent(player::openInventory);
            } else if (args[0].equalsIgnoreCase("placeholder")) {
                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("{TEST}","123");
                placeholders.put("{username}", player.getName());

                menuManager.getMenuByName("test2").ifPresent(menu -> player.openInventory(menuManager.createMenu(menu, placeholders)));
            }
        }

        return false;
    }
}
