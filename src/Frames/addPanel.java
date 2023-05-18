package Frames;

import TableModel.InvoicesTableModel;
//import org.jdatepicker.*;
//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;


public class addPanel extends JPanel {

    private JTextField dateTextField;
    private JTextField amountTextField;
    private JTextField descriptionTextField;


    public addPanel(InvoicesTableModel invModel){
        JLabel dateLabel= new JLabel("Date:");
        JLabel amountLabel= new JLabel("Amount:");
        JLabel descLabel= new JLabel("Description:");
        JTextField dateTextField= new JTextField("DISABILITATO TESTING");
        JTextField amountTextField= new JTextField("aaaaa");
        JTextField descTextField= new JTextField("bbbbbb");
        JButton addButton= new JButton("ADD");
        addButton.setIcon(new ImageIcon("resources/add-button.png"));
        add(dateLabel,BorderLayout.NORTH);
        add(dateTextField,BorderLayout.AFTER_LAST_LINE);
        add(amountLabel, BorderLayout.CENTER);
        add(amountTextField,BorderLayout.CENTER);
        add(descLabel,BorderLayout.SOUTH);
        add(descTextField,BorderLayout.SOUTH);
        //add(addButton);

//        UtilDateModel model = new UtilDateModel();
//        Properties p = new Properties();
//        p.put("text.today", "Today");
//        p.put("text.month", "Month");
//        p.put("text.year", "Year");
//        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
//        add(datePicker);
//        System.out.println(model.getDay());

    }

}
