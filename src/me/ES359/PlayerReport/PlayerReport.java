package me.ES359.PlayerReport;

import Commands.Report;
import ReportAPI.ReportAPI.Commands.*;
import ReportAPI.CreateSQLTables;
import Utilities.PRUtils;
import Utilities.SQL;
import Utilities.Updater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by ES359 on 5/18/15.
 */
public class PlayerReport extends JavaPlugin
{
    /**
     * Create a simple debug API
     * With this variable.
     */
    public SQL getSQL() {
        return this.sql;
    }
    public boolean isSqlEnabled() {
        return this.sqlEnabled;
    }


    private void createTable()
    {
            table.createTable(getSQL(),"CREATE TABLE IF NOT EXISTS player_reports (id INT PRIMARY KEY AUTO_INCREMENT, name varchar(50), UUID varchar(50), report varchar(250), reported_by varchar(26), senderUUID varchar(50), stamp TIMESTAMP);");
            table.createTable(getSQL(),"CREATE TABLE IF NOT EXISTS report_bans (id INT PRIMARY KEY AUTO_INCREMENT, Name varchar(50), UUID varchar(50), banned_by varchar(25), ban_reason varchar(250), stamp TIMESTAMP);");
    }
    public void logCommands()
    {
        this.getCommand("removeban").setExecutor(new RemoveBanCommand(this));
        this.getCommand("banlookup").setExecutor(new BanLookUpCommand(this));
        this.getCommand("reportban").setExecutor(new ReportBanCommand(this));
        this.getCommand("checkreport").setExecutor(new CheckReportCommand(this));
        this.getCommand("deletereport").setExecutor(new DeleteReportCommand(this));
        this.getCommand("report").setExecutor(new Report(this));
        if(DEBUG)
        {
            util.logToConsole("&cCommands have been registered.");
        }
    }


    public void onEnable()
    {

        if(isSqlEnabled()) {
        System.out.println(id);
        this.sql = new SQL(getConfig().getString("Database.host"), getConfig().getString("Database.username"), getConfig().getString("Database.password"), getConfig().getString("Database.database"));table.createTable(sql, "CREATE TABLE IF NOT EXISTS user_reports (id INT PRIMARY KEY AUTO_INCREMENT, name varchar(50), UUID varchar(50), report varchar(250), stamp TIMESTAMP;");
            createTable();
        }
        logCommands();
        this.getConfig().options().copyDefaults(true);
        saveConfig();
        Bukkit.getServer().getPluginManager().registerEvents(new Updater(),this);


        //this.util.configSetup(this);

    }
    CreateSQLTables table = new CreateSQLTables();
    String id = "Connected to: " + getConfig().getString("Database.host") + " Yay!";
    static public boolean DEBUG = false;
    private SQL sql;
    //private Utils util = new Utils();
    PRUtils util = new PRUtils();
    private boolean sqlEnabled = getConfig().getBoolean("Database.Enabled");
}
