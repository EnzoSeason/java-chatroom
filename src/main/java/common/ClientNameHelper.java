package common;

public interface ClientNameHelper {
    /**
     * @param name
     * @return Error message if the given name isn't a valid client name
     */
    String isValidName(String name);


    /**
     * @param name
     * @return Error message if the given name isn't a valid destination client name
     */
    String isValidMessageDestination(String name);

}
