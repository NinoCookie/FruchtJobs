package ninoo_jobs.jobs_cmds.jobs_admin_commands;

import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobsRemoveAdminCommand {
    public void doCommand(Player player, String[] strings, Plugin plugin) {
            if (player.hasPermission("jobs.admin.remove")) {
                    String job = strings[2];
                    Player p = Bukkit.getPlayer(strings[3]);
                    double amount = Double.parseDouble(strings[4]);
                    JobsDBManager jobsDbManager = new JobsDBManager();
                    jobsDbManager.setPlugin(plugin);
                    jobsDbManager.mySQLConnection();
                    switch (strings[1]) {
                        case "level":
                            if (jobsDbManager.hasjob(p.getUniqueId().toString(), job)) {
                                jobsDbManager.removelvl(p.getUniqueId().toString(), job, (int) amount);
                            } else {
                                player.sendMessage("Player doesnt have this job");
                            }
                            break;
                        case "xp":
                            if (jobsDbManager.hasjob(p.getUniqueId().toString(), job)) {
                                jobsDbManager.removexp(p.getUniqueId().toString(), job, amount, false);
                            } else {
                                player.sendMessage("Player doesnt have this job");
                            }
                            break;
                    }
                } else {
                    player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                }
            }

}
