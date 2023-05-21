package Listeners;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class deleteListener implements ActionListener {
    private final InvoicesTableModel model;
    private final JTable mainTable;

    public deleteListener(InvoicesTableModel model, JTable mainTable) {
        this.model=model;
        this.mainTable=mainTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int [] vSel= mainTable.getSelectedRows();

        //scorro gli elementi dall'ultimo per evitare il cambio di indice nel tablemodel
        for(int actualIndex = vSel.length-1; actualIndex>=0; actualIndex--){
            try {
                model.deleteInvoice(model.getInvoiceAtRow(vSel[actualIndex]));
            }
            //Indexoutofboundexception quando cancello l'ultimo elemento presente nell'arraylist (unsolved)
            catch (IndexOutOfBoundsException iob){
                System.out.println("\nTable is now empty\n");
            }
        }
    }
}
