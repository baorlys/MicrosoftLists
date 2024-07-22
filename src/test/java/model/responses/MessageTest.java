package model.responses;

import model.constants.MessageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void getMessage() {
        ResultMessage message = MessageFactory.getMessage(MessageType.ERROR, "error");
        assertEquals("error", message.getMsg());
        assertEquals(MessageType.ERROR, message.getType());

        message = MessageFactory.getMessage(MessageType.SUCCESS, "success");
        SuccessMessage successMessage = (SuccessMessage) message;
        successMessage.setData("data");
        assertEquals("success", message.getMsg());
        assertEquals(MessageType.SUCCESS, message.getType());
    }
}