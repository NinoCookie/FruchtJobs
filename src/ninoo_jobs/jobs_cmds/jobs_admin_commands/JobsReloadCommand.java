package ninoo_jobs.jobs_cmds.jobs_admin_commands;

import ninoo_jobs.jobs_Main.JobsMain;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobsReloadCommand{
    public void doCommand(Player player, String[] strings, Plugin plugin) {
                if (player.hasPermission("jobs.admin.reload")) {
                    JobsMain.initlists(plugin);
                    player.sendMessage("&210% ... 20% ... 30% ... -> &6100%");
                } else {
                    player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
                }
            }
    }
