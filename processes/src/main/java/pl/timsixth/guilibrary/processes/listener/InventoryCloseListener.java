package pl.timsixth.guilibrary.processes.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.timsixth.guilibrary.processes.manager.ProcessRunner;
import pl.timsixth.guilibrary.processes.model.MainGuiProcess;
import pl.timsixth.guilibrary.core.util.ChatUtil;

/**
 * The listener ends current user process when player close inventory.
 */
public class InventoryCloseListener implements Listener {

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        MainGuiProcess currentUserProcess = ProcessRunner.getCurrentUserProcess(player);

        if (currentUserProcess == null) return;

        currentUserProcess.getGuiProcesses().forEach(subGuiProcess -> {
            if (event.getView().getTitle().equalsIgnoreCase(ChatUtil.chatColor(subGuiProcess.getInventoryDisplayName()))) {
                subGuiProcess.setEnded(true);
            }
        });
    }
}
