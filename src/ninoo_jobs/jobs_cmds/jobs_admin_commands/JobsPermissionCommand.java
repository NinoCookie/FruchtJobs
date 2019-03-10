package ninoo_jobs.jobs_cmds.jobs_admin_commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobsPermissionCommand {
    public void doCommand(Player player, String[] strings, Plugin plugin) {
            if (player.hasPermission("jobs.admin.permission")) {
                player.sendMessage(ChatColor.RED + "Jobs_Permissions:");
                player.sendMessage(ChatColor.GOLD + "jobs.join");
                player.sendMessage(ChatColor.GOLD + "jobs.leave");
                player.sendMessage(ChatColor.GOLD + "jobs.info/stats");
                player.sendMessage(ChatColor.GOLD + "§6jobs.list");
                player.sendMessage(ChatColor.GOLD + "jobs.give");
                player.sendMessage(ChatColor.GOLD + "jobs.remove");
                player.sendMessage(ChatColor.GOLD + "jobs.permissions");
                player.sendMessage(ChatColor.GOLD + "bounty.achieve");
                player.sendMessage(ChatColor.GOLD + "bounty.open");
            } else {
                player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
            }
        }
}
