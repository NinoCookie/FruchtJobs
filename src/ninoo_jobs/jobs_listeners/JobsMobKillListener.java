package ninoo_jobs.jobs_listeners;


import net.minecraft.server.v1_13_R2.EntityPlayer;
import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_helpclasses.JobsTrigger;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class JobsMobKillListener implements Listener {

    Plugin plugin;

    HashMap<UUID, Double> ents = new HashMap<UUID, Double>();
    HashMap<UUID, Double> players=new HashMap<>();

    public JobsMobKillListener(Plugin plugin) {
        this.plugin=plugin;
    }

    public void addValueEnts(LivingEntity entity, double d){
        Set set = ents.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry mentry = (Map.Entry)iterator.next();
            if(mentry.getKey().equals(entity.getUniqueId())){
                double da= (double) mentry.getValue() + d;
                mentry.setValue(da);
            }
        }
    }

    public void removeEnt(LivingEntity entity){
        ents.remove(entity.getUniqueId());
    }

    public double getEntV(LivingEntity entity){
        Set set = ents.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry mentry = (Map.Entry)iterator.next();
            if(mentry.getKey().equals(entity.getUniqueId())){
                return (double) mentry.getValue();
            }
        }
        return 0;
    }

    /*@EventHandler
    public void playerdeathKill(PlayerDeathEvent event){
        JobsDBManager jobsDbManager = new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        Player killer=event.getEntity().getKiller();
        Player killed=event.getEntity();
        ArrayList<String> jobs = JobsMain.getAllPlayerJobs(killer.getUniqueId());
        if(event.getEntity().isDead()){
            if(!jobs.isEmpty()){
                for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                    for (int j = 0; j < JobsMain.jobslist.get(i).Triggers.size(); j++) {
                        String job = JobsMain.jobslist.get(i).Name;
                        if (JobsMain.jobslist.get(i).Triggers.get(j).Entity.equalsIgnoreCase("player")) {
                            if (jobsDbManager.getlvl(killer.getUniqueId().toString(), job) < JobsMain.jobslist.get(i).MaxLevel) {
                                jobsDbManager.insertxp(killer.getUniqueId().toString(), job, JobsMain.jobslist.get(i).Triggers.get(j).XP);
                                JobsMain.lvlup(killer.getUniqueId(), job, JobsMain.jobslist.get(i).MaxLevel);
                            }
                            JobsMain.giveBounty(killer.getUniqueId(), job, JobsMain.jobslist.get(i).Triggers.get(j).Bounties);
                        }
                    }
                }
            }
        }
    }*/

    @EventHandler
    public void onDmg(EntityDamageByEntityEvent event){
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        if(event.getDamager() instanceof Player){
            ArrayList<String> jobs = JobsMain.getAllPlayerJobs(event.getDamager().getUniqueId());
            if(!jobs.isEmpty()) {
                if(ents.containsKey(event.getEntity().getUniqueId())){
                    addValueEnts((LivingEntity) event.getEntity(), event.getDamage());
                }
                else{
                    ents.put(event.getEntity().getUniqueId(), event.getDamage());
                }
            }
        }
        jobsDbManager.close();
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        JobsDBManager jobsDbManager =new JobsDBManager();
        jobsDbManager.setPlugin(plugin);
        jobsDbManager.mySQLConnection();
        if(event.getEntity().isDead() && event.getEntity().getKiller() != null){
            ArrayList<String> jobs = JobsMain.getAllPlayerJobs(event.getEntity().getKiller().getUniqueId());
            if(!jobs.isEmpty()) {
                for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                    List<JobsTrigger> trigger = JobsMain.jobslist.get(i).Triggers;
                    for (int j = 0; j < trigger.size(); j++) {
                        if (trigger.get(j).Entity.equalsIgnoreCase("mob") && trigger.get(j).Name.equalsIgnoreCase(event.getEntity().getName())) {
                            String job = JobsMain.jobslist.get(i).Name;
                            LivingEntity entity = (LivingEntity) event.getEntity();
                            if (getEntV(event.getEntity()) >= event.getEntity().getMaxHealth() * (plugin.getConfig().getDouble("Events.minimumdmg") / 100)) {
                                if (jobsDbManager.getlvl(event.getEntity().getKiller().getUniqueId().toString(), job) < JobsMain.jobslist.get(i).MaxLevel) {
                                    if (event.getEntity().isDead()) {
                                        jobsDbManager.insertxp(event.getEntity().getKiller().getUniqueId().toString(), job, trigger.get(j).XP);
                                        JobsMain.lvlup(event.getEntity().getKiller().getUniqueId(), job, JobsMain.jobslist.get(i).MaxLevel);
                                        removeEnt(entity);
                                    }
                                }
                                JobsMain.giveBounty(event.getEntity().getKiller().getUniqueId(), job, JobsMain.jobslist.get(i).Triggers.get(j).Bounties);
                            }
                        }
                        if(trigger.get(j).Entity.equalsIgnoreCase("player") && event.getEntity().getType() == EntityType.PLAYER){
                            String job = JobsMain.jobslist.get(i).Name;
                            if (JobsMain.jobslist.get(i).Triggers.get(j).Entity.equalsIgnoreCase("player")) {
                                if (jobsDbManager.getlvl(event.getEntity().getKiller().getUniqueId().toString(), job) < JobsMain.jobslist.get(i).MaxLevel) {
                                    jobsDbManager.insertxp(event.getEntity().getKiller().getUniqueId().toString(), job, JobsMain.jobslist.get(i).Triggers.get(j).XP);
                                    JobsMain.lvlup(event.getEntity().getKiller().getUniqueId(), job, JobsMain.jobslist.get(i).MaxLevel);
                                }
                                JobsMain.giveBounty(event.getEntity().getKiller().getUniqueId(), job, JobsMain.jobslist.get(i).Triggers.get(j).Bounties);
                            }
                        }
                    }
                }
            }
        }
        jobsDbManager.close();
    }

}
