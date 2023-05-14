

package Frames;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;


public class InvoicesTableFrame extends JFrame {
    private JTable mainTable;
    public InvoicesTableModel mainModel;

    public InvoicesTableFrame() {
        setTitle("Gestione Bilancio Taccini");

        mainModel=new InvoicesTableModel();
        mainTable = new JTable(mainModel);
        final TableRowSorter<InvoicesTableModel> sorter = new TableRowSorter<>(mainModel);
        mainTable.setRowSorter(sorter);

        JPopupMenu mainPopupMenu = new JPopupMenu();
        final JTextField filterText = new JTextField("");
        final JButton addButton = new JButton("ADD");
        final JButton deleteButton = new JButton("DELETE");
        final JMenuItem popupDelete = new JMenuItem("Delete");
        final JMenuBar mainMenuBar = new JMenuBar();


        DocumentListener regexFilter = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = filterText.getText();
                if(text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        /**
                         * il RowFilter può essere impostato per controllare le regex solo su specifici campi del
                         * table model, è sufficiente passargli gli indici delle colonne su cui deve operare.
                         */
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch(PatternSyntaxException pse) {
                        System.out.println("Bad regex pattern");
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                insertUpdate(e);
            }
        };

        mainPopupMenu.add(popupDelete);
        //aggiungo tramite la classe Frames.popupMenu la selezione automatica dell'elemento della tabella quando è premuto il tasto destro
        popupMenu.setupPopupMenu(mainTable, mainPopupMenu);

        //
        add(new JScrollPane(mainTable), BorderLayout.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Filter");
        panel.add(label, BorderLayout.WEST);


        panel.add(filterText, BorderLayout.CENTER);
        //panel.add(addButton,BorderLayout.SOUTH);
        panel.add(deleteButton,BorderLayout.EAST);
        add(panel, BorderLayout.NORTH);
        //add(mainMenuBar);

        /**
         * il listener per l'eliminazione è istanziato separatamente per essere utilizzato da più elementi.
         */
        ActionListener deleteListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int [] vSel= mainTable.getSelectedRows();
                System.out.println(vSel.length);

                //scorro gli elementi dall'ultimo per evitare il cambio di indice nel tablemodel
                for(int actualIndex = vSel.length-1; actualIndex>=0; actualIndex--){
                    try {
                        mainModel.deleteInvoice(mainModel.getInvoiceAtRow(vSel[actualIndex]));
                    }
                    //Indexoutofboundexception quando cancello l'ultimo elemento presente nell'arraylist (unsolved)
                    catch (IndexOutOfBoundsException iob){
                        System.out.println("\nTable is now empty\n");
                    }
                }
            }
        };

        ActionListener addListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        //addButton.addActionListener();
        addFrame addframe= new addFrame(mainModel);
        deleteButton.addActionListener(deleteListener);
        popupDelete.addActionListener(deleteListener);

        filterText.getDocument().addDocumentListener(regexFilter);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
