package org.example.microsoftlists.repository.json;

import org.example.microsoftlists.model.Identifiable;
import org.example.microsoftlists.service.file.JsonService;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Repository<T extends Identifiable> {
    private final String dirPath;
    private final String filePath;


    protected Repository(String dirPath, String filePath) {
        this.dirPath = dirPath;
        this.filePath = filePath;
    }

    public T findById(String id) throws IOException {
        return findAll().stream()
                .filter(c -> c.getId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }
    public void save(T object) throws IOException {
        List<T> data = findAll();

        if (data.stream().noneMatch(c -> c.getId().equals(object.getId()))) {
            data.add(object);
        } else {
            update(object.getId().toString(), object);
        }

        JsonService.toJsonFile(data, dirPath, filePath);
    }

    public void saveAll(List<T> objects) throws IOException {
        List<T> data = findAll();
        data.addAll(objects);

        for (T object : objects) {
            save(object);
        }

    }


    public void delete(String id) throws IOException {
        List<T> data = findAll();
        data = data.stream()
                .filter(c -> !c.getId().toString().equals(id))
                .collect(Collectors.toList());
        JsonService.toJsonFile(data, dirPath, filePath);
    }

    public void update(String id, T object) throws IOException {
        List<T> data = findAll();
        data = data.stream()
                        .map(c -> {
                            if (c.getId().toString().equals(id)) {
                                object.setId(c.getId());
                                return object;
                            }
                            return c;
                        })
                .collect(Collectors.toList());

        JsonService.toJsonFile(data, dirPath, filePath);

    }

    public List<T> findAll() throws IOException {
        return JsonService.fromJsonFile(dirPath,filePath, new TypeReference<>() {
        });
    }


}
