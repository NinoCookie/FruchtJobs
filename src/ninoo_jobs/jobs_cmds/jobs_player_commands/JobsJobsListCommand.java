package ninoo_jobs.jobs_cmds.jobs_player_commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ninoo_jobs.jobs_Main.JobsMain;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobsJobsListCommand {
    public void doCommand(Player player, String[] strings, Plugin plugin) {
            if (player.hasPermission("jobs.list")) {
                player.sendMessage("");
                player.sendMessage(ChatColor.RED + "Jobsliste:");
                for (int i = 0; i < JobsMain.jobslist.size(); i++) {
                    TextComponent text = new TextComponent("");
                    text.setText(ChatColor.valueOf(JobsMain.jobslist.get(i).Color) + JobsMain.jobslist.get(i).Name);
                    text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Mehr infos").create()));
                    text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/jobs triggers " + JobsMain.jobslist.get(i).Name));
                    player.spigot().sendMessage(text);
                }
                player.sendMessage("");
            } else {
                player.sendMessage(plugin.getConfig().getString("Messages.noperm"));
            }
        }
}
