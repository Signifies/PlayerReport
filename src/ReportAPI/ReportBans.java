package ReportAPI;

import Utilities.SQL;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportBans {
 
    private SQL sql;

     String user_name;
     String user;
     boolean banned;
 
    public String getUser()
    {
        return this.user_name;
    }
 
    public String getUserUUID()
    {
        return this.user;
    }
 
    public boolean getBanned()
    {
        return this.banned;
    }
 
    public void checkBanned(SQL sql, Player p)  {
 
        String uuid = ""+p.getUniqueId();
 
        try {
            Statement s = sql.getConnection().createStatement();
 
            String query = "SELECT * FROM report_bans WHERE UUID='"+uuid+"'";
 
            ResultSet set = s.executeQuery(query);
            while(set.next()) {
                String result = set.getString(3);
                if(result.equals(uuid))
                {
                    this.setStatus(true);
                }else {
                    this.setStatus(false);
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
 
    public void getReason(SQL sql, Player p) {
        String uuid = ""+p.getUniqueId();
 
        try{
            Statement s = sql.getConnection().createStatement();
 
            String query = "SELECT ban_reason FROM report_bans WHERE UUID='"+uuid+"'";
 
            ResultSet set = s.executeQuery(query);
 
            while(set.next()) {
              //  p.sendMessage(report.getMessage());
                String result = ChatColor.RED + set.getString(1);
 
                //this.reason = result;
                p.sendMessage(result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    private String reason;
 
    public String returnReason() {
        return reason;
    }
 
    private boolean status;
 
    public boolean getStatus() {
        return status;
    }
 
    public void setStatus(boolean b) {
        this.status = b;
    }
 
    /**
     *
     * Possible that we might implement ingame access to developers.
     */
    @Deprecated
    public void submitBan(SQL sql, String uuid) {}
 
}