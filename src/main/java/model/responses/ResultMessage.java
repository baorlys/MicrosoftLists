package model.responses;

import lombok.Getter;
import lombok.Setter;
import model.constants.MessageType;

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
