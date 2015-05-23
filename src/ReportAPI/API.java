package ReportAPI;


import Utilities.PRUtils;
import Utilities.SQL;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ES359 on 5/22/15.
 */
public class API extends PRUtils
{

    public String getUser()
    {
        return this.userName;
    }

    public String getUserUUID()
    {
        return this.userUUID;
    }

    public boolean getBanned()
    {
        return this.banned;
    }

    /**
     * Creates a PlayerReport.
     *
     * This method will contain the logic for the database
     * and player data information variables.
     * @param sql Sql connection parameter.
     * @param report Report details.
     * @param p The player being reported,
     * @param sender Player that submitted the report. (sender.getName())
     */
    public void createReport(SQL sql, String report, Player p, Player sender)
    {
        try
        {
            PreparedStatement statement = sql.getConnection().prepareStatement("INSERT INTO player_reports (name,UUID,report,reported_by,senderUUID) VALUES (?,?,?,?,?); ");

            statement.setString(1,p.getName());
            statement.setString(2,""+p.getUniqueId());
            statement.setString(3,report);
            statement.setString(4, sender.getName());
            statement.setString(5, ""+sender.getUniqueId());

            statement.execute();
            statement.close();

        }catch (SQLException e)
        {
            sender.sendMessage(syntaxError(e));
        }
    }

    /**
     * Removes the report. We probably won't need the report value
     * We more than likely will be using some sort of ID number to the report.
     * This method eventually, will take an Integer.
     * @param sql
     * @param
     */
    public void deleteReport(SQL sql, int id, CommandSender sender)
    {
        try
        {
            PreparedStatement statement = sql.getConnection().prepareStatement("DELETE FROM player_reports WHERE id='"+id+"';");
            statement.execute();
            statement.close();
            sender.sendMessage(ChatColor.RED + "Deleted Report ID, " + id + ".");
        }catch (SQLException e)
        {
            sender.sendMessage(syntaxError(e));
        }
    }

    /**
     * Checks a users report.
     * This method will take the player value and attempt to check
     * the users UUID against the database. (This is why the player must be exact, and ONLINE.) //TODO I will attempt in the near future to fix this.
     * @param sql
     * @param
     * @param p
     */
    public void checkReport(SQL sql, Player p, CommandSender sender)
    {
        try
        {
            Statement s = sql.getConnection().createStatement();

            String query = "SELECT * FROM player_reports WHERE UUID='"+p.getUniqueId()+"'";

            ResultSet set = s.executeQuery(query);
         //  String header = ChatColor.GRAY +"Reported Player        report   Reported BY:\n--------------------------" ;

            while(set.next())
            {
                String header = color("&7Report ID: &6&o" + set.getString(1)+ "    &7Filed by: &6"+set.getString(2)+ " &7Reported on: &6" + set.getString(7));
                sender.sendMessage(header);
                String result = color("&b")+set.getString(2) +"  :"+ ChatColor.GRAY +"  " + set.getString(4) + color(" &cReported by, &a&o" + set.getString(5));
                sender.sendMessage(result);
                sender.sendMessage("\n");
            }
        }catch (SQLException e) {
                sender.sendMessage(syntaxError(e));
        }

    }

    /**
     * Creates a ban for the specified user.
     * @param sql
     * @param banned_player Player to be banned.
     * @param ban_reason Reason provided for the ban. Unsure if I will implement a default ban message.
     */
    public void createBan(SQL sql, Player banned_player, String ban_reason, CommandSender sender)
    {
        try
        {
            PreparedStatement statement = sql.getConnection().prepareStatement("INSERT INTO report_bans (Name,UUID,banned_by,ban_reason) VALUES (?,?,?,?)");
            statement.setString(1,banned_player.getName());
            statement.setString(2,""+banned_player.getUniqueId());
            statement.setString(3, sender.getName());
            statement.setString(4,ban_reason);

            statement.execute();
            statement.close();
            sender.sendMessage(color(getPrefix() + "&6The Player, &a&o" + banned_player.getName()+ " &chas been banned."));
            banned_player.sendMessage(color("&cYou have been banned from submitting reports:" + ban_reason+ "&c."));

        }catch (SQLException e)
        {
            sender.sendMessage(syntaxError(e));
        }

    }

    /**
     *
     * @param sql
     * @param banned Query's the database to check for banned player.
     * @param ban_id If secessful, it will remove the ban via ban ID. (THIS VALUE might be removed later on.)
     */
    public void removeBan(SQL sql, Player banned, CommandSender sender)
    {
        try
        {
            PreparedStatement statement = sql.getConnection().prepareStatement("DELETE FROM report_bans WHERE UUID='"+banned.getUniqueId()+"';");
            statement.execute();
            statement.close();
            sender.sendMessage(ChatColor.RED + "Pardoned the player, " + banned.getName() + ".");
        }catch (SQLException e)
        {
            sender.sendMessage(syntaxError(e));
        }
    }

    /**
     * Looks up a ban on a player
     * @param sql
     * @param banned Takes the parameter of a banned player.
     */
    public void lookUpBan(SQL sql, Player banned, CommandSender sender)
    {
        try
        {
            Statement s = sql.getConnection().createStatement();

            String query = "SELECT * FROM report_bans WHERE UUID='"+banned.getUniqueId()+"'";

            ResultSet set = s.executeQuery(query);
            //  String header = ChatColor.GRAY +"Reported Player        report   Reported BY:\n--------------------------" ;

            while(set.next())
            {
                String header = color("&cBan ID: &6&o" + set.getString(1)+ "    &cBanned user: &6"+set.getString(2)+ " &7Banned on: &6" + set.getString(6));
                sender.sendMessage(header);
                String result = color("&b")+set.getString(2) +"  :"+ ChatColor.GRAY +"  " + set.getString(5) + color(" &cBanned by, &a&o" + set.getString(4));
                sender.sendMessage(result);
                sender.sendMessage("\n");
            }
        }catch (SQLException e)
        {
            sender.sendMessage(syntaxError(e));
        }
    }



    //String message = color(getPrefix() + "&6The Player, &a&o" + banned_player.getName()+ " &chas been banned.")
    String userName;
    String userUUID;
    boolean banned;

}
