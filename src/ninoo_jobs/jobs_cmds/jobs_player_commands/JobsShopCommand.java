package ninoo_jobs.jobs_cmds.jobs_player_commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobsShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player=((Player) commandSender).getPlayer();
            if(player.hasPermission("JobsShop.use")){

            }
            return true;
        }
        return true;
    }
}
