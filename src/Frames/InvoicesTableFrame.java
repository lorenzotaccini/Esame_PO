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
import java.awt.print.PrinterException;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Frame principale dell'applicazione. Costruisce l'ambiente grafico principale, contiene JTable e modello per quest'ultima
 */
public class InvoicesTableFrame extends JFrame {
    private final InvoicesTableModel mainModel;


    public InvoicesTableFrame() {
        setTitle("Gestione Bilancio Taccini");


        //CREAZIONE ELEMENTI
        mainModel=new InvoicesTableModel();
        JTable mainTable = new JTable(mainModel);
        mainTable.getColumnModel().getColumn(3).setMaxWidth(25);
        mainTable.getTableHeader().setEnabled(false); //disabilito l'header della jtable per prevenire il sorting
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

        final ButtonGroup filterTypeSelectionGroup= new ButtonGroup();
        final JRadioButton filterByRegexBtn = new JRadioButton("Text Search");
        final JRadioButton filterByDateBtn = new JRadioButton("Date");
        filterByDateBtn.setBorder(BorderFactory.createEmptyBorder(0,10, 0, 10));
        filterByRegexBtn.setBorder(BorderFactory.createEmptyBorder(0,20, 0, 10));

        final JMenuBar menuBar= new JMenuBar();
        final JMenuItem printMenu =new JMenuItem("Print");
        final JMenu fileMenu= new JMenu("File");
        final JMenu saveExportMenu= new JMenu("Save/Export data");
        final JMenuItem saveMenuItem= new JMenuItem("Save");
        final JMenuItem loadMenuItem= new JMenuItem("Load from file");
        final JMenuItem excelExportItem= new JMenuItem("Export in Excel");
        final JMenu helpMenu= new JMenu("Help");
        final JMenuItem aboutMenuItem= new JMenuItem("About...");


        //MENUBAR
        saveExportMenu.add(saveMenuItem);
        saveExportMenu.add(excelExportItem);
        fileMenu.add(saveExportMenu);
        fileMenu.add(loadMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(printMenu);
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);

        //gruppo di radio button per mutua esclusione
        filterTypeSelectionGroup.add(filterByRegexBtn);
        filterTypeSelectionGroup.add(filterByDateBtn);



        //AGGIUNTA DEI LISTENERS
        saveMenuItem.addActionListener(new SaverLoaderExporter(mainModel,false));
        loadMenuItem.addActionListener(new SaverLoaderExporter(mainModel,false));

        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(tablePanel,
                "Gestione Bilancio Taccini\n\n Version 1.0\n AA. 2022/2023\n Author: Lorenzo Taccini",
                "About this project...",
                JOptionPane.PLAIN_MESSAGE
        ));

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

        printMenu.addActionListener(e -> {
            try {
                if (! mainTable.print()) {
                    System.err.println("User cancelled printing");
                }
            } catch (PrinterException e2) {
                System.err.format("Cannot print %s%n", e2.getMessage());
            }
        });

        sorter.addRowSorterListener(new sorterListener(totalLabel));
        mainModel.addTableModelListener(new totalListener(totalLabel,mainModel));
        addButton.addActionListener(new addListener(mainModel,tablePanel));
        deleteButton.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupDelete.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupEdit.addActionListener(new editListener(sorter, mainTable));
        filterText.getDocument().addDocumentListener(new searchFilter(sorter,filterText));

        //AGGIUNTA DOPPIO CLICK E CLICK DESTRO SU TABELLA
        mainPopupMenu.add(popupDelete);
        mainPopupMenu.add(popupEdit);

        //aggiungo tramite la classe Frames.popupMenu la selezione automatica dell'elemento della tabella quando è premuto il tasto destro
        popupMenuSettings.setupPopupMenu(mainTable, mainPopupMenu);
        popupMenuSettings.setupDoubleClickToEdit(mainTable,sorter);

        //AGGIUNTA COMPONENTI AI PANNELLI

        //Jscrollpane per far scorrere la tabella
        tablePanel.add(new JScrollPane(mainTable));


        filterPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        filterPanel.add(filterText, BorderLayout.CENTER);

        filterTypeSelectionPanel.add(filterTypeSelectionLabel);
        filterTypeSelectionPanel.add(filterByRegexBtn);
        filterTypeSelectionPanel.add(filterByDateBtn);
        filterTypeSelectionPanel.add(resetFiltersBtn,BorderLayout.EAST);


        topPanel.add(filterTypeSelectionPanel,BorderLayout.NORTH);
        topPanel.add(filterPanel,BorderLayout.SOUTH);
        topPanel.add(mainDatePanel);

        bottomPanel.add(addButton,BorderLayout.WEST);
        bottomPanel.add(deleteButton,BorderLayout.CENTER);
        bottomPanel.add(totalLabel,BorderLayout.EAST);


        filterPanel.setVisible(false);
        mainDatePanel.setVisible(false);

        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(tablePanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);
        add(mainPanel);


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
                JButton confirmSaveButton= new JButton("Save");
                confirmSaveButton.addActionListener(new SaverLoaderExporter(mainModel,true));
                Object[] ObjButtons = {confirmSaveButton,"Exit","Cancel"};
                int PromptResult = JOptionPane.showOptionDialog(tablePanel,
                        "Save before exit?\nAll non-saved data will be lost.",
                        "Closing application",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        ObjButtons,ObjButtons[0]
                );

                if(PromptResult==1) //tasto exit
                {
                    System.exit(0);
                }
            }
        });

        setLocationRelativeTo(null);

    }
}