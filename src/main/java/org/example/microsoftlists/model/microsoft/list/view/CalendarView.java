package org.example.microsoftlists.model.microsoft.list.view;

import org.example.microsoftlists.model.constants.CalendarLayout;
import org.example.microsoftlists.model.microsoft.list.Column;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ViewType;


@Getter
@Setter
public class CalendarView extends AbstractView {
    private CalendarLayout layout;

    private Column startDate;
    private Column endDate;

    public CalendarView(String name, CalendarLayout layout, Column startDate, Column endDate) {
        super(name, ViewType.CALENDAR);
        this.layout = layout;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
