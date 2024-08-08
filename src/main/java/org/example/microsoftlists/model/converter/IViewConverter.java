package org.example.microsoftlists.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.microsoftlists.model.constants.ViewType;
import org.example.microsoftlists.model.view.IView;
import org.example.microsoftlists.model.view.ViewFactory;

@Converter(autoApply = true)
public class IViewConverter implements AttributeConverter<IView, String> {
    @Override
    public String convertToDatabaseColumn(IView iType) {
        return iType == null ? null : iType.getViewType().name();
    }

    @Override
    public IView convertToEntityAttribute(String s) {
        return ViewFactory.create(ViewType.valueOf(s));
    }

}
