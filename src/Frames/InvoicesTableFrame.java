package Frames;

import Filters.searchFilter;
import Listeners.*;
import Panels.datePanel;
import SaveLoadExport.SaverLoaderExporter;
import SaveLoadExport.excelExport;
import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Frame principale dell'applicazione
 */
public class InvoicesTableFrame extends JFrame {
    public InvoicesTableModel mainModel; //TODO metodo private quando togli dati di esempio dal main


    public InvoicesTableFrame() {
        setTitle("Gestione Bilancio Taccini");

        mainModel=new InvoicesTableModel();
        JTable mainTable = new JTable(mainModel);
        mainTable.getColumnModel().getColumn(3).setMaxWidth(25);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        mainTable.setDefaultRenderer(String.class, centerRenderer);
        mainTable.setDefaultRenderer(Double.class, centerRenderer);
        mainTable.setDefaultRenderer(LocalDate.class, centerRenderer);

        final TableRowSorter<InvoicesTableModel> sorter = new TableRowSorter<>(mainModel);
        mainTable.setRowSorter(sorter);

        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        JPanel topPanel=new JPanel(new BorderLayout(10,10));
        JPanel tablePanel=new JPanel(new BorderLayout(10,10));
        JPanel bottomPanel=new JPanel();

        datePanel mainDatePanel=new datePanel(sorter);
        JPanel filterPanel=new JPanel(new BorderLayout(10,10));
        JPanel filterTypeSelectionPanel= new JPanel();
        filterTypeSelectionPanel.setLayout(new BoxLayout(filterTypeSelectionPanel,BoxLayout.X_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15,10, 5, 10));

        JPopupMenu mainPopupMenu = new JPopupMenu();

        final JTextField filterText = new JTextField("");
        final JButton addButton = new JButton("ADD");
        final JButton deleteButton = new JButton("DELETE");

        final JMenuItem popupDelete = new JMenuItem("Delete");
        final JMenuItem popupEdit = new JMenuItem("Edit Row");

        final JLabel totalLabel = new JLabel();
        final JLabel filterTypeSelectionLabel= new JLabel("Filter by:");
        final JButton resetFiltersBtn = new JButton("RESET FILTERS \uD83D\uDD04");
//        JButton printButton =new JButton("Print");

        final ButtonGroup filterTypeSelectionGroup= new ButtonGroup();
        final JRadioButton filterByRegexBtn = new JRadioButton("Text Search");
        final JRadioButton filterByDateBtn = new JRadioButton("Date");
        filterByDateBtn.setBorder(BorderFactory.createEmptyBorder(0,10, 0, 10));
        filterByRegexBtn.setBorder(BorderFactory.createEmptyBorder(0,20, 0, 10));

        final JMenuBar menuBar= new JMenuBar();
        final JMenu fileMenu= new JMenu("File");
        final JMenu saveExportMenu= new JMenu("Save/Export data");
        final JMenuItem saveMenuItem= new JMenuItem("Save...");
        final JMenuItem loadMenuItem= new JMenuItem("Load from file...");
        final JMenuItem excelExportItem= new JMenuItem("Export in Excel");



        saveExportMenu.add(saveMenuItem);
        saveExportMenu.add(excelExportItem);
        fileMenu.add(saveExportMenu);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);



        filterTypeSelectionGroup.add(filterByRegexBtn);
        filterTypeSelectionGroup.add(filterByDateBtn);
        filterTypeSelectionPanel.add(filterTypeSelectionLabel);
        filterTypeSelectionPanel.add(filterByRegexBtn);
        filterTypeSelectionPanel.add(filterByDateBtn);
        filterTypeSelectionPanel.add(resetFiltersBtn,BorderLayout.EAST);

        saveMenuItem.addActionListener(new SaverLoaderExporter(mainModel));
        loadMenuItem.addActionListener(new SaverLoaderExporter(mainModel));

        excelExport exporter= new excelExport(mainModel);
        excelExportItem.addActionListener(e -> {
            try {
                exporter.export();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        resetFiltersBtn.addActionListener(e -> {
            mainDatePanel.resetPanel();
            sorter.setRowFilter(null);
            filterText.setText(null);
            mainDatePanel.setVisible(false);
            filterPanel.setVisible(false);
            filterTypeSelectionGroup.clearSelection();
        });

        filterByRegexBtn.addActionListener(e -> {
            mainDatePanel.resetPanel();
            sorter.setRowFilter(null);
            mainDatePanel.setVisible(false);
            filterPanel.setVisible(true);
            filterText.requestFocus();
        });

        filterByDateBtn.addActionListener(e -> {
            mainDatePanel.resetPanel();
            sorter.setRowFilter(null);
            filterPanel.setVisible(false);
            mainDatePanel.setVisible(true);
        });

//        printButton.addActionListener((ActionListener) e -> {
//            try {
//                if (! mainTable.print()) {
//                    System.err.println("User cancelled printing");
//                }
//            } catch (PrinterException e2) {
//                System.err.format("Cannot print %s%n", e2.getMessage());
//            }
//        });


        mainPopupMenu.add(popupDelete);
        mainPopupMenu.add(popupEdit);

        //aggiungo tramite la classe Frames.popupMenu la selezione automatica dell'elemento della tabella quando è premuto il tasto destro
        popupMenuSettings.setupPopupMenu(mainTable, mainPopupMenu);
        popupMenuSettings.setupDoubleClickToEdit(mainTable,sorter);

        tablePanel.add(new JScrollPane(mainTable));



        filterPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        filterPanel.add(filterText, BorderLayout.CENTER);

        topPanel.add(filterTypeSelectionPanel,BorderLayout.NORTH);
        topPanel.add(filterPanel,BorderLayout.SOUTH);
        topPanel.add(mainDatePanel);
//        topPanel.add(tabbedPane);

        bottomPanel.add(addButton,BorderLayout.NORTH);
        bottomPanel.add(deleteButton,BorderLayout.CENTER);

        bottomPanel.add(new JPanel().add(totalLabel),BorderLayout.SOUTH);

        filterPanel.setVisible(false);
        mainDatePanel.setVisible(false);

        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(tablePanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);
        add(mainPanel);


        sorter.addRowSorterListener(new sorterListener(totalLabel));
        mainModel.addTableModelListener(new totalListener(totalLabel,mainModel));
        addButton.addActionListener(new addListener(mainModel,tablePanel));
        deleteButton.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupDelete.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupEdit.addActionListener(new editListener(sorter, mainTable));
        filterText.getDocument().addDocumentListener(new searchFilter(sorter,filterText));

        //setting dell'icona da usare nella barra delle applicazioni e nella statusbar
        setIconImage(Toolkit.getDefaultToolkit().getImage("resources/TaskBarIcon.png"));

        //dimensioni finestra settate in modo da evitare collisioni tra oggetti e blocco dimensioni minime
        pack();
        setMinimumSize(this.getSize());

        //aggiunta della conferma per l'uscita dell'applicazione
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we)
            {
                String[] ObjButtons = {"Exit","Cancel"};
                int PromptResult = JOptionPane.showOptionDialog(tablePanel,
                        "Are you sure you want to exit?\nAll non-saved data will be lost.", "Closing application",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        ObjButtons,ObjButtons[0]);
                if(PromptResult==0)
                {
                    System.exit(0);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);

    }
}