package org.example.microsoftlists.dto;

import org.example.microsoftlists.model.constants.MessageType;

public class ErrorMessage extends ResultMessage {


    public ErrorMessage() {
        super("", MessageType.ERROR);
    }
}
