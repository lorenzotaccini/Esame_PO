package Listeners;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class deleteListener implements ActionListener {
    private final InvoicesTableModel model;
    private final JTable mainTable;
    private final TableRowSorter<InvoicesTableModel> parentSorter;

    public deleteListener(TableRowSorter<InvoicesTableModel> parentSorter,InvoicesTableModel model, JTable mainTable) {
        this.model=model;
        this.mainTable=mainTable;
        this.parentSorter=parentSorter;
    }

    @Override
    public void actionPerformed(ActionEvent e) { //TODO mappa elementi in caso di sorter attivo

        int[] vSel = new int[mainTable.getSelectedRows().length];
        int[] mappedVector = new int[parentSorter.getViewRowCount()];

        //non è impostato nessun sorter(searchbox vuota)
        if (parentSorter.getRowFilter() == null) {
            vSel = mainTable.getSelectedRows();
            System.out.println(Arrays.toString(vSel));
        }

        else{
            for(int i=0; i<parentSorter.getViewRowCount(); i++){
                mappedVector[i]=parentSorter.convertRowIndexToModel(i);
                vSel = mainTable.getSelectedRows();
            }
        }
        //scorro gli elementi dall'ultimo per evitare il cambio di indice nel tablemodel
        for (int actualIndex = vSel.length - 1; actualIndex >= 0; actualIndex--) {
            try {
                if (parentSorter.getRowFilter() == null) {
                    model.deleteInvoice(model.getInvoiceAtRow(vSel[actualIndex]));
                }
                else{
                    model.deleteInvoice(model.getInvoiceAtRow(mappedVector[vSel[actualIndex]]));
                }
            }
            //Indexoutofboundexception quando cancello l'ultimo elemento presente nell'arraylist (unsolved)
            catch (IndexOutOfBoundsException iob) {
                System.out.println("\nTable is now empty\n");
            }
        }
    }
}
