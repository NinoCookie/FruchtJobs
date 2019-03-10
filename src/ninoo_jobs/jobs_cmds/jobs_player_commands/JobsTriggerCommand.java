package ninoo_jobs.jobs_cmds.jobs_player_commands;

import net.md_5.bungee.api.ChatColor;
import ninoo_jobs.jobs_Main.JobsMain;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobsTriggerCommand {
    public void doCommand(Player player, String[] strings, Plugin plugin) {
            if (player.hasPermission("jobs.list")) {
                String job = strings[1];
                String fin = "";
                String fir = "";
                String sec = "";
                for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                    if (JobsMain.jobslist.get(i).Name.equalsIgnoreCase(job)) {
                        player.sendMessage(ChatColor.valueOf(JobsMain.jobslist.get(i).Color) + JobsMain.jobslist.get(i).Name);
                        for (int j = 0; j < JobsMain.jobslist.get(i).Triggers.size(); j++) {
                            player.sendMessage(ChatColor.valueOf(JobsMain.jobslist.get(i).Color) + JobsMain.jobslist.get(i).Triggers.get(j).Name + ChatColor.GOLD + " XP: " + ChatColor.valueOf(JobsMain.jobslist.get(i).Color) + JobsMain.jobslist.get(i).Triggers.get(j).XP + "\n");
                        }
                    }
                }
                player.sendMessage("");
            } else {
                player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
            }
        }
    }
