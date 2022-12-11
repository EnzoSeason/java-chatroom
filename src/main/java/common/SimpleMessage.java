package common;

public class SimpleMessage extends Message {

    static SimpleMessage Builder(String message) {
        var sourceEnd = message.indexOf(Constants.MESSAGE_SEP_STR);
        var destinationEnd = message.indexOf(Constants.MESSAGE_SEP_STR, sourceEnd + 1);

        var source = message.substring(0, sourceEnd);
        var destination = message.substring(sourceEnd + 1, destinationEnd);
        var content = message.substring(destinationEnd + 1).trim();

        return new SimpleMessage(source, destination, content);
    }

    public SimpleMessage(String source, String destination, String content) {
        super(source, destination, content);
    }
}
