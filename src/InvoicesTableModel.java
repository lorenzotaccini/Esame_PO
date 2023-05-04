import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe TableModel che estende la classe AbstractTableModel per adattarla alle esigenze del progetto.
 * Si utilizza una ArrayList come struttura dati per il salvataggio e la gestione delle voci di bilancio.
 *
 */
public class InvoicesTableModel extends AbstractTableModel {
    /**
     * ArrayList di generics di tipo invoice
     */
    private ArrayList<Invoice> invoiceSet=new ArrayList<>();
    String[] columnNames = {"Date", "Amount", "Description"};

    public InvoicesTableModel() {
        super();
        invoiceSet=new ArrayList<>();
    }
    /** metodo per aggiungere elementi alla tabella, viene notificato il cambiamento a tutti i listener */
    public void addInvoice(String desc, BigDecimal amount, LocalDateTime date) {
        invoiceSet.add(new Invoice(desc,amount,date));
        fireTableDataChanged();
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
        switch(columnIndex){
            case 0: return invoiceSet.get(rowIndex).getDate();
            case 1: return invoiceSet.get(rowIndex).getAmount();
            case 2: return invoiceSet.get(rowIndex).getDesc();
        }
        return null;
    }
}
