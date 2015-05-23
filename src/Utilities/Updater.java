package Utilities;

/**
 * Created by ES359 on 5/2/15.
 */


/**
 * PLACE THIS INSIDE THE onEnable() method OF EVERY PLUGIN YOU IMPLEMENT.
 *
 * PluginManager pm = Bukkit.getServer().getPluginManager();
 * pm.registerEvents(new Updater(),this);
 *
 * Bukkit.getServer().getPluginManager().registerEvents(new Updater(), this);
 *
 */

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ES359 on 4/8/15.
 */
public class Updater implements Listener {


    public void runConnection(){
        setVersion("0.1");
        setPlugin("PlayerReport");
        setPrefix("&6&l[&6&lUpdater]");
        this.util.connectionExists();
        this.sql = util.getAccess();
    }


    /**
     * checkStatus checks to see if the plugin has an update ready.
     * If the ResultSet returns NO, then the user will not get an update message.
     *
     */

    public void checkStatus(SQL sql, Player p) {

        try {
            Statement s = sql.getConnection().createStatement();


            String query = "SELECT * FROM Updater where plugin_name='"+getPlugin()+"'";

            ResultSet set = s.executeQuery(query);

            while(set.next()) {

                String result = set.getString(3);
                if(result.equalsIgnoreCase("YES")) {
                    this.setUpdate(true);
                    p.sendMessage(getPrefix() + " " + ChatColor.GREEN + set.getString(5) + " Get it here: " + set.getString(4));
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method prevents spam to build up for users on the server.
     * It will disable the automatic broadcast of the update function
     * if, they have the matching version inside of the database.
     * If they do not, it will continue to inform that there is an update ready.
     *
     */
    public void hasUpdated(SQL sql) {

        try {
            Statement s = sql.getConnection().createStatement();

            String query = "SELECT plugin_version FROM Updater WHERE plugin_name='"+getPlugin()+"'";

            ResultSet set = s.executeQuery(query);
            while(set.next()) {

                String result = set.getString(1);

                if(!result.equals(getVersion())) {
                    this.setUpdateStatus(true);
                }else {
                    this.setUpdateStatus(false);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public String getVersion(){
        return version;
    }
    public void setVersion(String string) {
        this.version = string;
    }
    public boolean getUpdateStatus() {
        return hasUpdated;
    }
    public void setUpdateStatus(boolean status) {
        this.hasUpdated = status;
    }
    public void setUpdate(boolean value) {
        this.update = value;
    }
    public boolean getUpdate() {
        return update;
    }

    private String getPrefix() {
        return this.prefix;
    }
    private String getPlugin() {
        return this.plugin;
    }
    private void setPlugin(String plugin) {
        this.plugin = plugin;
    }
    private void setPrefix(String prefix) {
        prefix = prefix.replaceAll("&","§");
        this.prefix = prefix;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if(util.checkAuth(event.getPlayer().getUniqueId()+"")) {
            event.getPlayer().setPlayerListName(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "" + event.getPlayer().getName()+ChatColor.RESET);
            event.getPlayer().setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "" + event.getPlayer().getName()+ChatColor.RESET);
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {


        runConnection();

        Player p = event.getPlayer();
        String uuid = ""+p.getUniqueId();

        //set(p);

        this.hasUpdated(sql);
        if(getUpdateStatus()) {
            checkStatus(sql,p);
        }
    }
    private SQL sql;
    private PRUtils util = new PRUtils();
    private boolean hasUpdated;
    private boolean update;
    private String version;
    private String prefix,plugin;
}