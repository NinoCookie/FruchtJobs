package ninoo_jobs.jobs_listeners.guis;

import ninoo_jobs.jobs_cmds.JobsCommand;
import ninoo_jobs.jobs_cmds.jobs_bounty_commands.JobsBountyCommand;
import ninoo_jobs.jobs_cmds.jobs_player_commands.JobsShopCommand;
import ninoo_jobs.jobs_helpclasses.sectionControllers.JobsBounty;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import sun.reflect.annotation.ExceptionProxy;

public class JobsMainGuiListener implements Listener {
    Plugin plugin;

    public JobsMainGuiListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        try {
            if(event.getClickedInventory()!=null){
                if(event.getClickedInventory().getTitle().equalsIgnoreCase("FruchtJobs")){
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"Jobs")){
                        event.getWhoClicked().openInventory(JobsCommand.getJobsInv((Player) event.getWhoClicked(), plugin));
                    }
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE+"Belohnungen")){
                        event.getWhoClicked().openInventory(JobsBountyCommand.getBountyGuiContent((Player) event.getWhoClicked(), plugin));
                    }
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Shop")){
                        event.getWhoClicked().openInventory(JobsShopCommand.createInv((Player) event.getWhoClicked(), 27));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
