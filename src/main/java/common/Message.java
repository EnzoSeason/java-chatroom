package common;

public abstract class Message {
    /**
     * the name of the client who send the message
     */
    private String source;
    /**
     * the name of the client who receive the message
     */
    private String destination;
    private String content;

    protected Message(String source, String destination, String content) {
        this.source = source;
        this.destination = destination;
        this.content = content.trim();
    }


    /**
     * @return the source client name
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the destination client name
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb
                .append(source).append(Constants.MESSAGE_SEP)
                .append(destination).append(Constants.MESSAGE_SEP)
                .append(content).append(Constants.MESSAGE_END_STR);
        return sb.toString();
    }
}
