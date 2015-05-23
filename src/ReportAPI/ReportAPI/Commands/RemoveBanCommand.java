package ReportAPI.ReportAPI.Commands;

import ReportAPI.API;
import me.ES359.PlayerReport.PlayerReport;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by ES359 on 5/23/15.
 */
public class RemoveBanCommand extends API implements CommandExecutor
{
    PlayerReport main;

    public RemoveBanCommand(PlayerReport instance)
    {
        this.main = instance;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        if(cmd.getName().equalsIgnoreCase("removeban") && !(sender.hasPermission("pr.removeban")))
        {
            sender.sendMessage(permission("&cYou require the permission, &bpr.removeban"));
        }else if(args.length < 1)
        {
            sender.sendMessage(color(getPrefix() + "&a/removeban <&cplayername&a>"));
        }else if(args.length > 0)
        {
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null)
            {
                sender.sendMessage(getPrefix()+color("&7Couldn't find the user, &6" + args[0] + "&7."));
            }else
            {
                removeBan(this.main.getSQL(),target,sender);
            }
        }
        return true;
    }
}
