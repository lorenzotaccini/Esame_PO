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
    private final JButton backBtn;
    private final JButton forwardBtn;

    private final TableRowSorter<InvoicesTableModel> parentSorter;
    private final LocalDateRowFilter dateFilter;

    /**
     * @param parentSorter sorter del modello principale che viene modificato quando richiesto per il filtraggio tramite data
     */
    public datePanel(TableRowSorter<InvoicesTableModel> parentSorter) {
        super();

        this.parentSorter=parentSorter;
        dateFilter=new LocalDateRowFilter();

        setLayout(new BorderLayout());
        backBtn =new JButton(" ◀ ");
        forwardBtn = new JButton(" ▶ ");
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
        periodComboBox.addActionListener(this);
        resetPanel();

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

    }

    /**
     * Reset del pannello alle sue impostazioni di base.
     */
    public void resetPanel(){
        periodDuration=0;
        customEndDate.setDate(LocalDate.now());
        customStartDate.setDate(customEndDate.getDate().minusDays(periodDuration));
        customStartDate.setEnabled(false);
        customEndDate.setEnabled(false);
        backBtn.setEnabled(false);
        forwardBtn.setEnabled(false);
        periodComboBox.setSelectedIndex(-1);
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
            backBtn.setEnabled(true);
            forwardBtn.setEnabled(true);
            if(periodComboBox.getSelectedIndex()==-1){
                customEndDate.setEnabled(false);
                backBtn.setEnabled(false);
                forwardBtn.setEnabled(false);
            }
            else {
                switch (periodComboBox.getItemAt(periodComboBox.getSelectedIndex())) {
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

        //se è selezionato il periodo "Day" le due date vengono settate combacianti
        if(periodComboBox.getSelectedIndex()!=-1) {
            if (periodComboBox.getItemAt(periodComboBox.getSelectedIndex()).equals("Day")) {
                dateFilter.setStartLocalDate(customEndDate.getDate());
            } else {
                dateFilter.setStartLocalDate(customStartDate.getDate());
            }
            dateFilter.setEndLocalDate(customEndDate.getDate());
            parentSorter.setRowFilter(dateFilter);
        }
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        if(customEndDate.getDate().isBefore(customStartDate.getDate())){
            customEndDate.setBorder(BorderFactory.createBevelBorder(1,Color.RED,Color.RED));
            customStartDate.setBorder(BorderFactory.createBevelBorder(1,Color.RED,Color.RED));
        }
        else{
            customEndDate.setBorder(null);
            customStartDate.setBorder(null);
        }
        backBtn.setEnabled(true);
        forwardBtn.setEnabled(true);

        //se è impostata la selezione custom, le date non cambiano automaticamente in base al periodo
        if(!periodComboBox.getItemAt(periodComboBox.getSelectedIndex()).equals("Custom...")){
            customStartDate.setDate(customEndDate.getDate().minusDays(periodDuration));
        }
        dateFilter.setStartLocalDate(customStartDate.getDate());
        dateFilter.setEndLocalDate(customEndDate.getDate());
        parentSorter.setRowFilter(dateFilter);
    }
}
