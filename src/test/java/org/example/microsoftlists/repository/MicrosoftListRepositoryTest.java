package org.example.microsoftlists.repository;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.microsoft.list.MicrosoftList;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class MicrosoftListRepositoryTest {
    private static final String DIR_PATH = Configuration.DATA_PATH;
    private static final String FILE_PATH = Configuration.LISTS_PATH;
//    @Test
//    void testSave() throws IOException {
//         MicrosoftListRepository microsoftListRepository = new MicrosoftListRepository(DIR_PATH, FILE_PATH);
//         MicrosoftList microsoftList = new MicrosoftList("list 3");
//         microsoftListRepository.save(microsoftList);
//    }

    @Test
    void testDelete() throws IOException {
         MicrosoftListRepository microsoftListRepository = new MicrosoftListRepository(DIR_PATH, FILE_PATH);
//         id : aea1f2a5-4f64-499d-9b27-752c80394032
         String id = "";
         MicrosoftList microsoftList = microsoftListRepository.findById(id);
         microsoftListRepository.delete(microsoftList);
    }

    @Test
    void testUpdate() throws IOException {
        MicrosoftListRepository microsoftListRepository = new MicrosoftListRepository(DIR_PATH, FILE_PATH);
        MicrosoftList microsoftList = new MicrosoftList("List updated 4");
        microsoftListRepository.update("4787bd3d-9829-47fc-a716-cc40cacb3f9c",microsoftList);
    }

}