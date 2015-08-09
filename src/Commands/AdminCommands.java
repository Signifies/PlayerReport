package Commands;

import Utilities.PRUtils;
import Utilities.Permissions;
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
        if(cmd.getName().equalsIgnoreCase("pr") && !(sender.hasPermission(Permissions.PERMISSION_ADMIN)))
        {
            sender.sendMessage(util.permission("&cYou require the permission, &e" + Permissions.PERMISSION_ADMIN));
        }else
        {

        }
        return true;
    }
}
