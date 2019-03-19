package ninoo_jobs.jobs_cmds.jobs_bounty_commands;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class JobsBountyCommand implements CommandExecutor {

    Plugin plugin;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player= (Player) commandSender;
            if(strings.length>0 && player.hasPermission("bounty.open")){
                if(strings[0].equalsIgnoreCase("open")){
                    JobsDBManager jobsDbManager =new JobsDBManager();
                    jobsDbManager.setPlugin(plugin);
                    jobsDbManager.mySQLConnection();
                    player.openInventory(getBountyGuiContent(player, plugin));
                }
            }
        }
        return false;
    }
    public static Inventory getBountyGuiContent(Player player, Plugin plugin){
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        List<String> entries= jobsDbManager.getEntries(player.getUniqueId().toString());
        int chestc=0;
        if(entries.size()>9){
            if(entries.size()>18){
                if(entries.size()>27){
                    if(entries.size()>36){
                        if(entries.size()>45){
                            while(entries.size()>54){
                                String[] ent=entries.get(0).split("-");
                                jobsDbManager.removeBountyEntry(player.getUniqueId().toString(), ent[0], ent[1]);
                                entries= jobsDbManager.getEntries(player.getUniqueId().toString());
                            }
                            chestc=54;
                        }
                        else{
                            chestc=45;
                        }
                    }
                    else{
                        chestc=36;
                    }
                }
                else{
                    chestc=27;
                }
            }
            else{
                chestc=18;
            }
        }
        else{
            chestc=9;
        }
        Inventory bounty= Bukkit.getServer().createInventory(player, chestc, "Belohnungen");
        for (int i = 0; i < entries.size(); i++) {
            if(i==1){
                ItemStack itemStack=new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta itemMeta=itemStack.getItemMeta();
                itemMeta.setDisplayName("Zurück");
                itemStack.setItemMeta(itemMeta);
                bounty.setItem(i, itemStack);
            }
            String[] ent=entries.get(i).split("-");
            if(ent.length>0){
                ItemStack displayitem=null;
                for (int j = 0; j < JobsMain.bountylist.size(); j++) {
                    if(ent[0].equalsIgnoreCase(JobsMain.bountylist.get(j).Name)){
                        try {
                            displayitem=new ItemStack(Material.matchMaterial(JobsMain.bountylist.get(j).Guiicon));
                        }catch (Exception e){
                            Bukkit.getConsoleSender().sendMessage("Jobs - Error - Look up GuiIcon in bountyconfig!");
                        } }
                }
                if(displayitem!=null){
                    ItemMeta meta=displayitem.getItemMeta();
                    meta.setDisplayName(ent[0]);
                    List<String> strings1=new ArrayList<>();
                    strings1.add("Rarity: "+ent[1]);
                    strings1.add("Count: "+ent[2]);
                    meta.setLore(strings1);
                    displayitem.setItemMeta(meta);
                    bounty.setItem(i, displayitem);
                }
            }
        }
        jobsDbManager.close();
        return bounty;
    }
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
