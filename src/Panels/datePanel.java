package Panels;

import DatePickerGUI.MyDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class datePanel extends JPanel implements ActionListener {
    private JComboBox <String> periodComboBox;
    public datePanel() {
        super();
        MyDatePicker customStartDate = new MyDatePicker();
        MyDatePicker customEndDate = new MyDatePicker();
        periodComboBox= new JComboBox<>(new  String[] {"Day", "Week", "Month", "Year", "Custom..."});
        periodComboBox.addActionListener(this);
        add(periodComboBox);
        add(customEndDate);
        add(customStartDate);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(periodComboBox.getItemAt(periodComboBox.getSelectedIndex()));
    }
}
