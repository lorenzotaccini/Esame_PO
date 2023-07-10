package SaveLoadExport;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Objects;

/**
 * Classe che estende {@link ActionListener} per la gestione di salvataggi caricamenti ed esportazioni,
 * con funzioni specifiche, apertura di pannelli {@link JFileChooser} e controlli per i file già esistenti.
 */
public class SaverLoaderExporter implements ActionListener {

    private final InvoicesTableModel model;
    private final boolean close;
    private String lastPath;

    /**
     * Instantiates a new Saver loader exporter.
     *
     * @param model il modello da cui prelevare i dati/su cui scrivere
     * @param close valore booleano: se true, il programma viene chiuso dopo un salvataggio
     */
    public SaverLoaderExporter(InvoicesTableModel model,boolean close) {
        this.model = model;
        this.close=close;
    }

    /**
     * Funzione che controlla se il file su cui si vuole salvare esiste già nella directory corrente, e in caso affermativo
     * chiede conferma per fare l'overwrite.
     * @param file file di cui verificare l'esistenza
     * @return stato del controllo, -2 se il file non esiste ancora
     */
    private int checkFileExistence(File file){

        String path = file.getAbsolutePath().replace(file.getName(),"");
        File listfile = new File(path);
        for(String filename: Objects.requireNonNull(listfile.list())){
            if (filename.equals(file.getName())){
                return JOptionPane.showConfirmDialog(null, filename+" already exists, overwrite it?","Confirm overwrite",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
            }
        }
        return -2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("inizio salvataggio/caricamento");

        AbstractSaverLoaderExporter saver;
        JFileChooser fileChooser = new JFileChooser(lastPath);
        fileChooser.setCurrentDirectory(new File("./Saves"));

        FileNameExtensionFilter binary = new FileNameExtensionFilter("Binary File (*.bin)", "bin");
        FileNameExtensionFilter csv = new FileNameExtensionFilter("CSV File (*.csv)", "csv");
        FileNameExtensionFilter text = new FileNameExtensionFilter("Text File (*.txt)", "txt");
        fileChooser.addChoosableFileFilter(binary);
        fileChooser.addChoosableFileFilter(csv);
        fileChooser.addChoosableFileFilter(text);
        fileChooser.setDialogTitle(e.getActionCommand()+" File");
        fileChooser.setAcceptAllFileFilterUsed(false); //non viene mostrata l'opzione "all files"

        // set Default option for export or simply save

        int userSelection = JOptionPane.CANCEL_OPTION;

        switch (e.getActionCommand()){
            case "Save" -> {
                fileChooser.setFileFilter(binary);
                userSelection = fileChooser.showSaveDialog(null);
            }
            case "Load from file" -> {
                fileChooser.setFileFilter(new FileNameExtensionFilter("Binary, CSV or text (*.bin, *.csv, *.txt)","bin","csv","txt"));
                userSelection = fileChooser.showOpenDialog(null);
            }

        }

        if (userSelection == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String extension = "";
            String filterDescr = fileChooser.getFileFilter().getDescription();
            String filePath = file.getAbsolutePath();
            switch(e.getActionCommand()){
                case "Save" -> {

                    if(filterDescr.equals("Binary File (*.bin)") || filePath.endsWith("bin")){
                        saver = new binarySaverLoader();
                        extension = ".bin";
                    }
                    else if (filterDescr.equals("CSV File (*.csv)") || filePath.endsWith("csv")){
                        saver = new csvSaverLoader();
                        extension = ".csv";
                    }
                    else if (filterDescr.equals("Text File (*.txt)") || filePath.endsWith("txt")){
                        saver = new textSaverLoader();
                        extension = ".txt";
                    }
                    else
                        saver = new binarySaverLoader();

                    if(!filePath.endsWith(extension)) {
                        file = new File(filePath + extension);
                    }

                    // Check if File exists
                    int exitStatus = checkFileExistence(file);
                    if (exitStatus == -2 || exitStatus == JOptionPane.YES_OPTION) {
                        lastPath = filePath.replace(file.getName(), "");
                        try {
                            //model.loadFromFile(saver, file);
                            saver.saveData(model,file);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (exitStatus == JOptionPane.NO_OPTION) {
                        actionPerformed(e);
                    }
                }


                case "Load from file"->{
                    // Open and import
                    if (e.getActionCommand().equals("Load from file")) {
                                file = fileChooser.getSelectedFile();
                                try {
                                    filePath = file.getAbsolutePath();
                                    filterDescr = fileChooser.getFileFilter().getDescription();
                                    if(filterDescr.equals("Binary File (*.bin)") || filePath.endsWith("bin"))
                                        saver = new binarySaverLoader();
                                    else if (filterDescr.equals("CSV File (*.csv)") || filePath.endsWith("csv"))
                                        saver = new csvSaverLoader();
                                    else if (filterDescr.equals("Text File (*.txt)") || filePath.endsWith("txt"))
                                        saver = new textSaverLoader();
                                    else
                                        saver = new binarySaverLoader();

                                    this.model.loadFromFile(saver,file);
                                    lastPath = filePath.replace(file.getName(),"");
                                } catch (StreamCorruptedException ex){
                                    JOptionPane.showConfirmDialog(fileChooser,"Wrong file type!","File type error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                        }
                    }

        }

        System.out.println("fine salvataggio/caricamento");
        if(close && e.getActionCommand().equals("Save")){ //chiudi dopo salvataggio
            System.exit(0);
        }
    }

}
