package ReportAPI.ReportAPI.Commands;

import ReportAPI.API;
import Utilities.PRUtils;
import Utilities.Permissions;
import me.ES359.PlayerReport.PlayerReport;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by ES359 on 5/22/15.
 */
public class ReportBanCommand extends API implements CommandExecutor
{

    public PlayerReport main;

    public ReportBanCommand(PlayerReport instance)
    {
        this.main = instance;
    }

    PRUtils util = new PRUtils();
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        if(this.main.isSqlEnabled())
        {
            if(cmd.getName().equalsIgnoreCase("reportban") && !(sender.hasPermission(Permissions.PERM_BAN)))
            {
                util.permission("&cYou require the permission, &a&o" + Permissions.PERM_BAN);
            }else
            {
                if(args.length < 1)
                {
                    sender.sendMessage(util.color(util.getPrefix() +"&a/reportban <&cplayername&a> <&creason&a>"));
                }else if(args.length == 1)
                {
                    sender.sendMessage(util.getPrefix() + util.color("&a/reportban <&aplayername&c> <reason>"));
                }else if(args.length > 1)
                {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if(target == null)
                    {
                        sender.sendMessage(util.getPrefix() + util.color("&7Couldn't find the user, &6" + args[0] + "&7."));
                    }else
                    {
                        /**
                         * Implement ban logic.
                         *
                         * Make sure to get the UUID of the target banned player
                         * and store it in database.
                         */


                        StringBuilder str = new StringBuilder();

                        for (String arg : args) {
                            str.append(arg + " ");
                        }

                        String reason = str.toString().replaceAll(args[0], "");

                        createBan(this.main.getSQL(),target,reason,sender);
                    }
                }
            }
        }
        return  true;
    }
}
