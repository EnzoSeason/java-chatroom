package common;

public class Constants {
    static public int SERVER_PORT = 12345;
    static public String CLIENT_PREFIX = "@";
    static String SPACE_STR = " ";
    static char MESSAGE_SEP_CHAR = Character.UNASSIGNED;
    static String MESSAGE_SEP_STR = String.valueOf(Character.UNASSIGNED);
    static public String ADMIN_CLIENT = "admin";
    static public String ANON_CLIENT = "anonymous";
    static public String WELCOME = "Welcome to Chat Room.\nTo start a chat with another user, @he/she name.\n@admin list to check all the user names.\n @admin exit to log out.";
    static public String BYE = "bye";
    static public String SERVER_COMMAND_LOGOUT = "logout";
    static public String SERVER_COMMAND_LIST = "list";
}
