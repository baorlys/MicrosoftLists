package org.example.microsoftlists.model.view;

import org.example.microsoftlists.model.constants.ViewConfig;
import org.example.microsoftlists.model.constants.ViewType;

import java.util.Map;

public interface IView {
    ViewType getViewType();

    default void handleConfig(Map<ViewConfig, String> configs) {
        // Empty method
    }
}
