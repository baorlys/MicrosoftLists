package model.responses;

import model.constants.MessageType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MessageFactory {
    private MessageFactory() {
    }

    private static final Map<MessageType, Supplier<ResultMessage>> messageMap = new HashMap<>();
    static {
        messageMap.put(MessageType.ERROR, ErrorMessage::new);
        messageMap.put(MessageType.SUCCESS, SuccessMessage::new);
    }

    public static ResultMessage getMessage(MessageType type, String msg) {
        ResultMessage res = messageMap.get(type).get();
        res.setMsg(msg);
        return res;
    }

}
