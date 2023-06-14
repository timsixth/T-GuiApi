package pl.timsixth.gui.libray.example;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.gui.libray.manager.AbstractMenuManager;
import pl.timsixth.gui.libray.model.Menu;

import java.util.Optional;

@RequiredArgsConstructor
public class TestCommand implements CommandExecutor {

    private final AbstractMenuManager menuManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        Optional<Menu> menuOptional = menuManager.getMenuByName("test");

        if (!menuOptional.isPresent()) return true;

        player.openInventory(menuManager.createMenu(menuOptional.get()));

        return false;
    }
}
