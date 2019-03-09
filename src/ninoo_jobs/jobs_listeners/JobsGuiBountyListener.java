package ninoo_jobs.jobs_listeners;


import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_cmds.JobsBountyCommand;
import ninoo_jobs.jobs_helpclasses.JobsRItem;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

public class JobsGuiBountyListener implements Listener {
    Plugin plugin;
    @EventHandler
    public void openChest(InventoryClickEvent event) {
        if(event.getInventory().getTitle().equalsIgnoreCase("Belohnungen")){
            if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
                event.setCancelled(true);
            }
            if(event.getSlot() == -999){
                return;
            }
            if(event.getCurrentItem() == null){
                return;
            }
            if(!event.getClickedInventory().getName().equalsIgnoreCase("Belohnungen")){
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
            try {
                JobsDBManager jobsDbManager =new JobsDBManager();
                jobsDbManager.setPlugin(plugin);
                jobsDbManager.mySQLConnection();
                if(event.getWhoClicked() instanceof Player){
                    Player player= (Player) event.getWhoClicked();
                    if(player.hasPermission("bounty.achieve")){
                        List<String> entries= jobsDbManager.getEntries(player.getUniqueId().toString());
                        if(entries!=null){
                            for (int i = 0; i < entries.size(); i++) {
                                String[] ent = entries.get(i).split("-");
                                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ent[0])) {
                                        List<String> lores = event.getCurrentItem().getItemMeta().getLore();
                                        if (lores.get(0).equalsIgnoreCase("Rarity: " + ent[1]) && lores.get(1).equalsIgnoreCase("Count: " + ent[2]) && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ent[0])) {
                                            List<JobsRItem> items = JobsMain.giveTreasure(ent[1]);
                                            if(items!=null){
                                                if(items.size()>0){
                                                    for (int j = 0; j < items.size(); j++) {
                                                        if(items.get(j)!=null){
                                                            if(items.get(j).Rarity.equalsIgnoreCase(ent[1])){
                                                                if(chance(JobsMain.treasureItemList.get(i).Chance)){
                                                                    if(chance(JobsMain.treasureItemList.get(i).JobsTreasureItemsItemsList.get(j).Chance)){
                                                                        ItemStack item=items.get(j).contruct();
                                                                        if(item!=null){
                                                                            player.getInventory().addItem(item);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    player.sendMessage(plugin.getConfig().getString("Messages.bountyachieve").replace("%bounty%", ent[0]));
                                                    check_payout(player, ent[0]);
                                                    closeDeleteAndOpenBounty(jobsDbManager, ent[0], ent[1], player);
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
                jobsDbManager.close();
            }catch (Exception e){
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("Nino has to do some work .. fml");
            }
        }
    }
    public static Inventory buildFrame(Inventory inv){
        Material material=Material.GRAY_STAINED_GLASS_PANE;
        inv.setItem(0, new ItemStack(material));
        inv.setItem(1, new ItemStack(material));
        inv.setItem(2, new ItemStack(material));
        inv.setItem(3, new ItemStack(material));
        inv.setItem(4, new ItemStack(material));
        inv.setItem(5, new ItemStack(material));
        inv.setItem(6, new ItemStack(material));
        inv.setItem(7, new ItemStack(material));
        inv.setItem(8, new ItemStack(material));
        return inv;
    }
    public void check_payout(Player player, String bountyname){
        try {
            for (int i = 0; i < JobsMain.bountylist.size(); i++) {
                if(JobsMain.bountylist.get(i).Name.equalsIgnoreCase(bountyname)){
                    Random random= new Random();
                    double x = random.nextDouble();
                    if(x<=JobsMain.bountylist.get(i).Cashchance/100){
                        JobsMain.economy.depositPlayer(player, JobsMain.bountylist.get(i).Cash);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("Jobs - Error - Check Config for bountynames");
        }
    }
    public void closeDeleteAndOpenBounty(JobsDBManager jobsDbManager, String ent0, String ent1, Player player){
        jobsDbManager.removeBountyEntry(player.getUniqueId().toString(), ent0, ent1);
        player.closeInventory();
        //player.getInventory().setContents(JobsBountyCommand.getBountyGuiContent(player, plugin).getContents());
        try {
            player.openInventory(JobsBountyCommand.getBountyGuiContent(player, plugin));
        }catch (Exception e){
            Bukkit.getConsoleSender().sendMessage("Jobs - Error - Maybe something with the BountyConfig? (Names of Items for ex.)");
        }
    }
    public boolean chance(double c){
        Random r=new Random();
        double x=r.nextDouble();
        if(x<=c){
            return true;
        }
        return false;
    }
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
