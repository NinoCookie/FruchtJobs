package ninoo_jobs.jobs_cmds.jobs_player_commands;

import net.md_5.bungee.api.ChatColor;
import ninoo_jobs.jobs_Main.JobsMain;
import ninoo_jobs.jobs_db.JobsDBManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.regex.Pattern;

public class JobsJoinCommand {
    public void doCommand(Player player, String[] strings, Plugin plugin) {
                String job = strings[1];
                boolean is=false;
                ChatColor color=ChatColor.GRAY;
                for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                    if (JobsMain.jobslist.get(i).Name.equalsIgnoreCase(job)) {
                        if(player.hasPermission(JobsMain.jobslist.get(i).Permission)){
                            color=ChatColor.valueOf(JobsMain.jobslist.get(i).Color);
                            JobsDBManager jobsDbManager = new JobsDBManager();
                            jobsDbManager.setPlugin(plugin);
                            jobsDbManager.mySQLConnection();
                            if (!jobsDbManager.hasjob(player.getUniqueId().toString(), job)) {
                                jobsDbManager.register_playerjob(player.getUniqueId().toString(), job);
                                player.sendMessage(plugin.getConfig().getString("Messages.join").replaceAll(Pattern.quote("%job%"), color+job+ChatColor.WHITE));
                            }
                            else{
                                player.sendMessage(plugin.getConfig().getString("Messages.hasjob").replace("%job%", color+job+ChatColor.WHITE));
                            }
                            jobsDbManager.close();
                            is=true;
                            break;
                        }
                        else {
                            player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                        }
                    }
                    if(!is){
                        player.sendMessage(plugin.getConfig().getString("Messages.noexistent").replaceAll(Pattern.quote("%job%"), color+job+ChatColor.WHITE));
                    }
                }
            }


}
