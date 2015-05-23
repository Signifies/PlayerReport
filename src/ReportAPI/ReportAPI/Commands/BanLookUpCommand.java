package ReportAPI.ReportAPI.Commands;

import ReportAPI.API;
import me.ES359.PlayerReport.PlayerReport;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by ES359 on 5/22/15.
 */
public class BanLookUpCommand extends API implements CommandExecutor
{
    PlayerReport main;

    public BanLookUpCommand(PlayerReport instance)
    {
        this.main = instance;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        if(cmd.getName().equalsIgnoreCase("banlookup") && !(sender.hasPermission("br.banlookup")))
        {
            sender.sendMessage(permission("&cYou require the permission, &bbr.banlookup"));
        }else if(args.length < 1)
        {
            sender.sendMessage(color(getPrefix() + "&a/banlookup <&cplayername&a>"));
        }else if(args.length > 0)
        {
            Player target = Bukkit.getServer().getPlayer(args[0]);

            if(target == null)
            {
                sender.sendMessage(getPrefix()+color("&7Couldn't find the user, &6" + args[0] + "&7."));
            }else
            {
                lookUpBan(this.main.getSQL(),target,sender);
            }
        }
        return true;
    }

}
