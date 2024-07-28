package microsoftlists;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.constants.DateTime;
import org.example.microsoftlists.model.constants.NumberSymbol;
import org.example.microsoftlists.model.Parameter;
import org.example.microsoftlists.model.value.ValueFactory;
import org.junit.jupiter.api.Test;
import org.example.microsoftlists.service.builder.ColumnBuilder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestColumn {

    @Test
    // test valid value
    void testIsValidValue() {
        // test valid value for number column
        var colNumber = new ColumnBuilder(ColumnType.NUMBER, "Column Number")
                .configure(
                        Parameter.of(ConfigParameter.NUMBER_SYMBOL, NumberSymbol.NONE))
                .build();
        assertTrue(colNumber.isValidValue(ValueFactory.create(BigDecimal.valueOf(3.42113))));
        assertTrue(colNumber.isValidValue(ValueFactory.create(3)));
        assertFalse(colNumber.isValidValue(ValueFactory.create("text")));

        // test valid value for date column
        var colDate = new ColumnBuilder(ColumnType.DATE, "Column Date")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, DateTime.CURRENT_DATE))
                .build();
        assertTrue(colDate.isValidValue(ValueFactory.create("01-01-2024")));
        assertTrue(colDate.isValidValue(ValueFactory.create("01-01-2024 02:40 PM")));

        assertFalse(colDate.isValidValue(ValueFactory.create("text")));
        assertFalse(colDate.isValidValue(ValueFactory.create(3)));
//
//        var colYesNo = new ColumnBuilder(ColumnType.CHECKBOX, "Column YesNo")
//                .build();
//
//        var colChoice = new ColumnBuilder(ColumnType.CHOICE, "Column Choice")
//                .configure(
//                        Parameter.of(ConfigParameter.CHOICES, "choice 1","choice 2", "choice 3"),
//                        Parameter.of(ConfigParameter.MULTIPLE_SELECTION, false)
//                )
//                .build();
//
//        var colHyperlink = new ColumnBuilder(ColumnType.HYPERLINK, "Column Hyperlink")
//                .build();
//
//        var colImage = new ColumnBuilder(ColumnType.IMAGE, "Column Image")
//                .build();
//
//        var colPerson = new ColumnBuilder(ColumnType.PERSON, "Column Person")
//                .build();
//
//        var colMultipleLinesOfText = new ColumnBuilder(ColumnType.MULTIPLE_LINES_OF_TEXT,
//                "Column Multiple Lines of Text")
//                .build();
//
//        var colLookup = new ColumnBuilder(ColumnType.LOOKUP, "Column Lookup")
//                .build();
//
//        var colAverageRating = new ColumnBuilder(ColumnType.AVERAGE_RATING, "Column Average Rating")
//                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, 0))
//                .build();


    }
}
