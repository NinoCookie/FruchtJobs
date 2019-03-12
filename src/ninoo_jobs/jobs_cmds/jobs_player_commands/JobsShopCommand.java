package ninoo_jobs.jobs_cmds.jobs_player_commands;

import ninoo_jobs.jobs_Main.JobsMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class JobsShopCommand {
    public void doCommand(Player player, String[] strings, Plugin plugin) {
        if(player.hasPermission("JobsShop.use")){
            player.openInventory(createInv(player, 27));
        }
    }
    public static Inventory createInv(Player player, int am){
        Inventory inventory = Bukkit.createInventory(player, am, "JobsShop");
        for (int i = 0; i < JobsMain.shopsList.size(); i++) {
            if(JobsMain.shopsList.get(i).Entity.equalsIgnoreCase("job")){

            }
        }
        return inventory;
    }
}
