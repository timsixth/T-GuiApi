package pl.timsixth.guilibrary.api.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.timsixth.guilibrary.api.manager.process.ProcessRunner;
import pl.timsixth.guilibrary.api.model.process.MainGuiProcess;
import pl.timsixth.guilibrary.api.util.ChatUtil;

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
