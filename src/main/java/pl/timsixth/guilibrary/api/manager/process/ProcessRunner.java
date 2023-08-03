package pl.timsixth.guilibrary.api.manager.process;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.timsixth.guilibrary.api.model.process.MainGuiProcess;
import pl.timsixth.guilibrary.api.model.process.SubGuiProcess;

import java.util.*;

@Getter
public final class ProcessRunner {
    private static final Map<UUID, MainGuiProcess> processes = new HashMap<>();

    private ProcessRunner() {
    }

    public static void runProcess(Player player, MainGuiProcess mainGuiProcess) {
        List<SubGuiProcess> subGuiProcesses = configureSubProcesses(mainGuiProcess.getGuiProcesses());

        mainGuiProcess.setGuiProcesses(subGuiProcesses);

        processes.put(player.getUniqueId(), mainGuiProcess);

        runSubProcess(player, subGuiProcesses.get(0));

        mainGuiProcess.setEnded(true);
        processes.remove(player.getUniqueId());
    }

    private static void runSubProcess(Player player, SubGuiProcess subGuiProcess) {
        if (subGuiProcess.isEnded()) return;
        if (subGuiProcess.isCanceled()) return;

        subGuiProcess.setStarted(true);

        player.openInventory(subGuiProcess.getInventory());

        if (subGuiProcess.isEnded()) {
            if (subGuiProcess.nextProcess() == null) return;

            runSubProcess(player, subGuiProcess.nextProcess());
        }
    }

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

    public static MainGuiProcess getCurrentUserProcess(Player player) {
        return processes.get(player.getUniqueId());
    }
}
