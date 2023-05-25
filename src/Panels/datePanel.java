package Panels;

import DatePickerGUI.MyDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class datePanel extends JPanel implements ActionListener {
    private final JComboBox <String> periodComboBox;
    private final MyDatePicker customStartDate;
    private final MyDatePicker customEndDate;
    public datePanel() {
        super();
        customStartDate = new MyDatePicker();
        customEndDate = new MyDatePicker();
        periodComboBox= new JComboBox<>(new  String[] {"Day", "Week", "Month", "Year", "Custom..."});
        periodComboBox.addActionListener(this);
        add(periodComboBox);
        add(customStartDate);
        add(customEndDate);
        customEndDate.setEnabled(false);

    }

    //        //filtro sole date
//        List<RowFilter<Object,Object>> dateFilterIntervalArray = new ArrayList<RowFilter<Object,Object>>(2);
//        dateFilterIntervalArray.add( RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, startDate) );
//        dateFilterIntervalArray.add( RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, endDate) );
//        RowFilter<Object, Object> dateIntervalRowFilter = RowFilter.andFilter(dateFilterIntervalArray); //andfilter tra data inizio e data fine
//

    @Override
    public void actionPerformed(ActionEvent e) {
        customEndDate.setEnabled(false);
        switch(periodComboBox.getItemAt(periodComboBox.getSelectedIndex())){
            case "Day":
                break;
            case "Week":
                break;
            case "Month":
                break;
            case "Year":
                break;
            case "Custom...":
                customEndDate.setEnabled(true);
                break;
        }

    }
}
