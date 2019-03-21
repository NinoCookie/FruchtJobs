package ninoo_jobs.jobs_listeners.guis;

import ninoo_jobs.jobs_Main.JobsMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class JobsQuestListener implements Listener {
    Plugin plugin;

    public JobsQuestListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        try {
            if (event.getClickedInventory() != null) {
                if (event.getClick().isShiftClick()) {
                    return;
                }
                if (event.getClickedInventory().getTitle().equalsIgnoreCase("Quests")) {
                    for (int i = 0; i < JobsMain.jobsQuestsList.size(); i++) {
                        if(JobsMain.jobsQuestsList.get(i).type.equalsIgnoreCase("unlock")){

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
