package ReportAPI.ReportAPI.Commands;

import ReportAPI.API;
import me.ES359.PlayerReport.PlayerReport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by ES359 on 5/23/15.
 */
public class DeleteReportCommand extends API implements CommandExecutor
{

    PlayerReport main;

    public DeleteReportCommand(PlayerReport instance)
    {
        this.main = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        if(cmd.getName().equalsIgnoreCase("deletereport") && !(sender.hasPermission("pr.deletereport")))
        {
            sender.sendMessage(permission("&cYou require the permission, &bpr.deletereport"));
        }else
        {
            if(args.length < 1)
            {
                sender.sendMessage(color("&a/deletereport <&cID&a>"));
            }else if(args.length > 0)
            {
                deleteReport(this.main.getSQL(),Integer.parseInt(args[0]),sender);
            }
        }
        return true;
    }
}
