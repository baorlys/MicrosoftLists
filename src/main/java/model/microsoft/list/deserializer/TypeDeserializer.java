package model.microsoft.list.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import model.constants.ColumnType;
import model.microsoft.list.type.IType;
import model.microsoft.list.type.TypeFactory;

import java.io.IOException;

public class TypeDeserializer extends JsonDeserializer<IType> {

    @Override
    public IType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String typeString = jsonParser.getText();
        ColumnType columnType = ColumnType.valueOf(typeString);
        return TypeFactory.getType(columnType);
    }

}
