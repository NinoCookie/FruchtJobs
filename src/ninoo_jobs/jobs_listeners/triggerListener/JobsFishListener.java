package ninoo_jobs.jobs_listeners.triggerListener;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_helpclasses.sectionControllers.JobsTrigger;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class JobsFishListener implements Listener {
    Plugin plugin;
    public JobsFishListener(Plugin plugin) {
        this.plugin=plugin;
    }

    @EventHandler
    public void fishCatch(PlayerFishEvent event){
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        Player player=event.getPlayer();
        ArrayList<String> jobs = JobsMain.getAllPlayerJobs(event.getPlayer().getUniqueId());
        if(!jobs.isEmpty()){
            for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                List<JobsTrigger> trigger = JobsMain.jobslist.get(i).Triggers;
                for (int j = 0; j < trigger.size(); j++) {
                    if(trigger.get(j).Entity.equalsIgnoreCase("fish") && event.getState() == PlayerFishEvent.State.CAUGHT_FISH){
                        if(trigger.get(j).Name.equalsIgnoreCase(event.getCaught().getName())){
                            String job= JobsMain.jobslist.get(i).Name;
                            if(jobsDbManager.getlvl(player.getUniqueId().toString(), job)< JobsMain.jobslist.get(i).MaxLevel){
                                jobsDbManager.insertxp(player.getUniqueId().toString(), job, trigger.get(j).XP);
                                JobsMain.lvlup(player.getUniqueId(), job, JobsMain.jobslist.get(i).MaxLevel);
                            }
                            JobsMain.giveBounty(player.getUniqueId(), job, JobsMain.jobslist.get(i).Triggers.get(j).Bounties);
                        }
                    }
                }
            }
        }
    }
}
