package model.responses;

import model.constants.MessageType;

public class ErrorMessage extends ResultMessage {

    public ErrorMessage(String msg) {
        super(msg, MessageType.ERROR);
    }

    public ErrorMessage() {
        super("", MessageType.ERROR);
    }
}
