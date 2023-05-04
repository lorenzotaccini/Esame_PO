import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Classe TableModel che estende la classe AbstractTableModel per adattarla alle esigenze del progetto.
 * Si utilizza una ArrayList come struttura dati per il salvataggio e la gestione delle voci di bilancio.
 *
 */
public class InvoicesTableModel extends AbstractTableModel {
    /**
     * ArrayList di generics di tipo invoice
     */
    private ArrayList<Invoice> invoiceSet;

    public InvoicesTableModel(ArrayList<Invoice> invoiceSet) {
        this.invoiceSet = invoiceSet;
    }

    @Override
    public int getRowCount() {
        return this.invoiceSet.size();
    }

    @Override
    public int getColumnCount() {

        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
