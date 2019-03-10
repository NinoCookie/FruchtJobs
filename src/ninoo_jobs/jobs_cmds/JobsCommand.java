package ninoo_jobs.jobs_cmds;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
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
import java.util.regex.Pattern;

public class JobsCommand implements CommandExecutor {
    private Plugin plugin;

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length > 0) {
                if (strings[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("jobs.reload")) {
                        JobsMain.initlists(plugin);
                        player.sendMessage("&210% ... 20% ... 30% ... -> &6100%");
                    } else {
                        player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                    }
                }
                if(strings[0].equalsIgnoreCase("give")){ // /jobs give 0 level/xp 1  jobname 2 Player 3 amount 4 
                    if(player.hasPermission("jobs.give")){
                        if(strings.length==5){
                                String job=strings[2];
                                Player p= Bukkit.getPlayer(strings[3]);
                                double amount= Double.parseDouble(strings[4]);
                                JobsDBManager jobsDbManager = new JobsDBManager();
                                jobsDbManager.setPlugin(plugin);
                                jobsDbManager.mySQLConnection();
                                int maxlvl=0;
                                for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                                    if(job.equalsIgnoreCase(JobsMain.jobslist.get(i).Name)){
                                        maxlvl= JobsMain.jobslist.get(i).MaxLevel;
                                    }
                                }
                                switch (strings[1]){
                                    case "level":
                                        if(jobsDbManager.hasjob(p.getUniqueId().toString(), job)){
                                            if(amount>0 && amount<maxlvl){
                                                jobsDbManager.insertlvl(p.getUniqueId().toString(), job, (int)amount);
                                            }
                                            else{
                                                player.sendMessage("Amount is too high!");
                                            }
                                        }
                                        else{
                                            player.sendMessage("Player doesnt have this job");
                                        }
                                        break;
                                    case "xp":
                                        if(jobsDbManager.hasjob(p.getUniqueId().toString(), job)){
                                            if(amount>0){
                                                jobsDbManager.insertxp(p.getUniqueId().toString(), job, amount);
                                            }
                                            else{
                                                player.sendMessage("Amount is too high!");
                                            }
                                        }
                                        else{
                                            player.sendMessage("Player doesnt have this job");
                                        }
                                        break;
                                }
                        }
                        else{
                            player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                        }
                    }
                    else {
                        player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                    }
                }
                if(strings[0].equalsIgnoreCase("remove")){
                    if(player.hasPermission("jobs.remove")){
                        if(strings.length==5){
                            String job=strings[2];
                            Player p= Bukkit.getPlayer(strings[3]);
                            double amount= Double.parseDouble(strings[4]);
                            JobsDBManager jobsDbManager = new JobsDBManager();
                            jobsDbManager.setPlugin(plugin);
                            jobsDbManager.mySQLConnection();
                            switch (strings[1]){
                                case "level":
                                    if(jobsDbManager.hasjob(p.getUniqueId().toString(), job)){
                                        jobsDbManager.removelvl(p.getUniqueId().toString(), job, (int)amount);
                                    }
                                    else{
                                        player.sendMessage("Player doesnt have this job");
                                    }
                                    break;
                                case "xp":
                                    if(jobsDbManager.hasjob(p.getUniqueId().toString(), job)){
                                        jobsDbManager.removexp(p.getUniqueId().toString(), job, amount, false);
                                    }
                                    else{
                                        player.sendMessage("Player doesnt have this job");
                                    }
                                    break;
                            }
                        }
                        else{
                            player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                        }
                    }
                    else {
                        player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                    }
                }
                if (strings[0].equalsIgnoreCase("join")) {
                    if (strings.length == 2) {
                        String job = strings[1];
                        boolean is=false;
                        ChatColor color=ChatColor.GRAY;
                        for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                            if (JobsMain.jobslist.get(i).Name.equalsIgnoreCase(job)) {
                                if(player.hasPermission(JobsMain.jobslist.get(i).Permission)){
                                    color=ChatColor.valueOf(JobsMain.jobslist.get(i).Color);
                                    JobsDBManager jobsDbManager = new JobsDBManager();
                                    jobsDbManager.setPlugin(plugin);
                                    jobsDbManager.mySQLConnection();
                                    if (!jobsDbManager.hasjob(player.getUniqueId().toString(), job)) {
                                        jobsDbManager.register_playerjob(player.getUniqueId().toString(), job);
                                        player.sendMessage(plugin.getConfig().getString("Messages.join").replaceAll(Pattern.quote("%job%"), color+job+ChatColor.WHITE));
                                    }
                                    else{
                                        player.sendMessage(plugin.getConfig().getString("Messages.hasjob").replace("%job%", color+job+ChatColor.WHITE));
                                    }
                                    jobsDbManager.close();
                                    is=true;
                                    break;
                                }
                                else {
                                    player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                                }
                            }
                            if(!is){
                                player.sendMessage(plugin.getConfig().getString("Messages.noexistent").replaceAll(Pattern.quote("%job%"), color+job+ChatColor.WHITE));
                            }
                        }
                    }
                    else{
                        player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                    }
                }
                if (strings[0].equalsIgnoreCase("leave")) {
                    if (player.hasPermission("jobs.leave")) {
                        if (strings.length == 2) {
                            ChatColor color=ChatColor.GRAY;
                            String job = strings[1];
                            boolean is=false;
                            for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                                if (JobsMain.jobslist.get(i).Name.equalsIgnoreCase(job)) {
                                    color=ChatColor.valueOf(JobsMain.jobslist.get(i).Color);
                                    JobsDBManager jobsDbManager = new JobsDBManager();
                                    jobsDbManager.setPlugin(plugin);
                                    jobsDbManager.mySQLConnection();
                                    if (jobsDbManager.hasjob(player.getUniqueId().toString(), job)) {
                                        jobsDbManager.remove_playerjob(player.getUniqueId().toString(), job);
                                        player.sendMessage(plugin.getConfig().getString("Messages.leave").replaceAll(Pattern.quote("%job%"), color+job+ChatColor.WHITE));
                                    }
                                    else{
                                        player.sendMessage(plugin.getConfig().getString("Messages.hasnotjob").replace("%job%", color+job+ChatColor.WHITE));
                                    }
                                    jobsDbManager.close();
                                    is=true;
                                    break;
                                }
                            }
                            if(!is){
                                player.sendMessage(plugin.getConfig().getString("Messages.noexistent").replaceAll(Pattern.quote("%job%"), color+job+ChatColor.WHITE));
                            }
                        }
                        else{
                            player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                        }
                    } else {
                        player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                    }
                }
                if (strings[0].equalsIgnoreCase("stats") || strings[0].equalsIgnoreCase("info")) {
                    if (player.hasPermission("jobs.stats") || player.hasPermission("jobs.info")) {
                        if (strings.length == 2) {
                            String job = strings[1];
                            boolean is=false;
                            for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                                if (JobsMain.jobslist.get(i).Name.equalsIgnoreCase(job)) {
                                    JobsDBManager jobsDbManager = new JobsDBManager();
                                    jobsDbManager.setPlugin(plugin);
                                    jobsDbManager.mySQLConnection();
                                    if (jobsDbManager.hasjob(player.getUniqueId().toString(), job)) {
                                        player.sendMessage(jobsDbManager.showstats(player.getUniqueId().toString(), job, JobsMain.jobslist.get(i).Color));
                                    } else {
                                        player.sendMessage(plugin.getConfig().getString("Messages.nojob"));
                                    }
                                    jobsDbManager.close();
                                    is=true;
                                    break;
                                }
                            }
                            if(!is){
                                player.sendMessage(plugin.getConfig().getString("Messages.noexistent").replaceAll(Pattern.quote("%job%"), job));
                            }
                        }
                        else{
                            player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                        }
                    } else {
                        player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                    }
                }
                if (strings[0].equalsIgnoreCase("permissions")) {
                    if (player.hasPermission("jobs.permission")) {
                        player.sendMessage(ChatColor.RED+"Jobs_Permissions:");
                        player.sendMessage(ChatColor.GOLD+"jobs.join");
                        player.sendMessage(ChatColor.GOLD+"jobs.leave");
                        player.sendMessage(ChatColor.GOLD+"jobs.info/stats");
                        player.sendMessage(ChatColor.GOLD+"§6jobs.list");
                        player.sendMessage(ChatColor.GOLD+"jobs.give");
                        player.sendMessage(ChatColor.GOLD+"jobs.remove");
                        player.sendMessage(ChatColor.GOLD+"jobs.permissions");
                        player.sendMessage(ChatColor.GOLD+"bounty.achieve");
                        player.sendMessage(ChatColor.GOLD+"bounty.open");
                    }
                    else{
                        player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                    }
                }
                if(strings[0].equalsIgnoreCase("list")){
                    if(player.hasPermission("jobs.list")){
                        player.sendMessage("");
                        player.sendMessage(ChatColor.RED+"Jobsliste:");
                        for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                            TextComponent text = new TextComponent("");
                            text.setText(ChatColor.valueOf(JobsMain.jobslist.get(i).Color)+ JobsMain.jobslist.get(i).Name);
                            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Mehr infos").create()));
                            text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/jobs triggers "+ JobsMain.jobslist.get(i).Name));
                            player.spigot().sendMessage(text);
                        }
                        player.sendMessage("");
                    }
                    else{
                        player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                    }
                }
                if(strings[0].equalsIgnoreCase("triggers")){
                    if(player.hasPermission("jobs.list")){
                        String job=strings[1];
                        String fin="";
                        String fir="";
                        String sec="";
                        for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                            if(JobsMain.jobslist.get(i).Name.equalsIgnoreCase(job)){
                                player.sendMessage(ChatColor.valueOf(JobsMain.jobslist.get(i).Color)+ JobsMain.jobslist.get(i).Name);
                                for (int j = 0; j < JobsMain.jobslist.get(i).Triggers.size(); j++) {
                                    player.sendMessage(ChatColor.valueOf(JobsMain.jobslist.get(i).Color)+ JobsMain.jobslist.get(i).Triggers.get(j).Name+ChatColor.GOLD+" XP: "+ChatColor.valueOf(JobsMain.jobslist.get(i).Color)+ JobsMain.jobslist.get(i).Triggers.get(j).XP+"\n");
                                }
                            }
                        }
                        player.sendMessage("");
                    }
                    else{
                        player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                    }
                }
                if(strings[0].equalsIgnoreCase("commands")){
                    player.sendMessage(ChatColor.RED+"JobsCommands:");
                    player.sendMessage(ChatColor.GOLD+"/jobs join §2Jobname");
                    player.sendMessage(ChatColor.GOLD+"/jobs leave §2Jobname");
                    player.sendMessage(ChatColor.GOLD+"/jobs stats/info §2Jobname");
                    player.sendMessage(ChatColor.GOLD+"/jobs list");
                    if(player.hasPermission("jobs.give")||player.hasPermission("jobs.remove")){
                        player.sendMessage(ChatColor.GOLD+"/jobs give level jobname playername amount");
                        player.sendMessage(ChatColor.GOLD+"/jobs give xp jobname playername amount");
                        player.sendMessage(ChatColor.GOLD+"/jobs remove level jobname playername amount");
                        player.sendMessage(ChatColor.GOLD+"/jobs remove xp jobname playername amount");
                    }
                    player.sendMessage(ChatColor.GOLD+"/bounty open");
                    player.sendMessage(ChatColor.GOLD+"/jobs permissions");
                }
            }
            else{
                player.openInventory(getJobsInv(player, plugin));
                return true;
            }
        }        return false;
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

    public void setPlugin (Plugin plugin){
        this.plugin = plugin;
    }
}
