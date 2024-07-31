package org.example.microsoftlists.view;

import org.example.microsoftlists.model.constants.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String msg;
    private MessageType type;

    public Response(String msg, MessageType type) {
        this.msg = msg;
        this.type = type;
    }

}
