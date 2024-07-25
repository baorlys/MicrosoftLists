package service;

import com.fasterxml.jackson.core.type.TypeReference;
import config.Configuration;
import model.microsoft.list.MicrosoftList;
import service.file.OpenService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MicrosoftListService {
    private MicrosoftListService() {
        throw new IllegalStateException("Utility class");
    }


    public static List<MicrosoftList> loadLists(){
        File rootDir = new File(Configuration.DIR_PATH);
        File[] folders = rootDir.listFiles(File::isDirectory);

        if (folders == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(folders)
                .map(MicrosoftListService::loadList)
                .collect(Collectors.toList());
    }

    private static MicrosoftList loadList(File file) {
        MicrosoftList list = new MicrosoftList(file.getName());
        try {
            list.setColumns(OpenService.openFile(file.getPath(), Configuration.COLS_PATH, new TypeReference<>() {
            }));
            list.setRows(OpenService.openFile(file.getPath(), Configuration.DATA_PATH, new TypeReference<>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static MicrosoftList findList(List<MicrosoftList> lists, String listName) {
        return lists.stream()
                .filter(list -> list.getName().equals(listName))
                .findFirst()
                .orElse(null);
    }
}
