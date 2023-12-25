package pl.timsixth.guilibrary.example.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.timsixth.guilibrary.example.manager.UserManager;
import pl.timsixth.guilibrary.example.model.Group;
import pl.timsixth.guilibrary.example.model.User;

@RequiredArgsConstructor
public class UserCommand implements CommandExecutor {

    private final UserManager userManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        User user = new User("test", "123", 12, Group.ADMIN);

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("add")) {
                userManager.getUserList().add(user);
            } else if (args[0].equalsIgnoreCase("remove")) {
                userManager.getUserList().remove(user);
            }
        }

        return false;
    }
}
