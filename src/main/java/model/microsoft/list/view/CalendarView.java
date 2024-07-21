package model.microsoft.list.view;

import lombok.Getter;
import lombok.Setter;
import model.constants.CalendarLayout;
import model.constants.ViewType;
import model.microsoft.list.Column;


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
