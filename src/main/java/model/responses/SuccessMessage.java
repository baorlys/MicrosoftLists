package model.responses;

import lombok.Getter;
import lombok.Setter;
import model.constants.MessageType;

import java.util.List;

@Getter
@Setter
public class SuccessMessage extends ResultMessage {

    private List<Object> data;


    public SuccessMessage() {
        super("", MessageType.SUCCESS);
    }

    public void setData(Object... data) {
        this.data = List.of(data);
    }
}
