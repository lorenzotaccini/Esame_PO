package Frames;

import TableModel.InvoicesTableModel;
import com.github.lgooddatepicker.components.*;
//import org.jdatepicker.*;
//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.UtilDateModel;


import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;


public class addPane extends JOptionPane {


    public addPane(InvoicesTableModel invModel){
        JLabel dateLabel= new JLabel("Date:");
        JLabel amountLabel= new JLabel("Amount:");
        JLabel descLabel= new JLabel("Description:");
        JTextField dateTextField = new JTextField("DISABILITATO TESTING");
        JTextField amountTextField = new JTextField("aaaaa");
        JTextField descTextField = new JTextField("bbbbbb");
        JButton addButton= new JButton("ADD");

        add(dateLabel,BorderLayout.NORTH);
        add(dateTextField,BorderLayout.AFTER_LAST_LINE);
        add(amountLabel, BorderLayout.CENTER);
        add(amountTextField,BorderLayout.CENTER);
        add(descLabel,BorderLayout.SOUTH);
        add(descTextField,BorderLayout.SOUTH);
        //add(addButton);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker1 = new DatePicker(dateSettings);
        this.add(datePicker1);

        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextValidTime, Color.blue);
        timeSettings.initialTime = LocalTime.now();
        TimePicker timePicker1 = new TimePicker(timeSettings);
        this.add(timePicker1);

    }

}
