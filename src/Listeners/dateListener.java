package Listeners;

import TableModel.InvoicesTableModel;

import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class dateListener implements ActionListener {
    private final TableRowSorter<InvoicesTableModel> parentSorter;
    private int range;

    public dateListener( TableRowSorter<InvoicesTableModel> parentSorter) {
        this.parentSorter = parentSorter;
        this.range=0;
        Calendar cal= Calendar.getInstance();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
//            case "Back" ->;
//            case "Next" ->;
            case "Day" ->range=1;
            case "Week" ->range=7;
            case "Month" ->range=30;
            case "Year" ->range=365;
        }
    }
}