package org.example.microsoftlists.view;

import org.example.microsoftlists.model.constants.MessageType;

public class ErrorMessage extends Response {


    public ErrorMessage() {
        super("", MessageType.ERROR);
    }
}
