package org.example.microsoftlists.dto.response;

import org.example.microsoftlists.model.constants.MessageType;

public class ErrorMessage extends ResultMessage {


    public ErrorMessage() {
        super("", MessageType.ERROR);
    }
}
