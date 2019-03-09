package ninoo_jobs.jobs_listeners;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class JobsSheepListener implements Listener {
    Plugin plugin;
    public JobsSheepListener(Plugin plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onShear(PlayerShearEntityEvent event){
        JobsDBManager jobsDbManager = new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        ArrayList<String> jobs = JobsMain.getAllPlayerJobs(event.getPlayer().getUniqueId());
        if(!jobs.isEmpty()){
            if(event.getEntity().getType().name().equalsIgnoreCase("Sheep")){
                if(event.getEntity() instanceof Sheep){
                for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                    for (int j = 0; j < JobsMain.jobslist.get(i).Triggers.size(); j++) {
                        String job = JobsMain.jobslist.get(i).Name;
                        if (JobsMain.jobslist.get(i).Triggers.get(j).Entity.equalsIgnoreCase("shear")) {
                            String[] te = JobsMain.jobslist.get(i).Triggers.get(j).Name.split("-");
                            String color = te[1];
                            if (color.equalsIgnoreCase("all")) {
                                if (jobsDbManager.getlvl(event.getPlayer().getUniqueId().toString(), job) < JobsMain.jobslist.get(i).MaxLevel) {
                                    jobsDbManager.insertxp(event.getPlayer().getUniqueId().toString(), job, JobsMain.jobslist.get(i).Triggers.get(j).XP);
                                    JobsMain.lvlup(event.getPlayer().getUniqueId(), job, JobsMain.jobslist.get(i).MaxLevel);
                                }
                                JobsMain.giveBounty(event.getPlayer().getUniqueId(), job, JobsMain.jobslist.get(i).Triggers.get(j).Bounties);
                            } else {
                                if (event.getEntity() instanceof Sheep) {
                                    Sheep sheep = (Sheep) event.getEntity();
                                    if (sheep.getColor().name().equalsIgnoreCase(color)) {
                                        if (jobsDbManager.getlvl(event.getPlayer().getUniqueId().toString(), job) < JobsMain.jobslist.get(i).MaxLevel) {
                                            jobsDbManager.insertxp(event.getPlayer().getUniqueId().toString(), job, JobsMain.jobslist.get(i).Triggers.get(j).XP);
                                            JobsMain.lvlup(event.getPlayer().getUniqueId(), job, JobsMain.jobslist.get(i).MaxLevel);
                                        }
                                        JobsMain.giveBounty(event.getPlayer().getUniqueId(), job, JobsMain.jobslist.get(i).Triggers.get(j).Bounties);
                                    }
                                }
                            }
                        }
                    }
                }
                }
            }
        }
        jobsDbManager.close();
    }
}
