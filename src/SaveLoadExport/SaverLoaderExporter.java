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

public class SaverLoaderExporter implements ActionListener {

    private InvoicesTableModel model;
    private final boolean close;
    private String lastPath;

    public SaverLoaderExporter(InvoicesTableModel model,boolean close) {
        this.model = model;
        this.close=close;
    }

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

        FileNameExtensionFilter binary = new FileNameExtensionFilter("Binary File (*.bin)", "bin");
        FileNameExtensionFilter csv = new FileNameExtensionFilter("CSV File (*.csv)", "csv");
        FileNameExtensionFilter text = new FileNameExtensionFilter("Text File (*.txt)", "txt");
        fileChooser.addChoosableFileFilter(binary);
        fileChooser.addChoosableFileFilter(csv);
        fileChooser.addChoosableFileFilter(text);
        fileChooser.setDialogTitle(e.getActionCommand()+" File");
        fileChooser.setAcceptAllFileFilterUsed(false);

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
