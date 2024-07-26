package org.example.microsoftlists.dto;

import org.example.microsoftlists.model.constants.MessageType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class MessageFactory {
    private MessageFactory() {
    }

    private static final Map<MessageType, Supplier<ResultMessage>> messageMap = new EnumMap<>(MessageType.class);
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
