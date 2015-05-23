package Commands;

import Utilities.PRUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by ES359 on 5/23/15.
 */
public class AdminCommands implements CommandExecutor
{

    PRUtils util = new PRUtils();
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        if(cmd.getName().equalsIgnoreCase("pr") && !(sender.hasPermission("playerreports.admin")))
        {
            sender.sendMessage(util.permission("&cYou require the permission, &bplayerreports.admin"));
        }else
        {

        }
        return true;
    }
}
