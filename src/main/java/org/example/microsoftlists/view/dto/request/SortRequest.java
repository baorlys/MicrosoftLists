package org.example.microsoftlists.view.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class SortRequest {
    @NotNull
    private String columnId;
    private SortOrder order;


}
