package ninoo_jobs.jobs_cmds.jobs_admin_commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobsPermissionCommand {
    public void doCommand(Player player, String[] strings, Plugin plugin) {
            if (player.hasPermission("jobs.admin.permission")) {
                player.sendMessage(ChatColor.RED + "Jobs_Permissions:");
                player.sendMessage(ChatColor.GOLD + "jobs.admin.give");
                player.sendMessage(ChatColor.GOLD + "jobs.admin.reload");
                player.sendMessage(ChatColor.GOLD + "jobs.admin.remove");
                player.sendMessage(ChatColor.GOLD + "bounty.open");
                player.sendMessage(ChatColor.GOLD + "jobs.list");
                player.sendMessage(ChatColor.GOLD + "jobs.leave");
                player.sendMessage(ChatColor.GOLD + "jobs.job.jobname");
                player.sendMessage(ChatColor.GOLD + "JobsShop.use");
                player.sendMessage(ChatColor.GOLD + "jobs.info");
            } else {
                player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
            }
        }
}
