package Commands;

import ReportAPI.API;
import Utilities.PRUtils;
import me.ES359.PlayerReport.PlayerReport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by ES359 on 5/18/15.
 */

//TODO Implement CommandDelay - Add configuration option to set time.

public class Report extends API implements CommandExecutor {

    public Report(PlayerReport instance)
    {
        this.main = instance;
    }


    public HashMap<String, Long> cooldowns = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage(color("&cThis command isn't for the console."));
            return true;
        }

        Player p = (Player)sender;

        int cooldownTime = this.main.getConfig().getInt("Time-Delay");

        if (cooldowns.containsKey(p.getName())) {
            long secondsLeft = ((cooldowns.get(p.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                p.sendMessage(util.color("&cYou still have &b" + secondsLeft + " &cseconds left!"));
            } else if (secondsLeft <= 0) {
                cooldowns.remove(sender.getName());
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("report") && !(p.hasPermission("pr.report"))) {


            p.sendMessage(util.permission("&cYou need the permission, &bpr.report &cto use this!"));
        } else if (args.length < 1)
        {
            p.sendMessage(util.color(util.getPrefix() + "&6Usage: &a/report <&cuser&a> <&creason&a>"));
        } else if (args.length == 1)
        {
            p.sendMessage(util.color(util.getPrefix() + "&6Usage: &a/report <user> <&creason&a>"));
        } else if (args.length > 1)
        {
            Player user = Bukkit.getServer().getPlayer(args[0]);
            if (user == null)
            {
                p.sendMessage(util.color(util.getPrefix() + "&6The Player, &a&o" + args[0] + " &cIs invalid."));
            } else
            {

                StringBuilder str = new StringBuilder();

                for (String arg : args) {
                    str.append(arg + " ");
                }

                String report = str.toString().replaceAll(args[0], "");
                String prefix = this.main.getConfig().getString(util.color("Report.prefix"));

                String config = this.main.getConfig().getString("Report.format");
                config = config.replace("%username%", user.getName());
                config = config.replace("%sendername%", sender.getName());
                config = config.replace("%reportmsg%", report);
                config = config.replace("%prefix%", prefix);

                String filter = this.main.getConfig().getString("Filter");

                /**
                 * Add user to cool down.
                 */

                if(this.main.isSqlEnabled())
                {
                    createReport(this.main.getSQL(),report,user,p);
                    p.sendMessage(color("&7Your report has been &6&ofiled&7 &a&o" + p.getName()));
                }

                if (!sender.hasPermission("pr.bypass.cooldown")) {

                    cooldowns.put(sender.getName(), System.currentTimeMillis());
                    reportBroadcast(main.getSQL(),main.getConfig().getBoolean("Broadcast.public"), util.color(config));
                    //sender.sendMessage(ChatColor.GREEN + "Your report has been submitted, " + ChatColor.GOLD + "" + sender.getName());

                } else {
                    util.reportBroadcast(main.getConfig().getBoolean("Broadcast.public"), util.color(config));
                }
            }
        }
        return true;
    }

    public PlayerReport main;
    PRUtils util = new PRUtils();

}
