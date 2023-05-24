package Listeners;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class totalListener implements TableModelListener {

    private JLabel totalLabel;
    private InvoicesTableModel model;

    public totalListener(JLabel totalLabel, InvoicesTableModel model) {
        this.totalLabel = totalLabel;
        this.model = model;
    }
    @Override
    public void tableChanged(TableModelEvent e) {
        totalLabel.setText(" Total income: "+model.getTotal());
    }
}
