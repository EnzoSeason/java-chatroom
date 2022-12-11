package common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {
    static public int SERVER_PORT = 12345;
    static public Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    static public String CLIENT_PREFIX = "@";
    static String SPACE_STR = " ";
    static String MESSAGE_SEP_STR = String.valueOf(Character.UNASSIGNED);
    static String MESSAGE_END_STR = "\n";
    static public String ADMIN_CLIENT = "admin";
    static public String ANON_CLIENT = "anonymous";
    static public String WELCOME = "Welcome to Chat Room.\nTo start a chat with another user, @he/she name.\n@admin list to check all the user names.\n @admin exit to log out.";
    static public String BYE = "bye";
    static public String SERVER_COMMAND_LOGOUT = "logout";
    static public String SERVER_COMMAND_LIST = "list";
}
