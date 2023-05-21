package Frames;

import DatePickerGUI.MyDatePicker;
import Listeners.addListener;
import Listeners.deleteListener;
import Listeners.editListener;
import TableModel.Invoice;
import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;


public class InvoicesTableFrame extends JFrame {
    private final JTable mainTable;
    public InvoicesTableModel mainModel; //TODO metodo private quando togli dati di esempio dal main
    private String searchedText;

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
        final JMenuItem popupEdit = new JMenuItem("Edit Row");
        final JMenuBar mainMenuBar = new JMenuBar();

        //necessaria la creazione di un rowfilter solo per le date (searchbox vuota)
        //e di un rowfilter in and tra data e regex (filtro date attivato, searchbox non vuota)

//        //filtro sole date
//        List<RowFilter<Object,Object>> dateFilterIntervalArray = new ArrayList<RowFilter<Object,Object>>(2);
//        dateFilterIntervalArray.add( RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, startDate) );
//        dateFilterIntervalArray.add( RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, endDate) );
//        RowFilter<Object, Object> dateIntervalRowFilter = RowFilter.andFilter(dateFilterIntervalArray); //andfilter tra data inizio e data fine
//
//        //filtro in and tra date e regex
//        searchedText = null;
//        List<RowFilter<Object,Object>> filtersArray = new ArrayList<>(2);
//        filtersArray.add(RowFilter.regexFilter(searchedText));
//        filtersArray.add(dateIntervalRowFilter);
//        RowFilter<Object, Object> dateAndRegexRowFilter = RowFilter.andFilter(filtersArray); //andfilter tra data inizio e data fine


        DocumentListener regexFilter = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchedText = filterText.getText();
                if(searchedText.length() == 0) {
                    //searchbox vuota, filtro solo per date interval
                    sorter.setRowFilter(null);
                } else {
                    try {
                        /*
                          il RowFilter può essere impostato per controllare le regex solo su specifici campi del
                          table model, è sufficiente passargli gli indici delle colonne su cui deve operare.
                         */
                        sorter.setRowFilter(RowFilter.regexFilter(searchedText));
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

        //        DocumentListener regexFilter = new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                String text = filterText.getText();
//                if(text.length() == 0) {
//                    sorter.setRowFilter(null);
//                } else {
//                    try {
//                        /*
//                          il RowFilter può essere impostato per controllare le regex solo su specifici campi del
//                          table model, è sufficiente passargli gli indici delle colonne su cui deve operare.
//                         */
//                        sorter.setRowFilter(RowFilter.regexFilter(text));
//                    } catch(PatternSyntaxException pse) {
//                        System.out.println("Bad regex pattern");
//                    }
//                }
//            }

        mainPopupMenu.add(popupDelete);
        mainPopupMenu.add(popupEdit);
        //aggiungo tramite la classe Frames.popupMenu la selezione automatica dell'elemento della tabella quando è premuto il tasto destro
        popupMenuSettings.setupPopupMenu(mainTable, mainPopupMenu);


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



//        //il listener per l'eliminazione è istanziato separatamente per essere utilizzato da più elementi.
//        ActionListener deleteListener= new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int [] vSel= mainTable.getSelectedRows();
//
//                //scorro gli elementi dall'ultimo per evitare il cambio di indice nel tablemodel
//                for(int actualIndex = vSel.length-1; actualIndex>=0; actualIndex--){
//                    try {
//                        mainModel.deleteInvoice(mainModel.getInvoiceAtRow(vSel[actualIndex]));
//                    }
//                    //Indexoutofboundexception quando cancello l'ultimo elemento presente nell'arraylist (unsolved)
//                    catch (IndexOutOfBoundsException iob){
//                        System.out.println("\nTable is now empty\n");
//                    }
//                }
//            }
//        };

//        ActionListener addListener= new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MyDatePicker addDatePicker= new MyDatePicker();
//                JTextField amountField = new JTextField();
//                JTextArea descriptionField= new JTextArea(5,20);
//                descriptionField.setFont(new Font("corsivo",Font.ITALIC,descriptionField.getFont().getSize()));
//
//                final JComponent[] inputsComponent = new JComponent[] {
//                        new JLabel("Date:"),
//                        addDatePicker,
//                        new JLabel("Amount"),
//                        amountField,
//                        new JLabel("Brief description:"),
//                        descriptionField
//                };
//                int addPanelExitStatus = JOptionPane.showConfirmDialog(tablePanel, inputsComponent, "Add an invoice", JOptionPane.DEFAULT_OPTION);
//
//                if (addPanelExitStatus == JOptionPane.OK_OPTION) {
//                    mainModel.addInvoice(new Invoice(descriptionField.getText(),Float.parseFloat(amountField.getText()), addDatePicker.getDate()));
//                } else {
//                    System.out.println("User canceled/closed the dialog, exit status = " + addPanelExitStatus);
//                }
//            }
//        };

        addButton.addActionListener(new addListener(mainModel,tablePanel));
        deleteButton.addActionListener(new deleteListener(mainModel,mainTable));
        popupDelete.addActionListener(new deleteListener(mainModel,mainTable));
        popupEdit.addActionListener(new editListener(mainModel,mainTable));
        filterText.getDocument().addDocumentListener(regexFilter);

        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
