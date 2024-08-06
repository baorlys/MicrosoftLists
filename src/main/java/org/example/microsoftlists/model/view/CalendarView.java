package org.example.microsoftlists.model.view;

import org.example.microsoftlists.model.constants.CalendarLayout;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ViewConfig;
import org.example.microsoftlists.model.constants.ViewType;

import java.util.Map;


@Getter
@Setter
public class CalendarView implements IView {
    private CalendarLayout layout;

    private String startDate;
    private String endDate;


    @Override
    public ViewType getViewType() {
        return ViewType.CALENDAR;
    }

    @Override
    public void handleConfig(Map<ViewConfig, String> configs) {
        this.layout = CalendarLayout.valueOf(configs.get(ViewConfig.LAYOUT));
        this.startDate = configs.get(ViewConfig.START_DATE);
        this.endDate = configs.get(ViewConfig.END_DATE);
    }
}
