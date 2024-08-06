package org.example.microsoftlists.model.view;

import org.example.microsoftlists.model.constants.ViewType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class ViewFactory {
    private ViewFactory() {
        // Empty constructor
    }
    private static final Map<ViewType, Supplier<IView>> viewMap = new EnumMap<>(ViewType.class);
    static {
        viewMap.put(ViewType.LIST, ListView::new);
        viewMap.put(ViewType.BOARD, BoardView::new);
        viewMap.put(ViewType.CALENDAR, CalendarView::new);
        viewMap.put(ViewType.GALLERY, GalleryView::new);

    }

    public static IView create(ViewType viewType) {
        return viewMap.get(viewType).get();
    }
}
