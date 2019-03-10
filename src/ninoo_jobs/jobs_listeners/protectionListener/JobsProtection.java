package ninoo_jobs.jobs_listeners.protectionListener;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class JobsProtection implements Listener {
    Plugin plugin;
    public JobsProtection(Plugin plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onPlaceProt(BlockPlaceEvent event){
        List<String> list=JobsMain.getAllPlayerJobs(event.getPlayer().getUniqueId());
        if(list.size()>0){
            for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                for (String s : list) {
                    if (JobsMain.jobslist.get(i).Name.equalsIgnoreCase(s)) {
                        JobsDBManager dbManager = new JobsDBManager();
                        dbManager.setPlugin(plugin);
                        dbManager.mySQLConnection();
                        if (!dbManager.isLocationReg(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ(), event.getBlock().getWorld().getName())) {
                            dbManager.registerlocation(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ(), event.getBlock().getWorld().getName());
                        }
                        dbManager.close();
                    }
                }
            }
        }
    }
}
