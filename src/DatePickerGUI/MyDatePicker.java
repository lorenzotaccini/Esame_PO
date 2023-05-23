package DatePickerGUI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import java.time.DayOfWeek;
import java.time.LocalDate;



public class MyDatePicker extends DatePicker {

    public MyDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        dateSettings.setAllowKeyboardEditing(false);
        super.setSettings(dateSettings);
        super.setDate(LocalDate.now());
    }
}
