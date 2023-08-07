package pl.timsixth.guilibrary.example.action;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.guilibrary.core.model.MenuItem;
import pl.timsixth.guilibrary.core.model.action.AbstractAction;
import pl.timsixth.guilibrary.core.model.action.click.ClickAction;
import pl.timsixth.guilibrary.processes.model.MainGuiProcess;
import pl.timsixth.guilibrary.processes.model.SubGuiProcess;
import pl.timsixth.guilibrary.example.model.Group;
import pl.timsixth.guilibrary.example.model.User;

import java.util.Optional;

public class ChooseUserGroupAction extends AbstractAction implements ClickAction {
    public ChooseUserGroupAction() {
        super("CHOOSE_USER_GROUP");
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event, MenuItem menuItem) {
        Player player = (Player) event.getWhoClicked();

        String group = getArgs().get(0);

        MainGuiProcess currentUserProcess = ProcessRunner.getCurrentUserProcess(player);

        Optional<SubGuiProcess> processOptional = currentUserProcess.currentProcess();

        if (!processOptional.isPresent()) return;

        SubGuiProcess process = processOptional.get();

        String name = (String) process.getDatum("name");
        String surname = (String) process.getDatum("surname");
        int age = Integer.parseInt(String.valueOf(process.getDatum("age")));

        User user = new User(name, surname, age, Group.valueOf(group));

        process.setEnded(true);

        ProcessRunner.endProcess(player, currentUserProcess);

        event.setCancelled(true);
        player.closeInventory();

        player.sendMessage("You have created user with data: " + user);
    }
}
