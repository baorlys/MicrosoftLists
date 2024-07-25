package org.example.microsoftlists.dto.response;

import org.example.microsoftlists.model.constants.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultMessage {
    private String msg;
    private MessageType type;

    public ResultMessage(String msg, MessageType type) {
        this.msg = msg;
        this.type = type;
    }

}
