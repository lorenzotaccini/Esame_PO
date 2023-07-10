package Listeners;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.text.DecimalFormat;

/**
 * La classe implementa {@link TableModelListener} e, prendendo in input una JLabel contenente il bilancio totale, ne aggiorna
 * il valore ogniqualvolta esso venga modificato dall'utente tramite operazioni di add/delete/edit di elementi.
 */
public class totalListener implements TableModelListener {

    private final JLabel totalLabel;
    private final InvoicesTableModel model;
    private static DecimalFormat decimalFormatter;

    /**
     *
     * @param totalLabel {@link JLabel} su cui scrivere il bilancio totale
     * @param model modello della tabella da cui ottenere il valore di bilancio attuale
     */
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
