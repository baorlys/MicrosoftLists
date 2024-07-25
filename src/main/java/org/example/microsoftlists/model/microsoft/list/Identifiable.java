package org.example.microsoftlists.model.microsoft.list;


import java.util.UUID;

public interface Identifiable {
    UUID getId();

    void setId(UUID fromString);
}
