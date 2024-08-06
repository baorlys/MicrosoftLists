package org.example.microsoftlists.model.view;

import org.example.microsoftlists.model.constants.CalendarLayout;
import org.example.microsoftlists.model.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class CalendarView extends View {
    private CalendarLayout layout;

    private Column startDate;
    private Column endDate;


    public CalendarView(Map<String,String> data) {
        this.setData(data);

    }
}
