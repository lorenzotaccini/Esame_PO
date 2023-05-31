package Panels;

import DatePickerGUI.MyDatePicker;
import Filters.LocalDateRowFilter;
import TableModel.InvoicesTableModel;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class datePanel extends JPanel implements ActionListener, DateChangeListener {
    private final JComboBox <String> periodComboBox;
    private final MyDatePicker customStartDate;
    private final MyDatePicker customEndDate;
    private int periodDuration;
    private final JPanel arrowsPanel;
    private final JLabel fromLabel;
    private final JLabel toLabel;

    private final TableRowSorter<InvoicesTableModel> parentSorter;
    private final LocalDateRowFilter dateFilter;

    public datePanel(TableRowSorter<InvoicesTableModel> parentSorter) {
        super();

        this.parentSorter=parentSorter;
        dateFilter=new LocalDateRowFilter();


        setLayout(new BorderLayout());
        JButton backBtn =new JButton(" ◀ ");
        JButton forwardBtn = new JButton(" ▶ ");
        JPanel selectionPanel = new JPanel();

        JLabel periodLabel= new JLabel("Period:");
        fromLabel= new JLabel("From:");
        toLabel= new JLabel("To:");

        toLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fromLabel.setHorizontalAlignment(SwingConstants.CENTER);
        periodLabel.setHorizontalAlignment(SwingConstants.CENTER);

        arrowsPanel= new JPanel();
        arrowsPanel.add(backBtn);
        arrowsPanel.add(forwardBtn);
        customStartDate = new MyDatePicker();
        customEndDate = new MyDatePicker();

        periodComboBox= new JComboBox<>(new  String[] {"Day", "Week", "Month", "Year", "Custom..."});
        periodComboBox.setSelectedIndex(-1);
        periodComboBox.addActionListener(this);
        backBtn.addActionListener(this);
        forwardBtn.addActionListener(this);
        customEndDate.addDateChangeListener(this);
        customStartDate.addDateChangeListener(this);

        JPanel periodPanel,fromPanel,toPanel;
        periodPanel=new JPanel(new BorderLayout());
        fromPanel=new JPanel(new BorderLayout());
        toPanel=new JPanel(new BorderLayout());

        periodPanel.add(periodLabel,BorderLayout.CENTER);
        periodPanel.add(periodComboBox,BorderLayout.SOUTH);
        fromPanel.add(fromLabel,BorderLayout.NORTH);
        fromPanel.add(customStartDate,BorderLayout.SOUTH);
        toPanel.add(toLabel,BorderLayout.NORTH);
        toPanel.add(customEndDate,BorderLayout.SOUTH);

        selectionPanel.add(periodPanel,BorderLayout.WEST);
        selectionPanel.add(fromPanel,BorderLayout.CENTER);
        selectionPanel.add(toPanel,BorderLayout.EAST);

        add(selectionPanel, BorderLayout.NORTH);
        add(arrowsPanel, BorderLayout.SOUTH);

        periodDuration=1;
        customStartDate.setEnabled(false);


    }

    public void resetFilter(){
        periodComboBox.setSelectedIndex(-1);
        parentSorter.setRowFilter(null);
        customStartDate.setDate(LocalDate.now());
        customEndDate.setDate(LocalDate.now());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JComboBox<?>){
            customStartDate.setEnabled(false);
            customStartDate.setVisible(true);
            customEndDate.setEnabled(true);
            arrowsPanel.setVisible(true);
            fromLabel.setText("From:");
            toLabel.setVisible(true);
            switch (periodComboBox.getItemAt(periodComboBox.getSelectedIndex())) {
                default -> {

                }
                case "Day" -> {
                    periodDuration = 1;
                    customStartDate.setDate(customEndDate.getDate());

                    fromLabel.setText("        Day:       ");
                    toLabel.setVisible(false);
                    customStartDate.setVisible(false);
                }
                case "Week" -> {
                    periodDuration = 7;
                    customStartDate.setDate(customEndDate.getDate().minusDays(periodDuration));
                }
                case "Month" -> {
                    periodDuration = 30;
                    customStartDate.setDate(customEndDate.getDate().minusDays(periodDuration));
                }
                case "Year" -> {
                    periodDuration = 365;
                    customStartDate.setDate(customEndDate.getDate().minusDays(periodDuration));
                }
                case "Custom..." -> {
                    customStartDate.setEnabled(true);
                    arrowsPanel.setVisible(false);
                }
            }
        }
        if(e.getSource() instanceof JButton){
            switch (e.getActionCommand()) {
                case " ◀ " -> {
                    customEndDate.setDate(customEndDate.getDate().minusDays(periodDuration));
                    customStartDate.setDate(customStartDate.getDate().minusDays(periodDuration));
                }
                case " ▶ " -> {
                    customStartDate.setDate(customStartDate.getDate().plusDays(periodDuration));
                    customEndDate.setDate(customEndDate.getDate().plusDays(periodDuration));
                }
            }
        }
        //System.out.println(Date.from(customEndDate.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if(customEndDate.getDate()!=null && customStartDate.getDate()!=null){
            dateFilter.setStartLocalDate(customStartDate.getDate());
            dateFilter.setEndLocalDate(customEndDate.getDate());
            parentSorter.setRowFilter(dateFilter);
        }
        else{
            parentSorter.setRowFilter(null);
        }
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        if(!periodComboBox.getItemAt(periodComboBox.getSelectedIndex()).equals("Custom...")){
            customStartDate.setDate(customEndDate.getDate().minusDays(periodDuration));
        }
        dateFilter.setStartLocalDate(customStartDate.getDate());
        dateFilter.setEndLocalDate(customEndDate.getDate());
        parentSorter.setRowFilter(dateFilter);

    }
}
