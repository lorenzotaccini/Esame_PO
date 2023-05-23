package Listeners;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;

public class sorterListener implements RowSorterListener {
    private final JLabel totalLabel;
    private double total;
    //private final TableRowSorter<InvoicesTableModel> parentSorter;

    public sorterListener(JLabel totalLabel) {
        this.totalLabel=totalLabel;
        //this.parentSorter = parentSorter;
    }

    @Override
    public void sorterChanged(RowSorterEvent e) {
        //System.out.println(e.toString());
        total=0;

        InvoicesTableModel model= (InvoicesTableModel) e.getSource().getModel();
        if (e.getSource().getViewRowCount() == 0) {
            totalLabel.setText(" Total income: "+ 0);
        }
        else{
            for(int i=0; i<e.getSource().getViewRowCount(); i++){
                total+=(Double)model.getValueAt(e.getSource().convertRowIndexToModel(i),1);
                totalLabel.setText(" Total income: "+ total);
            }
        }


    }
}
