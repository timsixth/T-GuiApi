package pl.timsixth.guilibrary.example;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.timsixth.guilibrary.api.manager.AbstractMenuManager;
import pl.timsixth.guilibrary.api.model.Menu;

import java.util.Optional;

@RequiredArgsConstructor
public class TestCommand implements CommandExecutor {

    private final AbstractMenuManager menuManager;

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
            } else if (args[0].equalsIgnoreCase("anvil")) {
                Inventory inventory = Bukkit.createInventory(null, InventoryType.ANVIL);

                player.openInventory(inventory);
            }
        }

        return false;
    }
}
