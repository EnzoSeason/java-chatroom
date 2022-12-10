package common;

public class SimpleClientNameHelper implements ClientNameHelper {
    @Override
    public String isValidName(String name) {
        String errorMsg = isValidMessageDestination(name);
        if (errorMsg != null) {
            return errorMsg;
        }

        if (name.toLowerCase().contains(Constants.ADMIN_CLIENT)) {
            return "The user name can't have " + Constants.ADMIN_CLIENT;
        }

        return null;
    }

    @Override
    public String isValidMessageDestination(String name) {
        if (name.trim().length() == 0) {
            return "The user name can't be null.";
        }

        for (String specialString : new String[]{Constants.MESSAGE_SEP_STR, Constants.SPACE_STR, Constants.CLIENT_PREFIX, Constants.ANON_CLIENT}) {
            if (name.toLowerCase().contains(specialString)) {
                return "The user name can't have " + specialString;
            }
        }

        return null;
    }
}
