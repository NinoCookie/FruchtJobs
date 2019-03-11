package ninoo_jobs.jobs_listeners.guis;

import ninoo_jobs.jobs_Main.JobsMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class JobsShop implements Listener {
    Plugin plugin;
    public JobsShop(Plugin plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onJobsShopCall(InventoryClickEvent event) {
        try {
            if(event.getClickedInventory()!=null){
                if (event.getCurrentItem().equals(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))) {
                    return;
                }
                if(event.getClick().isShiftClick()){
                    return;
                }
                if (event.getClickedInventory().getTitle().equalsIgnoreCase("JobsShop")) {
                    for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(JobsMain.jobslist.get(i).Name)) {
                            List<String> lores = event.getCurrentItem().getItemMeta().getLore();
                            for (int j = 0; j < JobsMain.shopsList.size(); j++) {
                                if (JobsMain.shopsList.get(j).Entity.equalsIgnoreCase("job")) {
                                    if (JobsMain.shopsList.get(j).Jobname.equalsIgnoreCase(JobsMain.jobslist.get(i).Name)) {
                                        Player player = (Player) event.getWhoClicked();
                                        if (JobsMain.economy.has(player, Integer.parseInt(lores.get(0)))) {
                                            if (player.hasPermission("JobsShop.use")) {
                                                JobsMain.economy.withdrawPlayer(player, Integer.parseInt(lores.get(0)));
                                                JobsMain.permission.playerAdd(player, JobsMain.jobslist.get(i).Permission);
                                                Bukkit.getServer().dispatchCommand(player, "jobs join" + JobsMain.jobslist.get(i).Name);
                                            }
                                        }
                                        else{
                                            player.sendMessage(plugin.getConfig().getString("Messages.notenoughmoney"));
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
