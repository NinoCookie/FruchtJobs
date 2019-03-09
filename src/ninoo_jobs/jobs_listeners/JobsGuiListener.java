package ninoo_jobs.jobs_listeners;

import net.md_5.bungee.api.ChatColor;
import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_cmds.JobsCommand;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class JobsGuiListener implements Listener {
    Plugin plugin;

    public JobsGuiListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJobsGuiOpen(InventoryClickEvent event){
        if(event.getClickedInventory().getTitle().equalsIgnoreCase("Triggers")) {
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                event.setCancelled(true);
                return;
            }
            if(event.getRawSlot() == -999 || event.getSlot()<0){
                return;
            }
            if (event.getSlot() == -999 || event.getSlot()<0) {
                return;
            }
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            if (event.getClick().isShiftClick()) {
                event.setCancelled(true);
                return;
            }
            Inventory in=event.getClickedInventory();
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().openInventory(in);
        }
        if(event.getClickedInventory().getTitle().equalsIgnoreCase("Jobs")){
            if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
                event.setCancelled(true);
            }
            if(event.getSlot() == -999){
                return;
            }
            if(event.getCurrentItem() == null){
                return;
            }
            if(event.getCurrentItem().getType() == Material.AIR){
                return;
            }
            if(event.getClick().isShiftClick()){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().equals(new ItemStack(Material.GRAY_STAINED_GLASS_PANE))){
                event.setCancelled(true);
                return;
            }
            if(event.getWhoClicked() instanceof Player){
                Player player= (Player) event.getWhoClicked();
                for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(JobsMain.jobslist.get(i).Name)){
                        if(event.getClick().isLeftClick()){
                            Inventory inv= Bukkit.getServer().createInventory(player, 9, JobsMain.jobslist.get(i).Name);
                            ItemStack item_join=new ItemStack(Material.OAK_DOOR);
                            ItemMeta meta=item_join.getItemMeta();
                            meta.setDisplayName("beitreten");
                            item_join.setItemMeta(meta);
                            ItemStack item_triggers=new ItemStack(Material.SIGN);
                            meta=item_triggers.getItemMeta();
                            meta.setDisplayName("Triggers");
                            item_triggers.setItemMeta(meta);
                            ItemStack item_leave=new ItemStack(Material.BARRIER);
                            meta=item_leave.getItemMeta();
                            meta.setDisplayName("verlassen");
                            item_leave.setItemMeta(meta);
                            ItemStack item_stats=new ItemStack(Material.BOOK);
                            meta=item_stats.getItemMeta();
                            meta.setDisplayName("Stats");
                            item_stats.setItemMeta(meta);
                            inv.setItem(0, new ItemStack(Material.AIR));
                            inv.setItem(1, item_join);
                            inv.setItem(2, new ItemStack(Material.AIR));
                            inv.setItem(3, item_triggers);
                            inv.setItem(4, new ItemStack(Material.AIR));
                            inv.setItem(5, item_stats);
                            inv.setItem(8, new ItemStack(Material.AIR));
                            inv.setItem(7, item_leave);
                            inv.setItem(8, new ItemStack(Material.AIR));
                            player.closeInventory();
                            player.openInventory(inv);
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < JobsMain.jobslist.size(); i++) {
            if(event.getClickedInventory().getTitle().equalsIgnoreCase(JobsMain.jobslist.get(i).Name)){
                if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
                    event.setCancelled(true);
                }
                if(event.getSlot() == -999){
                    return;
                }
                if(event.getCurrentItem() == null){
                    return;
                }
                if(event.getCurrentItem().getType() == Material.AIR){
                    return;
                }
                if(event.getClick().isShiftClick()){
                    event.setCancelled(true);
                    return;
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("beitreten")){
                    Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "jobs join "+JobsMain.jobslist.get(i).Name);
                    event.getWhoClicked().closeInventory();
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("verlassen")){
                    Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "jobs leave "+JobsMain.jobslist.get(i).Name);
                    event.getWhoClicked().closeInventory();
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Triggers")){
                    //Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "jobs triggers "+JobsMain.jobslist.get(i).Name);
                    Inventory tinv=Bukkit.getServer().createInventory(event.getWhoClicked(), 54, "Triggers");
                    for (int k = 0; k < JobsMain.jobslist.get(i).Triggers.size(); k++) {
                        if(JobsMain.jobslist.get(i).Triggers.size()<=54){
                            try {
                                ItemStack item1=new ItemStack(Material.matchMaterial(JobsMain.jobslist.get(i).Triggers.get(k).Name));
                                ItemMeta meta1=item1.getItemMeta();
                                List<String> lore1=new ArrayList<>();
                                lore1.add(ChatColor.GOLD+""+JobsMain.jobslist.get(i).Triggers.get(k).XP+" XP");
                                meta1.setLore(lore1);
                                item1.setItemMeta(meta1);
                                tinv.setItem(k, item1);
                            }catch (Exception e){
                                Bukkit.getConsoleSender().sendMessage("Jobs - Error -> Look in Triggers (maybe you wrote wrong itemname to display)");
                            }
                        }
                    }
                    event.getWhoClicked().openInventory(tinv);
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Stats")){
                    Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "jobs stats "+JobsMain.jobslist.get(i).Name);
                    event.getWhoClicked().closeInventory();
                }
                break;
            }
        }
    }
    public void resetInv(Player player, Plugin plugin){
        player.openInventory(JobsCommand.getJobsInv(player, plugin));
    }
}
