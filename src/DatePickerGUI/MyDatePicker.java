package DatePickerGUI;

import com.github.lgooddatepicker.components.*;
import java.time.DayOfWeek;
import java.time.LocalTime;



public class MyDatePicker extends DatePicker{

    public MyDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        new DatePicker(dateSettings);
    }
}
