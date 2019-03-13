package ninoo_jobs.jobs_listeners.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
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
                if (event.getCurrentItem().equals(new ItemStack(Material.GREEN_WALL_BANNER))) {
                    return;
                }
                if (event.getClickedInventory().getTitle().equalsIgnoreCase("Quests")) {

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
