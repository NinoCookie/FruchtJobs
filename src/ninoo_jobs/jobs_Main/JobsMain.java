package ninoo_jobs.jobs_Main;

import net.milkbowl.vault.VaultEco;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import ninoo_jobs.jobs_cmds.JobsBountyCommand;
import ninoo_jobs.jobs_cmds.JobsCommand;
import ninoo_jobs.jobs_db.JobsDBManager;
import ninoo_jobs.jobs_helpclasses.*;
import ninoo_jobs.jobs_helpclasses.JobsShop;
import ninoo_jobs.jobs_listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class JobsMain extends JavaPlugin {
    private static Plugin plugin;
    public static List<JobsJob> jobslist;
    public static List<JobsBounty> bountylist;
    public static List<JobsTreasureItems> treasureItemList;
    public static List<JobsShop> shopsList;

    private static JobsCFG jobsCfg_jobs;
    private static JobsCFG jobsCfg_bounty;
    private static JobsCFG jobsCfg_treasureItems;
    private static JobsCFG jobsCFG_shop;

    public static Economy economy;

    @Override
    public void onEnable() {
        plugin=this;
        jobsCfg_jobs = new JobsCFG(this, "jobs");
        jobsCfg_bounty = new JobsCFG(this, "bounty");
        jobsCfg_treasureItems = new JobsCFG(this, "treasureItems");
        jobsCFG_shop=new JobsCFG(this, "shop");
        this.jobsCfg_jobs.setup(true);
        this.jobsCfg_bounty.setup(true);
        this.jobsCfg_treasureItems.setup(true);
        this.jobsCFG_shop.setup(true);

        createconfig();
        if(!setupEconomy()){
            Bukkit.getConsoleSender().sendMessage("Vault not found, maybe no economy plugin detected?");
        }
        else{
            Bukkit.getConsoleSender().sendMessage("Vault loaded!");
        }
        initlists(plugin);
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        jobsDbManager.createtable();
        jobsDbManager.register_location_table();
        jobsDbManager.register_bountystats_table();
        jobsDbManager.register_bounty_table();
        jobsDbManager.close();

        JobsCommand jobsCommand=new JobsCommand();
        jobsCommand.setPlugin(plugin);
        this.getCommand("jobs").setExecutor(jobsCommand);

        JobsBountyCommand jobsBountyCommand =new JobsBountyCommand();
        jobsBountyCommand.setPlugin(plugin);
        this.getCommand("bounty").setExecutor(jobsBountyCommand);

        JobsBlockBreaker jobsBlockBreaker =new JobsBlockBreaker(plugin);
        this.getServer().getPluginManager().registerEvents(jobsBlockBreaker, this);

        JobsFishListener jobsFishListener =new JobsFishListener(plugin);
        this.getServer().getPluginManager().registerEvents(jobsFishListener, this);

        JobsMobKillListener jobsMobKillListener =new JobsMobKillListener(plugin);
        this.getServer().getPluginManager().registerEvents(jobsMobKillListener, this);

        JobsGuiBountyListener jobsGuiBountyListener =new JobsGuiBountyListener();
        jobsGuiBountyListener.setPlugin(plugin);
        this.getServer().getPluginManager().registerEvents(jobsGuiBountyListener, this);

        JobsGuiListener jobsGuiListener=new JobsGuiListener(plugin);
        this.getServer().getPluginManager().registerEvents(jobsGuiListener, this);

        JobsSheepListener sheepListener=new JobsSheepListener(plugin);
        this.getServer().getPluginManager().registerEvents(sheepListener, this);

        JobsProtection jobsProtection=new JobsProtection(plugin);
        this.getServer().getPluginManager().registerEvents(jobsProtection, this);


        BukkitRunnable run=new BukkitRunnable() {
            @Override
            public void run() {
                JobsDBManager db=new JobsDBManager();
                db.setPlugin(plugin);
                db.mySQLConnection();
                db.checkAndRemoveLocationsByTime();
                db.close();
            }
        };
        Bukkit.getServer().getScheduler().runTaskTimer(this, run, 20*plugin.getConfig().getInt("Events.checkforprotectiontime"), 20*plugin.getConfig().getInt("Events.checkforprotectiontime"));
    }

    public void createconfig(){
        File file=new File(getDataFolder()+File.separator+"config.yml");
        if(!file.exists()){
            this.saveDefaultConfig();
        }
        else{
            this.reloadConfig();
        }
    }
    public static void initlists(Plugin plugin){
        plugin.reloadConfig();
        jobsCfg_jobs = new JobsCFG(plugin, "jobs");
        jobsCfg_bounty = new JobsCFG(plugin, "bounty");
        jobsCfg_treasureItems = new JobsCFG(plugin, "treasureItems");
        jobsCFG_shop=new JobsCFG(plugin, "shop");
        jobsCfg_jobs.setup(true);
        jobsCfg_bounty.setup(true);
        jobsCfg_treasureItems.setup(true);
        jobsCFG_shop.setup(true);
        jobslist=new ArrayList<JobsJob>();
        bountylist=new ArrayList<JobsBounty>();
        treasureItemList=new ArrayList<JobsTreasureItems>();
        shopsList=new ArrayList<JobsShop>();
        ConfigurationSection section= getJobsCfg_jobs().getFile().getConfigurationSection("Jobs");
        for(String key : section.getKeys(false)){
            jobslist.add(new JobsJob(section.getConfigurationSection(key)));
        }
        section= getJobsCfg_bounty().getFile().getConfigurationSection("Bounty");
        for(String key : section.getKeys(false)){
            bountylist.add(new JobsBounty(section.getConfigurationSection(key)));
        }
        section= getJobsCfg_treasureItems().getFile().getConfigurationSection("TreasureItems");
        for(String key : section.getKeys(false)){
            treasureItemList.add(new JobsTreasureItems(section.getConfigurationSection(key)));
        }
        section= getJobsCFG_shop().getFile().getConfigurationSection("Shop");
        for (String key:section.getKeys(false)){
            shopsList.add(new JobsShop(section.getConfigurationSection(key)));
        }

    }

    private boolean setupEconomy(){
        if(getServer().getPluginManager().getPlugin("Vault")==null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp= getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp==null){
            return false;
        }
        economy=rsp.getProvider();
        return true;
    }

    public static boolean lvlup(UUID uuid, String job, int maxlvl){
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        int lvl= jobsDbManager.getlvl(uuid.toString(), job);
        double xp= jobsDbManager.getxp(uuid.toString(), job);
        if(lvl>=maxlvl){
            return false;
        }
        if(xp>=(1000+(10*lvl*lvl))){
            jobsDbManager.insertlvl(uuid.toString(), job, 1);
            Bukkit.getPlayer(uuid).sendMessage(plugin.getConfig().getString("Messages.lvlup").replace("%job%", job).replace("%joblevel%", ""+ jobsDbManager.getlvl(uuid.toString(), job)));
            jobsDbManager.removexp(uuid.toString(), job, 0.0, true);
            if(xp>(1000+(10*lvl*lvl))){
                jobsDbManager.insertxp(uuid.toString(), job, xp-(1000+(10*lvl*lvl)));
            }
            jobsDbManager.close();
            return true;
        }
        jobsDbManager.close();
        return false;
    }

    public static void giveBounty(UUID uuid, String job, List<Integer> bounties){
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        int lvl= jobsDbManager.getlvl(uuid.toString(), job);
        for (int i = 0; i < JobsMain.bountylist.size(); i++) {
            for (int j = 0; j < bounties.size(); j++) {
                if(JobsMain.bountylist.get(i).Number==bounties.get(j)) {
                    if (lvl >= JobsMain.bountylist.get(i).Level) {
                        Random r = new Random();
                        double result = r.nextDouble();
                        if (result <= JobsMain.bountylist.get(i).Chance / 100) {
                            if (JobsMain.bountylist.get(i).Permission.equalsIgnoreCase("none") || Bukkit.getPlayer(uuid).hasPermission(JobsMain.bountylist.get(i).Permission)) {
                                if(JobsMain.bountylist.get(i).Stats){
                                    jobsDbManager.registerbounty(uuid.toString(), JobsMain.bountylist.get(i).Name, JobsMain.bountylist.get(i).Rarity, true);
                                }
                                if(JobsMain.bountylist.get(i).Treasure && !JobsMain.bountylist.get(i).Stats){
                                    jobsDbManager.registerbounty(uuid.toString(), JobsMain.bountylist.get(i).Name, JobsMain.bountylist.get(i).Rarity, false);
                                }
                            }
                            giveCommandReward(JobsMain.bountylist.get(i).Commands, uuid);
                        }
                    } else {
                        //shit happens mate keine chance ...
                    }
                }
            }
        }
        jobsDbManager.close();
    }

    public static ArrayList<String> getAllPlayerJobs(UUID uuid){
        ArrayList<String> jobs=new ArrayList<>();
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        for (int i = 0; i < JobsMain.jobslist.size(); i++) {
            if(jobsDbManager.hasjob(uuid.toString(), JobsMain.jobslist.get(i).Name)){
                jobs.add(JobsMain.jobslist.get(i).Name);
            }
        }
        return jobs;
    }

    public static List<JobsRItem> giveTreasure(String rarity){
        JobsBountyTreasure jobsBountyTreasure =new JobsBountyTreasure(rarity);
        jobsBountyTreasure.fillBountyTreasure();
        return jobsBountyTreasure.getItemList();
    }

    public static void giveCommandReward(List<String> commands, UUID uuid){
        if(commands.size()>0){
            for (int i = 0; i < commands.size(); i++) {
                if(!commands.get(i).equalsIgnoreCase("")){
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.get(i).replace("%player%", Bukkit.getPlayer(uuid).getDisplayName()));
                }
            }
        }
    }



    public static JobsCFG getJobsCfg_jobs() {
        return jobsCfg_jobs;
    }

    public static JobsCFG getJobsCfg_bounty() {
        return jobsCfg_bounty;
    }

    public static JobsCFG getJobsCfg_treasureItems() {
        return jobsCfg_treasureItems;
    }
    public static JobsCFG getJobsCFG_shop(){
        return jobsCFG_shop;
    }
}
