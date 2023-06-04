package Listeners;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.text.DecimalFormat;

public class totalListener implements TableModelListener {

    private final JLabel totalLabel;
    private final InvoicesTableModel model;
    private static DecimalFormat decimalFormatter;

    public totalListener(JLabel totalLabel, InvoicesTableModel model) {
        this.totalLabel = totalLabel;
        this.model = model;
        decimalFormatter = new DecimalFormat("#.00");
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        totalLabel.setText(" Total income: " + decimalFormatter.format(model.getTotal()));
    }
}
