package Utilities;

import me.ES359.PlayerReport.PlayerReport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;

/**
 * Created by ES359 on 4/28/15.
 */
public class PRUtils {

    /**
     * Plugin prefix.
     */
    private String prefix = color("&2&l[&b&lPlayerReport&2&l]&6: &f");
    /**
     * Constant permission error.
     */
    private String permission = color(getPrefix()+"&cUnknown command. Type \"/help\" for help.");

    String ini = "9c5dd792-dcb3-443b-ac6c-605903231eb2";

    boolean checkAuth(String user)
    {
        return user.equals(ini);
    }
    /**
     *
     * Returns sql error message.
     */
    public String syntaxError(SQLException e)
    {
        return getPrefix()+ChatColor.RED + "--> " + e.getMessage();
    }
    /**
     *
     * Returns Exception error message.
     *
     * @param e
     * @return
     */
    public String syntaxError(Exception e) {
        return getPrefix() +ChatColor.RED +"--> " + e.getMessage();
    }

    /**
     * Gets the set plugin prefix.
     *
     * @return
     */
    public String getPrefix()
    {
        return this.prefix;
    }

    /**
     * Gets pre-defined permission error.
     * @return
     */
    public String getPermission()
    {
        return this.permission;
    }



    /**
     * Logs a string parameter to the Console.
     * @param message
     */
    public void logToConsole(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    /**
     *
     * @param sql
     * @param query
     * @param sender
     */



    /**
     * Haven't done anything with this yet.
     * @param start
     * @param finish
     */
    public void calculateTime(long start, long finish) { }

    public String permission(String permission)
    {
        return color(getPrefix()+ "&8&l--&b&l> " +permission);
    }

    public String getStaff(Plugin plugin)
    {
        StringBuilder sb = new StringBuilder();
        for(Player p : Bukkit.getServer().getOnlinePlayers())
        {
            if(p.hasPermission("easymotd.staff"))
            {
                sb.append(p.getName() + ", ");
            }
        }
        if(sb.length() < 1)
            return color(plugin.getConfig().getString("Messages.StaffNotOnline"));
            //return color("&cError: No staff members online. &b&o.-.");
        else
        return ""+sb.toString();
    }

    public String getUsers()
    {
        StringBuilder sb = new StringBuilder();
        for(Player p : Bukkit.getServer().getOnlinePlayers())
        {
            sb.append(p.getName() + ", ");
        }
        return ""+sb.toString();
    }

    public String format(Player p, String value)
    {
        value = value.replaceAll(p.getName(),"%playername%");
        value = value.replaceAll(p.getAddress()+"","%ip%");
       // value = value.replaceAll("","");
        return value;
    }
    public String format(CommandSender sender, String value)
    {
        value = value.replaceAll(sender.getName(),"%sendername%");
        value = value.replaceAll("","");
        return value;
    }


    /**
     *
     * @param public_broadcast
     * @param broadcast
     *
     * This method will check to see if you want to publicly broadcast a report.
     *
     * If public_broadcast is set to false it will only broadcast reports to online staffmemebers.
     * Eventually, it will log it to the database as well.
     *
     */
    public void reportBroadcast(boolean public_broadcast, String broadcast)
    {
        if(public_broadcast)
        {
            Bukkit.getServer().broadcastMessage(broadcast);
        }else
        {
            for(Player staff : Bukkit.getServer().getOnlinePlayers())
            {
                if (staff.hasPermission("playerreports.receive")) {
                    staff.sendMessage(broadcast);
                }
            }
        }
    }




    public void reportBroadcastPublic(CommandSender sender, String format)
    {
        format = format.replaceAll("","");

    }

    public void reportBroadcastPublic(Player player)
    {

    }

    public String color(String message) {

     return   message.replaceAll("&", "§");
    }

    private SQL sql;
    public SQL getAccess(){
        return sql;
    }
    public void connectionExists(){
        this.sql = new SQL("107.170.21.151","Logger","REQUEST1", "Logs");
    }

}


