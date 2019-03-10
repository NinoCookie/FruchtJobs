package ninoo_jobs.jobs_cmds.jobs_player_commands;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.regex.Pattern;

public class JobsStatsCommand{
    public void doCommand(Player player, String[] strings, Plugin plugin) {
            if (player.hasPermission("jobs.stats") || player.hasPermission("jobs.info")) {
                    String job = strings[1];
                    boolean is = false;
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
                            is = true;
                            break;
                        }
                    }
                    if (!is) {
                        player.sendMessage(plugin.getConfig().getString("Messages.noexistent").replaceAll(Pattern.quote("%job%"), job));
                    }
                } else {
                    player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                }
            }

}
