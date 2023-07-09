package TableModel;

import SaveLoadExport.AbstractSaverLoaderExporter;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe TableModel che estende la classe AbstractTableModel per adattarla alle esigenze del progetto.
 * Si utilizza una ArrayList come struttura dati per il salvataggio e la gestione delle voci di bilancio.
 * (Oggetto Vector ormai deprecato)
 */
public class InvoicesTableModel extends AbstractTableModel implements Serializable {
    /**
     * ArrayList di generics di tipo invoice
     */
    private ArrayList<Invoice> invoiceSet;
    private double total=0;
    String[] columnNames = {"Date", "Amount", "Description","+/-"};

    public InvoicesTableModel() {
        super();
        invoiceSet=new ArrayList<>();
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /** metodo per aggiungere elementi alla tabella, viene notificato il cambiamento a tutti i listener */
    public void addInvoice(Invoice t) {
        invoiceSet.add(t);
        total=total+t.getAmount();
        System.out.println("added "+ t);
        fireTableDataChanged();
    }

    /** Si utilizza un oggetto di classe Iterator per scorrere il vettore arrayList e rimuovere l'oggetto */
    public void deleteInvoice(Invoice delItem){
        Iterator<Invoice> itr = invoiceSet.iterator();
        while(itr.hasNext()){
            Invoice actual= itr.next();
            if(delItem.equals(actual)){
                total=total-actual.getAmount();
                itr.remove(); //rimozione dell'elemento tramite iteratore
                fireTableDataChanged();
                return;
            }
        }

    }

    @Override
    public int getRowCount() {
        return this.invoiceSet.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return invoiceSet.get(rowIndex).getDate();
            }
            case 1 -> {
                return invoiceSet.get(rowIndex).getAmount();
            }
            case 2 -> {
                return invoiceSet.get(rowIndex).getDesc();
            }
            case 3 -> {
                if(invoiceSet.get(rowIndex).getAmount()>=0){
                    return "\uD83E\uDC75";
                }
                else{
                    return "\uD83E\uDC76";
                }
            }
            default -> {
                return null;
            }
        }
    }

    public ArrayList<Invoice> getInvoiceSet() {
        return invoiceSet;
    }

    public void setInvoiceSet(ArrayList<Invoice> newSet){
        this.invoiceSet=newSet;
        Iterator<Invoice> itr = invoiceSet.iterator();
        total=0;
        while(itr.hasNext()){
            Invoice actual= itr.next();
            total+=actual.getAmount();
        }
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        switch (columnIndex){
            case 0 -> invoiceSet.get(rowIndex).setDate((LocalDate) aValue);
            case 1 -> {
                total -= invoiceSet.get(rowIndex).getAmount();
                invoiceSet.get(rowIndex).setAmount((Double) aValue);
                total+= invoiceSet.get(rowIndex).getAmount();
            }
            case 2 -> invoiceSet.get(rowIndex).setDesc(((String) aValue));
        }
        fireTableDataChanged();
    }

    public Invoice getInvoiceAtRow(int index){
        return invoiceSet.get(index);
    }

    /**
     * @return 1 se la cella è editabile, 0 altrimenti
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }


    /** Metodo che restituisce la classe del dato contenuto in una certa colonna
     * @return Classe del dato in colonna columnIndex, null se invoiceSet è vuota
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(getRowCount()==0){
            return Object.class;
        }
        //si guarda alla prima riga
        return getValueAt(0, columnIndex).getClass();
    }

    public void loadFromFile(AbstractSaverLoaderExporter saver, File file) throws IOException {
        System.out.println("Loading table content from file: "+file.getPath()+"\n");
        this.setInvoiceSet(saver.loadData(file));
        System.out.println("Loaded successfully.\n");
    }

}
