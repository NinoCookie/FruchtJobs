package ninoo_jobs.jobs_cmds.jobs_admin_commands;

import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobsGiveAdminCommand{
    public void doCommand(Player player, String[] strings, Plugin plugin) {
            if (player.hasPermission("jobs.admin.give")) {
                if (strings.length == 5) {
                    String job = strings[2];
                    Player p = Bukkit.getPlayer(strings[3]);
                    double amount = Double.parseDouble(strings[4]);
                    JobsDBManager jobsDbManager = new JobsDBManager();
                    jobsDbManager.setPlugin(plugin);
                    jobsDbManager.mySQLConnection();
                    int maxlvl = 0;
                    for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                        if (job.equalsIgnoreCase(JobsMain.jobslist.get(i).Name)) {
                            maxlvl = JobsMain.jobslist.get(i).MaxLevel;
                        }
                    }
                    switch (strings[1]) {
                        case "level":
                            if (jobsDbManager.hasjob(p.getUniqueId().toString(), job)) {
                                if (amount > 0 && amount < maxlvl) {
                                    jobsDbManager.insertlvl(p.getUniqueId().toString(), job, (int) amount);
                                } else {
                                    player.sendMessage("Amount is too high!");
                                }
                            } else {
                                player.sendMessage("Player doesnt have this job");
                            }
                            break;
                        case "xp":
                            if (jobsDbManager.hasjob(p.getUniqueId().toString(), job)) {
                                if (amount > 0) {
                                    jobsDbManager.insertxp(p.getUniqueId().toString(), job, amount);
                                } else {
                                    player.sendMessage("Amount is too high!");
                                }
                            } else {
                                player.sendMessage("Player doesnt have this job");
                            }
                            break;
                    }
                } else {
                    player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                }
            } else {
                player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
            }
    }
}
