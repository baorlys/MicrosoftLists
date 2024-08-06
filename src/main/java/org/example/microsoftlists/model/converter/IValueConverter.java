package org.example.microsoftlists.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.ValueFactory;
@Converter(autoApply = true)
public class IValueConverter implements AttributeConverter<IValue, String> {
    @Override
    public String convertToDatabaseColumn(IValue iValue) {
        return iValue == null ? null : iValue.get();
    }

    @Override
    public IValue convertToEntityAttribute(String s) {
        return ValueFactory.create(s);
    }
}
