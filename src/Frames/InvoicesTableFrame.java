

package Frames;

import DatePickerGUI.MyDatePicker;
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


        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        JPanel topPanel=new JPanel(new BorderLayout(10,10));
        JPanel tablePanel=new JPanel(new BorderLayout(10,10));
        JPanel bottomPanel=new JPanel(new BorderLayout(10,10));
        JPanel filterPanel=new JPanel(new BorderLayout(10,10));


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


        tablePanel.add(new JScrollPane(mainTable));
        JLabel filterLabel = new JLabel(" Filter:");
        filterPanel.add(filterLabel, BorderLayout.WEST);
        filterPanel.add(filterText, BorderLayout.CENTER);
        bottomPanel.add(addButton,BorderLayout.WEST);
        bottomPanel.add(deleteButton,BorderLayout.EAST);

        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);
        add(mainPanel);
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
                MyDatePicker addDatePicker= new MyDatePicker();
                JTextField lastName = new JTextField();
                JTextArea descriptionField= new JTextArea();
                final JComponent[] inputsComponent = new JComponent[] {
                        new JLabel("Date:"),
                        addDatePicker,
                        new JLabel("Amount"),
                        lastName,
                        new JLabel("Brief description:"),
                        descriptionField
                };
                int addPanelExitStatus = JOptionPane.showConfirmDialog(tablePanel, inputsComponent, "Add an invoice", JOptionPane.OK_CANCEL_OPTION);
                if (addPanelExitStatus == JOptionPane.OK_OPTION) {
                    System.out.println("You entered " +
                            addDatePicker.getDate().toString() + ", " +
                            lastName.getText() + ", " +
                            descriptionField.getText());
                } else {
                    System.out.println("User canceled/closed the dialog, exit status = " + addPanelExitStatus);
                }
            }
        };

        addButton.addActionListener(addListener);
        deleteButton.addActionListener(deleteListener);
        popupDelete.addActionListener(deleteListener);

        filterText.getDocument().addDocumentListener(regexFilter);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
