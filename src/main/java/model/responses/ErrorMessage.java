package model.responses;

import model.constants.MessageType;

public class ErrorMessage extends ResultMessage {


    public ErrorMessage() {
        super("", MessageType.ERROR);
    }
}
