package org.example.microsoftlists.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class SortRequest {
    private String columnId;
    private SortOrder order;


}
