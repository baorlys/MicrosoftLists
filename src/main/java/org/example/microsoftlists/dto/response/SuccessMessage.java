package org.example.microsoftlists.dto.response;

import org.example.microsoftlists.model.constants.MessageType;
import lombok.Getter;
import lombok.Setter;

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
