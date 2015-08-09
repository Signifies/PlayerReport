package Utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;
import java.util.UUID;

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

    String author = "9c5dd792-dcb3-443b-ac6c-605903231eb2";


    public boolean checkAuthor(UUID uuid)
    {
        return uuid.toString().equals(author);
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

    /**
     *
     * @param plugin
     * @return
     */
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


