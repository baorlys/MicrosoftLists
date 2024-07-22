package model.microsoft.list.type;

import model.constants.ColumnType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class TypeFactory {

    private TypeFactory() {
    }
    private static final Map<ColumnType, Supplier<AbstractType>> typeMap = new EnumMap<>(ColumnType.class);

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

    public static AbstractType getType(ColumnType type) {
        return typeMap.get(type).get();
    }
}
