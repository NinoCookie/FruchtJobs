package ninoo_jobs.jobs_listeners;

import ninoo_jobs.jobs_Main.JobsMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class JobsShop implements Listener {
    Plugin plugin;
    public JobsShop(Plugin plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onJobsShopCall(InventoryClickEvent event){
        if(event.getClickedInventory().getTitle().equalsIgnoreCase("JobsShop")){
            for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(JobsMain.jobslist.get(i).Name)){
                    List<String> lores=event.getCurrentItem().getItemMeta().getLore();
                    for (int j = 0; j < JobsMain.shopsList.size(); j++) {
                        if(JobsMain.shopsList.get(j).Entity.equalsIgnoreCase("job")){
                            if(JobsMain.shopsList.get(j).Jobname.equalsIgnoreCase(JobsMain.jobslist.get(i).Name)){
                                Player player= (Player) event.getWhoClicked();
                                if(JobsMain.economy.has(player, Integer.parseInt(lores.get(0)))){
                                    if(player.hasPermission("JobsShop.use")){
                                        JobsMain.economy.withdrawPlayer(player, Integer.parseInt(lores.get(0)));
                                        JobsMain.permission.playerAdd(player, JobsMain.jobslist.get(i).Permission);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
