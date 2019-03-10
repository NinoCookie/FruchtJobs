package ninoo_jobs.jobs_cmds.jobs_player_commands;

import net.md_5.bungee.api.ChatColor;
import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.regex.Pattern;

public class JobsLeaveCommand {
    public void doCommand(Player player, String[] strings, Plugin plugin) {
            if (player.hasPermission("jobs.leave")) {
                    ChatColor color = ChatColor.GRAY;
                    String job = strings[1];
                    boolean is = false;
                    for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                        if (JobsMain.jobslist.get(i).Name.equalsIgnoreCase(job)) {
                            color = ChatColor.valueOf(JobsMain.jobslist.get(i).Color);
                            JobsDBManager jobsDbManager = new JobsDBManager();
                            jobsDbManager.setPlugin(plugin);
                            jobsDbManager.mySQLConnection();
                            if (jobsDbManager.hasjob(player.getUniqueId().toString(), job)) {
                                jobsDbManager.remove_playerjob(player.getUniqueId().toString(), job);
                                player.sendMessage(plugin.getConfig().getString("Messages.leave").replaceAll(Pattern.quote("%job%"), color + job + ChatColor.WHITE));
                            } else {
                                player.sendMessage(plugin.getConfig().getString("Messages.hasnotjob").replace("%job%", color + job + ChatColor.WHITE));
                            }
                            jobsDbManager.close();
                            is = true;
                            break;
                        }
                    }
                    if (!is) {
                        player.sendMessage(plugin.getConfig().getString("Messages.noexistent").replaceAll(Pattern.quote("%job%"), color + job + ChatColor.WHITE));
                    }
                } else {
                    player.sendMessage(plugin.getConfig().getString("Messages.missingpara"));
                }
            }


}
