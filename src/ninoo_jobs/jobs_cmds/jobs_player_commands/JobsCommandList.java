package ninoo_jobs.jobs_cmds.jobs_player_commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobsCommandList{
        public void doCommand(Player player, String[] strings, Plugin plugin) {
                player.sendMessage(ChatColor.RED + "JobsCommands:");
                player.sendMessage(ChatColor.GOLD + "/jobs join §2Jobname");
                player.sendMessage(ChatColor.GOLD + "/jobs leave §2Jobname");
                player.sendMessage(ChatColor.GOLD + "/jobs stats/info §2Jobname");
                player.sendMessage(ChatColor.GOLD + "/jobs list");
                if (player.hasPermission("jobs.give") || player.hasPermission("jobs.remove")) {
                    player.sendMessage(ChatColor.GOLD + "/jobs give level jobname playername amount");
                    player.sendMessage(ChatColor.GOLD + "/jobs give xp jobname playername amount");
                    player.sendMessage(ChatColor.GOLD + "/jobs remove level jobname playername amount");
                    player.sendMessage(ChatColor.GOLD + "/jobs remove xp jobname playername amount");
                }
                player.sendMessage(ChatColor.GOLD + "/bounty open");
                player.sendMessage(ChatColor.GOLD + "/jobs permissions");
            }
}
