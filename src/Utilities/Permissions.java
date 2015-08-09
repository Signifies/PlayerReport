package Utilities;

/**
 * Created by ES359 on 8/4/15.
 */
public class Permissions {
    static public String PERMISSION_ADMIN = "PlayerReport.admin";
    static public String reportCheck_PERM = "PlayerReport.check";
    static public String BANLOOK_UP_PERM = "PlayerReport.banlookup";
    static public String PERM_REMOVEBAN = "PlayerReport.deleteban";
    static public String PERM_BAN = "PlayerReport.ban";

    public String getPermissions()
    {
        return "Permissions:\n" + PERMISSION_ADMIN +"\n"+reportCheck_PERM + "\n"+BANLOOK_UP_PERM+"\n"+PERM_REMOVEBAN+"\n"+PERM_BAN+"";

    }


}
