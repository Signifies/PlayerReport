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

/**
 * Remember to implement a two way checking system.
 * See if we can write the logic to check either for the player reported,
 *
 * or the player who actually submitted the report.
 */

public class CheckReportCommand extends API implements CommandExecutor
{

    PlayerReport main;

    public CheckReportCommand(PlayerReport instance)
    {
        this.main = instance;
    }



    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        if(cmd.getName().equalsIgnoreCase("checkreport") && !(sender.hasPermission("pr.checkreport")))
        {
            sender.sendMessage(permission("&cYou require the permission &bpr.checkreport"));
        }else
        {
            if(args.length < 1)
            {
                sender.sendMessage(color(getPrefix() + "&6Usage: &a/checkreport <&cplayername>"));
            }else if(args.length > 0)
            {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null)
                {
                    sender.sendMessage(color(getPrefix() + "&6The Player, &a&o" + args[0] + " &cIs invalid."));
                }else
                {
                    checkReport(this.main.getSQL(),target,sender);
                }
            }
        }
        return  true;
    }
}
