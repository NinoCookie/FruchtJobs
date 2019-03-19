package ninoo_jobs.jobs_cmds.jobs_player_commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class JobsQuestCommand {
    Plugin plugin;

    public JobsQuestCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public static Inventory questInv(Player player, Plugin plugin){
        Inventory inv= Bukkit.createInventory(player, 45, ChatColor.DARK_GREEN+"Quests");
        for (int i = 0; i < inv.getSize(); i++) {
            if(i==1){
                ItemStack itemStack=new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta itemMeta=itemStack.getItemMeta();
                itemMeta.setDisplayName("Zurück");
                itemStack.setItemMeta(itemMeta);
                inv.setItem(i, itemStack);
            }
            else{
                if(i<=9){
                    inv.setItem(i, new ItemStack(Material.GREEN_WALL_BANNER));
                }
                if(i>=27){
                    inv.setItem(i, new ItemStack(Material.GREEN_WALL_BANNER));
                }
            }
        }
        return inv;
    }
}
