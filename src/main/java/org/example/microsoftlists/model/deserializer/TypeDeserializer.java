package org.example.microsoftlists.model.deserializer;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.type.IType;
import org.example.microsoftlists.model.type.TypeFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TypeDeserializer extends JsonDeserializer<IType> {

    @Override
    public IType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String typeString = jsonParser.getText();
        ColumnType columnType = ColumnType.valueOf(typeString);
        return TypeFactory.getType(columnType);
    }

}
