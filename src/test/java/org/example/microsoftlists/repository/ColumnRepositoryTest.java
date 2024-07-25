package org.example.microsoftlists.repository;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.constants.NumberSymbol;
import org.example.microsoftlists.model.microsoft.list.Column;
import org.example.microsoftlists.model.microsoft.list.MicrosoftList;
import org.example.microsoftlists.model.microsoft.list.Parameter;
import org.example.microsoftlists.model.microsoft.list.value.ValueFactory;
import org.example.microsoftlists.service.builder.ColumnBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ColumnRepositoryTest {
    private static final String DIR_PATH = Configuration.DATA_PATH;
    private static final String FILE_PATH = Configuration.COLS_PATH;

    MicrosoftList microsoftList;

    @BeforeEach
    void setUp() throws IOException {
        microsoftList = new MicrosoftListRepository(DIR_PATH, Configuration.LISTS_PATH).findById("ebe4375d-0a57-43a3-aa8f-50def3b823a9");

    }

//
//    @Test
//    void testSave() throws IOException {
//        ColumnRepository columnRepository = new ColumnRepository(DIR_PATH, FILE_PATH);
//        Column column = new ColumnBuilder(ColumnType.NUMBER, "Column Number")
//                .configure(new Parameter(ConfigParameter.NUMBER_SYMBOL, ValueFactory.create(NumberSymbol.DOLLAR)))
//                .build();
//        column.setList(microsoftList);
//        columnRepository.save(column);
//    }

    @Test
    void testUpdate() throws IOException {
        ColumnRepository columnRepository = new ColumnRepository(DIR_PATH, FILE_PATH);
        Column column = new ColumnBuilder(ColumnType.TEXT, "Column 1 updated").build();
        column.setList(microsoftList);
        columnRepository.update("bcc67f66-887a-47a3-b381-a375dea53085", column);
    }

    @Test
    void testFindALlByList() throws IOException {
        ColumnRepository columnRepository = new ColumnRepository(DIR_PATH, FILE_PATH);
        try {
            columnRepository.findAllByListId("ebe4375d-0a57-43a3-aa8f-50def3b823a9").forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}