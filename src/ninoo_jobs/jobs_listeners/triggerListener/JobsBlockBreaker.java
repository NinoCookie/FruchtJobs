package ninoo_jobs.jobs_listeners.triggerListener;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class JobsBlockBreaker implements Listener {
    Plugin plugin;

    public JobsBlockBreaker(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        JobsDBManager jobsDbManager = new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        ArrayList<String> jobs = JobsMain.getAllPlayerJobs(event.getPlayer().getUniqueId());
        Boolean isit=false;
        if (!jobs.isEmpty()) {
            for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                for (int j = 0; j < JobsMain.jobslist.get(i).Triggers.size(); j++) {
                    if(JobsMain.jobslist.get(i).Triggers.get(j).Entity.equalsIgnoreCase("block")){
                        if (JobsMain.jobslist.get(i).Triggers.get(j).Name.equalsIgnoreCase(event.getBlock().getType().toString())) {
                            String job = JobsMain.jobslist.get(i).Name;
                            String block = event.getBlock().getType().toString();
                            Player player = event.getPlayer();
                            if (!jobsDbManager.isLocationReg(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ(), event.getBlock().getWorld().getName())) {
                                if(jobsDbManager.getlvl(player.getUniqueId().toString(), job)< JobsMain.jobslist.get(i).MaxLevel){
                                    jobsDbManager.insertxp(player.getUniqueId().toString(), job, JobsMain.jobslist.get(i).Triggers.get(j).XP);
                                    JobsMain.lvlup(player.getUniqueId(), job, JobsMain.jobslist.get(i).MaxLevel);
                                }
                                isit=true;
                                JobsMain.giveBounty(player.getUniqueId(), job, JobsMain.jobslist.get(i).Triggers.get(j).Bounties);
                            }
                        }
                    }
                }
            }
            if(isit){
                jobsDbManager.registerlocation(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ(), event.getBlock().getWorld().getName());
            }
        }
        jobsDbManager.close();
    }
}
