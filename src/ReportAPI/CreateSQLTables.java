package ReportAPI;

import Utilities.PRUtils;
import Utilities.SQL;
import me.ES359.PlayerReport.PlayerReport;

import java.sql.SQLException;

public class CreateSQLTables extends PRUtils{
 
    /**
     *
     * @param val Takes a SQL connection parameter.
     * @param instance Checks to see if the table should be created via a config.
     * @param sql Parameters you wish to define for table.
     */
    public void createTable(SQL val, boolean instance, String sql) {
        if(instance) {
            try{
                val.getConnection().prepareStatement(sql).executeUpdate();
                //logToConsole("&bSQL table has been created!");
            }catch (SQLException e) {
                e.printStackTrace();
                //logToConsole("&4WARNING. SQL table could not be created.");
            }
        }
    }
 
 
    /**
     *
     *
     * ]'
     */
 
 
 
    /**
     *
     * @param value Takes SQL connection parameter.
     * @param sql SQL syntax.
     *
     * Overridden method to create table with out a boolean value.
     */
    public void createTable(SQL value, String sql) {
        try {
            value.getConnection().prepareStatement(sql).executeUpdate();
            if(PlayerReport.DEBUG)
            {
                logToConsole("&bSQL table has been created!");
            }
        }catch (SQLException e){
            if(PlayerReport.DEBUG)
            {
                logToConsole("&4WARNING. SQL table could not be created.");
            }else
            {
                e.getMessage();
            }
        }
    }
}