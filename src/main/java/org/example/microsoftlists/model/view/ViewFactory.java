package org.example.microsoftlists.model.view;

import org.example.microsoftlists.model.constants.ViewType;
import org.example.microsoftlists.view.dto.request.ViewRequest;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class ViewFactory {
    private ViewFactory() {
        // Empty constructor
    }
    private static final Map<ViewType, Function<Map<String,String>,View>> viewMap = new EnumMap<>(ViewType.class);
    static {
        viewMap.put(ViewType.LIST, ListView::new);
        viewMap.put(ViewType.BOARD, BoardView::new);
        viewMap.put(ViewType.CALENDAR, CalendarView::new);
        viewMap.put(ViewType.GALLERY, GalleryView::new);

    }

    public static View create(ViewRequest viewReq) {
        return viewMap.get(viewReq.getType()).apply(viewReq.getData());
    }
}
