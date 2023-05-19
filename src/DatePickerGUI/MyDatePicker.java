package DatePickerGUI;

import com.github.lgooddatepicker.components.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;



public class MyDatePicker extends DatePicker{

    public MyDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.THURSDAY);
        dateSettings.setAllowKeyboardEditing(true);
        this.setDate(LocalDate.now());
        new DatePicker(dateSettings);
    }
}
