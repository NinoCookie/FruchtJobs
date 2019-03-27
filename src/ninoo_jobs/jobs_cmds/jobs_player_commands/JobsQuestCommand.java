package ninoo_jobs.jobs_cmds.jobs_player_commands;

import ninoo_jobs.jobs_Main.JobsMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class JobsQuestCommand {
    Plugin plugin;

    public JobsQuestCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public static Inventory questInv(Player player, Plugin plugin){
        Inventory inv= Bukkit.createInventory(player, 54, ChatColor.DARK_GREEN+"Quests");
        for (int i = 0; i < inv.getSize(); i++) {
            if(i==0){
                ItemStack itemStack=new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta itemMeta=itemStack.getItemMeta();
                itemMeta.setDisplayName("Zurück");
                itemStack.setItemMeta(itemMeta);
                inv.setItem(i, itemStack);
            }
            else{
                if(i<=17 && i>=8){
                    inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                }
                if(i>17){
                    for (int j = 0; j < JobsMain.jobsQuestsList.size(); j++) {
                        try {
                            Bukkit.getConsoleSender().sendMessage(JobsMain.jobsQuestsList.get(j).guiicon);
                            if(JobsMain.jobsQuestsList.get(j).guiicon!=null){
                                Material m = Material.matchMaterial(JobsMain.jobsQuestsList.get(j).guiicon);
                                Bukkit.getConsoleSender().sendMessage("1");
                                ItemStack itemStack=new ItemStack(m);
                                Bukkit.getConsoleSender().sendMessage("2");
                                ItemMeta itemMeta=itemStack.getItemMeta();
                                Bukkit.getConsoleSender().sendMessage("3");
                                itemMeta.setDisplayName(JobsMain.jobsQuestsList.get(j).questname);
                                Bukkit.getConsoleSender().sendMessage("4");
                                List<String> lore = new ArrayList<>(JobsMain.jobsQuestsList.get(j).materialList);
                                Bukkit.getConsoleSender().sendMessage("5");
                                itemMeta.setLore(lore);
                                Bukkit.getConsoleSender().sendMessage("6");
                                itemStack.setItemMeta(itemMeta);
                                Bukkit.getConsoleSender().sendMessage("7");
                                inv.addItem(itemStack);
                            }
                        }catch (Exception e){
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Jobs - Error, maybe look guiicon in Quest Config!");
                        }
                    }
                }
            }

        }
        return inv;
    }
}
