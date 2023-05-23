package Frames;

import Listeners.*;
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
    public InvoicesTableModel mainModel; //TODO metodo private quando togli dati di esempio dal main


    public InvoicesTableFrame() {
        setTitle("Gestione Bilancio Taccini");


        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        JPanel topPanel=new JPanel(new BorderLayout(10,10));
        JPanel tablePanel=new JPanel(new BorderLayout(10,10));
        JPanel bottomPanel=new JPanel(new BorderLayout(10,10));
        JPanel filterPanel=new JPanel(new BorderLayout(10,10));
        JPanel filterTypeSelectionPanel= new JPanel(new BorderLayout(10,10));


        mainModel=new InvoicesTableModel();
        JTable mainTable = new JTable(mainModel);

        final TableRowSorter<InvoicesTableModel> sorter = new TableRowSorter<>(mainModel);
        mainTable.setRowSorter(sorter);

        JPopupMenu mainPopupMenu = new JPopupMenu();
        final JTextField filterText = new JTextField("");
        final JButton addButton = new JButton("ADD");
        final JButton deleteButton = new JButton("DELETE");
        final JMenuItem popupDelete = new JMenuItem("Delete");
        final JMenuItem popupEdit = new JMenuItem("Edit Row");
        final JMenuBar mainMenuBar = new JMenuBar();
        final JLabel totalLabel = new JLabel();
        final JLabel partialLabel = new JLabel();
        final JLabel filterTypeSelectionLabel= new JLabel("Filter by: ");
        final JButton restoreFiltersBtn = new JButton("RESTORE");


        //JRadioButton

        //necessaria la creazione di un rowfilter solo per le date


//        //filtro sole date
//        List<RowFilter<Object,Object>> dateFilterIntervalArray = new ArrayList<RowFilter<Object,Object>>(2);
//        dateFilterIntervalArray.add( RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, startDate) );
//        dateFilterIntervalArray.add( RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, endDate) );
//        RowFilter<Object, Object> dateIntervalRowFilter = RowFilter.andFilter(dateFilterIntervalArray); //andfilter tra data inizio e data fine
//



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

        ActionListener dateRangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(e.getActionCommand()){
                }
            }
        };



        mainPopupMenu.add(popupDelete);
        mainPopupMenu.add(popupEdit);
        //aggiungo tramite la classe Frames.popupMenu la selezione automatica dell'elemento della tabella quando è premuto il tasto destro
        popupMenuSettings.setupPopupMenu(mainTable, mainPopupMenu);


        tablePanel.add(new JScrollPane(mainTable),BorderLayout.NORTH);
        //tablePanel.add(restoreFiltersBtn,BorderLayout.PAGE_END);
        JLabel filterLabel = new JLabel(" Filter:");
        filterPanel.add(filterLabel, BorderLayout.WEST);
        filterPanel.add(filterText, BorderLayout.CENTER);
        bottomPanel.add(addButton,BorderLayout.WEST);
        bottomPanel.add(deleteButton,BorderLayout.EAST);
        bottomPanel.add(new statusPanel(this).add(totalLabel),BorderLayout.SOUTH);

        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);
        add(mainPanel);
        //add(mainMenuBar);


        sorter.addRowSorterListener(new sorterListener(partialLabel));
        mainModel.addTableModelListener(new totalListener(totalLabel,mainModel));
        addButton.addActionListener(new addListener(mainModel,tablePanel));
        deleteButton.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupDelete.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupEdit.addActionListener(new editListener(sorter,mainModel, mainTable));
        filterText.getDocument().addDocumentListener(regexFilter);

        //pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
