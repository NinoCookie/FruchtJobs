package ninoo_jobs.jobs_cmds;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_cmds.jobs_admin_commands.JobsGiveAdminCommand;
import ninoo_jobs.jobs_cmds.jobs_admin_commands.JobsPermissionCommand;
import ninoo_jobs.jobs_cmds.jobs_admin_commands.JobsReloadCommand;
import ninoo_jobs.jobs_cmds.jobs_admin_commands.JobsRemoveAdminCommand;
import ninoo_jobs.jobs_cmds.jobs_player_commands.*;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class JobsCommand implements CommandExecutor {
    private Plugin plugin;

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length > 0) {
                switch (strings[0]){
                    case "give":
                       if(strings.length==5){
                           JobsGiveAdminCommand jobsGiveAdminCommand=new JobsGiveAdminCommand();
                           jobsGiveAdminCommand.doCommand(player, strings, plugin);
                       }
                       else{
                           player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                       }
                       break;
                    case "permissions":
                        JobsPermissionCommand jobsPermissionCommand=new JobsPermissionCommand();
                        jobsPermissionCommand.doCommand(player, strings, plugin);
                        break;
                    case "reload":
                        JobsReloadCommand jobsReloadCommand=new JobsReloadCommand();
                        jobsReloadCommand.doCommand(player, strings, plugin);
                        break;
                    case "remove":
                        if(strings.length==5){
                            JobsRemoveAdminCommand jobsRemoveAdminCommand=new JobsRemoveAdminCommand();
                            jobsRemoveAdminCommand.doCommand(player, strings, plugin);
                        }
                        else{
                            player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                        }
                        break;
                    case "commands":
                        JobsCommandList jobsCommandList=new JobsCommandList();
                        jobsCommandList.doCommand(player, strings, plugin);
                        break;
                    case "list":
                        JobsJobsListCommand jobsJobsListCommand=new JobsJobsListCommand();
                        jobsJobsListCommand.doCommand(player, strings, plugin);
                        break;
                    case "join":
                        if(strings.length==2){
                            JobsJoinCommand jobsJoinCommand=new JobsJoinCommand();
                            jobsJoinCommand.doCommand(player, strings, plugin);
                        }
                        else{
                            player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                        }
                        break;
                    case "leave":
                        if(strings.length==2){
                            JobsLeaveCommand jobsLeaveCommand=new JobsLeaveCommand();
                            jobsLeaveCommand.doCommand(player, strings, plugin);
                        }
                        else {
                            player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                        }
                        break;
                    case "shop":
                        JobsShopCommand jobsShopCommand=new JobsShopCommand();
                        jobsShopCommand.doCommand(player, strings, plugin);
                    case "info":
                        if(strings.length==2){
                            JobsStatsCommand jobsStatsCommand=new JobsStatsCommand();
                            jobsStatsCommand.doCommand(player, strings, plugin);
                        }
                        else{
                            player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                        }
                        break;
                    case "stats":
                        if(strings.length==2){
                            JobsStatsCommand jobsStatsCommand=new JobsStatsCommand();
                            jobsStatsCommand.doCommand(player, strings, plugin);
                        }
                        else{
                            player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                        }
                        break;
                    case "triggers":
                        JobsTriggerCommand jobsTriggerCommand=new JobsTriggerCommand();
                        jobsTriggerCommand.doCommand(player, strings, plugin);
                        break;
                }
            }
            else{
                //player.openInventory(getJobsInv(player, plugin));
                player.openInventory(getMainJobsInv(player, plugin));
                return true;
            }
        }
        return false;
    }
    public static Inventory getMainJobsInv(Player player, Plugin plugin){
        Inventory inv=Bukkit.getServer().createInventory(player, 27, "FruchtJobs");
        for (int i = 0; i < inv.getSize(); i++) {
            if(i==1){
                ItemStack itemStack=new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta itemMeta=itemStack.getItemMeta();
                itemMeta.setDisplayName("Zurück");
                itemStack.setItemMeta(itemMeta);
                inv.setItem(i, itemStack);
            }
            if(i<=19){
                inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            }
            if(i>=17){
                inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            }
        }
        inv.setItem(10, getItem("Jobs", Material.IRON_SHOVEL, "GREEN"));
        inv.setItem(11, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        inv.setItem(12, getItem("Shop", Material.GOLD_INGOT, "GOLD"));
        inv.setItem(13, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        inv.setItem(14, getItem("Belohnungen", Material.ENDER_CHEST, "BLUE"));
        inv.setItem(15, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        inv.setItem(16, getItem("Quests", Material.GREEN_BANNER, ChatColor.DARK_GREEN.name()));
        return inv;
    }
    public static ItemStack getItem(String name, Material material, String color){
        ItemStack item=new ItemStack(material);
        ItemMeta itemMeta=item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.valueOf(color)+name);
        item.setItemMeta(itemMeta);
        return item;
    }
    public static Inventory getJobsInv(Player player, Plugin plugin){
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        List<String> entries= jobsDbManager.getEntries(player.getUniqueId().toString());
        int cam=0;
        if(JobsMain.jobslist.size()>9){
            if(JobsMain.jobslist.size()>18){
                if(JobsMain.jobslist.size()>27){
                    if(JobsMain.jobslist.size()>36){
                        player.sendMessage("Cannot perform command, to many jobs are available! Please contact the Administrator! Maybe more than 36 jobs available.");
                    }
                    else{
                        cam=36;
                    }
                }
                else{
                    cam=27;
                }
            }
            else{
                cam=18;
            }
        }
        else{
            cam=9;
        }
        Inventory inv=Bukkit.getServer().createInventory(player, cam, "Jobs");
        inv=buildFrame(inv);
        for (int i = 0; i < JobsMain.jobslist.size(); i++) {
            if(!(JobsMain.jobslist.get(i).GUIIcon.equalsIgnoreCase("")&&JobsMain.jobslist.get(i).GUIText.isEmpty()&&JobsMain.jobslist.get(i).GUITitle.equalsIgnoreCase(""))){
                Material m = Material.matchMaterial(JobsMain.jobslist.get(i).GUIIcon);
                try{
                    if(m.isItem()){
                        ItemStack displayitem=new ItemStack(m, 1);
                        if(displayitem!=null){
                            List<String> lor=JobsMain.jobslist.get(i).GUIText;
                            ItemMeta meta=displayitem.getItemMeta();
                            meta.setDisplayName(JobsMain.jobslist.get(i).Name);
                            meta.setLore(lor);
                            displayitem.setItemMeta(meta);
                            if(inv.getSize()<=inv.getContents().length){
                                if(inv.getSize()<=54){
                                    Inventory inventory=Bukkit.getServer().createInventory(inv.getHolder(), inv.getSize()+9, inv.getTitle());
                                    inventory.setStorageContents(inv.getStorageContents());
                                    inventory.addItem(displayitem);
                                    inv=inventory;
                                }
                            }
                            else{
                                if(inv.getSize()<54){
                                    inv.addItem(displayitem);
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    Bukkit.getConsoleSender().sendMessage("Jobs - Error - Look up Configs again!");
                    Bukkit.getConsoleSender().sendMessage("Maybe a Blockname for the GUI is wrong written!");
                }
            }
        }
        jobsDbManager.close();
        return inv;
    }
    public static Inventory buildFrame(Inventory inv){
        Material material=Material.GRAY_STAINED_GLASS_PANE;
        for (int i = 0; i < inv.getSize(); i++) {
            if(i<=9){
                inv.setItem(i, new ItemStack(material));
            }
        }
        return inv;
    }

    public void setPlugin (Plugin plugin){
        this.plugin = plugin;
    }
}
