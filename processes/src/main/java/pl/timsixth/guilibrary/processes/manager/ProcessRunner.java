package pl.timsixth.guilibrary.processes.manager;

import lombok.Getter;
import net.wesjd.anvilgui.version.VersionMatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.timsixth.guilibrary.core.util.ChatUtil;
import pl.timsixth.guilibrary.processes.model.MainGuiProcess;
import pl.timsixth.guilibrary.processes.model.SubGuiProcess;
import pl.timsixth.guilibrary.processes.model.input.WriteableInput;

import java.util.*;

/**
 * Manages processes
 */
@Getter
public final class ProcessRunner {
    private static final Map<UUID, MainGuiProcess> processes = new HashMap<>();

    private ProcessRunner() {
    }

    /**
     * Runs new process
     *
     * @param player         player which calls new process
     * @param mainGuiProcess process to run
     */
    public static void runProcess(Player player, MainGuiProcess mainGuiProcess) {
        List<SubGuiProcess> subGuiProcesses = configureSubProcesses(mainGuiProcess.getGuiProcesses());

        mainGuiProcess.setGuiProcesses(subGuiProcesses);

        processes.put(player.getUniqueId(), mainGuiProcess);

        runSubProcess(player, subGuiProcesses.get(0));
    }

    /**
     * Runs subProcess
     *
     * @param player        player which calls new process
     * @param subGuiProcess subprocess to run
     */
    public static void runSubProcess(Player player, SubGuiProcess subGuiProcess) {
        if (subGuiProcess.isEnded()) return;
        if (subGuiProcess.isCanceled()) return;

        subGuiProcess.setStarted(true);

        if (subGuiProcess instanceof WriteableInput) {
            WriteableInput writeable = (WriteableInput) subGuiProcess;
            try {
                new VersionMatcher().match();

                writeable.getAnvilInput().open(player);
            } catch (IllegalStateException ex) {
                player.sendMessage(ChatUtil.chatColor("&cThis " + Bukkit.getBukkitVersion() + " version is not supported"));
                Bukkit.getLogger().severe(ex.getMessage());
            }

            return;
        }

        player.openInventory(subGuiProcess.getInventory());
    }

    /**
     * Sets nextProcess and previousProcess in {@link SubGuiProcess}
     *
     * @param subGuiProcessList list of subprocesses
     * @return configured processes
     */
    private static List<SubGuiProcess> configureSubProcesses(List<SubGuiProcess> subGuiProcessList) {
        List<SubGuiProcess> subGuiProcesses = new ArrayList<>();

        for (int i = 0; i < subGuiProcessList.size(); i++) {
            if (i == 0) {
                SubGuiProcess subGuiProcess = subGuiProcessList.get(i);

                if (subGuiProcessList.size() > i + 1) {
                    subGuiProcess.setNextProcess(subGuiProcessList.get(i + 1));
                }
                subGuiProcesses.add(subGuiProcess);
            } else if (i < subGuiProcessList.size() - 1) {
                SubGuiProcess subGuiProcess = subGuiProcessList.get(i);

                subGuiProcess.setPreviousProcess(subGuiProcessList.get(i - 1));

                subGuiProcess.setNextProcess(subGuiProcessList.get(i + 1));

                subGuiProcesses.add(subGuiProcess);
            } else if (i == subGuiProcessList.size() - 1) {
                SubGuiProcess subGuiProcess = subGuiProcessList.get(i);
                subGuiProcess.setPreviousProcess(subGuiProcessList.get(i - 1));

                subGuiProcesses.add(subGuiProcess);
            }
        }

        return subGuiProcesses;
    }

    /**
     * Ends process for player, ends all subprocesses
     *
     * @param player         player to end process
     * @param mainGuiProcess process to end
     */
    public static void endProcess(Player player, MainGuiProcess mainGuiProcess) {
        mainGuiProcess.setEnded(true);

        mainGuiProcess.getGuiProcesses().forEach(subGuiProcess -> subGuiProcess.setEnded(true));

        processes.remove(player.getUniqueId());
    }

    /**
     * Gets current player process
     *
     * @param player player to get process
     * @return current player process otherwise null
     */
    public static MainGuiProcess getCurrentUserProcess(Player player) {
        return processes.get(player.getUniqueId());
    }
}
