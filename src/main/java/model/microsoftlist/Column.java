package model.microsoftlist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Column {
    private MicrosoftList list;
    private String name;
    private Type type;
    private boolean isHidden;




    public Column(MicrosoftList list, String name, Type type) {
        this.list = list;
        this.name = name;
        this.type = type;
        this.isHidden = false;
    }



}
