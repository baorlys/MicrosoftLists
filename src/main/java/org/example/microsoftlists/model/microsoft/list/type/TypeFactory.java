package org.example.microsoftlists.model.microsoft.list.type;

import org.example.microsoftlists.model.constants.ColumnType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class TypeFactory  {
    private TypeFactory() {
        // Empty constructor
    }

    private static final Map<ColumnType, Supplier<IType>> typeMap = new EnumMap<>(ColumnType.class);

    static {
        typeMap.put(ColumnType.TEXT, TextType::new);
        typeMap.put(ColumnType.NUMBER, NumberType::new);
        typeMap.put(ColumnType.DATE, DateType::new);
        typeMap.put(ColumnType.CHOICE, ChoiceType::new);
        typeMap.put(ColumnType.CHECKBOX, CheckBoxType::new);
        typeMap.put(ColumnType.PERSON, PersonType::new);
        typeMap.put(ColumnType.IMAGE,  ImageType::new);
        typeMap.put(ColumnType.MULTIPLE_LINES_OF_TEXT, MultipleLinesOfTextType::new);
        typeMap.put(ColumnType.LOOKUP, LookupType::new);
        typeMap.put(ColumnType.HYPERLINK, HyperlinkType::new);
        typeMap.put(ColumnType.AVERAGE_RATING, AverageRatingType::new);
    }

    public static IType getType(ColumnType type) {
        return typeMap.get(type).get();
    }
}
