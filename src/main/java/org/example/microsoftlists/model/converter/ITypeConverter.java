package org.example.microsoftlists.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.type.IType;
import org.example.microsoftlists.model.type.TypeFactory;

@Converter(autoApply = true)
public class ITypeConverter implements AttributeConverter<IType, String> {
    @Override
    public String convertToDatabaseColumn(IType iType) {
        return iType == null ? null : iType.getColumnType().name();
    }

    @Override
    public IType convertToEntityAttribute(String s) {
        return TypeFactory.getType(ColumnType.valueOf(s));
    }

}
