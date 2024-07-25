package org.example.microsoftlists.repository;

import org.example.microsoftlists.model.microsoft.list.MicrosoftList;


public class MicrosoftListRepository extends Repository<MicrosoftList> {
    public MicrosoftListRepository(String dirPath, String filePath) {
        super(dirPath, filePath);
    }
}
