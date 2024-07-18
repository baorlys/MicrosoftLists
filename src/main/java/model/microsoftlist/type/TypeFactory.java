package model.microsoftlist.type;

import model.constants.TypeColumn;

import java.util.EnumMap;
import java.util.Map;

public class TypeFactory {

    private TypeFactory() {
    }
    private static final Map<TypeColumn, AbstractType> typeMap = new EnumMap<>(TypeColumn.class);

    static {
        typeMap.put(TypeColumn.TEXT, new TextType());
        typeMap.put(TypeColumn.NUMBER, new NumberType());
        typeMap.put(TypeColumn.DATE, new DateType());
        typeMap.put(TypeColumn.CHOICE, new ChoiceType());
        typeMap.put(TypeColumn.CHECKBOX, new CheckBoxType());
        typeMap.put(TypeColumn.PERSON, new PersonType());
        typeMap.put(TypeColumn.IMAGE, new ImageType());
        typeMap.put(TypeColumn.MULTIPLE_LINES_OF_TEXT, new MultipleLinesOfTextType());
        typeMap.put(TypeColumn.LOOKUP, new LookupType());
        typeMap.put(TypeColumn.HYPERLINK, new HyperlinkType());
        typeMap.put(TypeColumn.AVERAGE_RATING, new AverageRatingType());
    }

    public static AbstractType getType(TypeColumn type) {
        return typeMap.get(type);
    }
}
