package SaveLoadExport;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SaverLoaderExporter implements ActionListener {

    private InvoicesTableModel model;
    private String lastPath;

    public SaverLoaderExporter(InvoicesTableModel model) {
        this.model = model;
    }

    private int checkFileExistence(File file){

        String path = file.getAbsolutePath().replace(file.getName(),"");
        File listfile = new File(path);
        for(String filename: Objects.requireNonNull(listfile.list())){
            if (filename.equals(file.getName())){
                return JOptionPane.showConfirmDialog(null, filename+" already exists, do you want to overwrite it?","Overwrite",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
            }
        }
        return -2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("inizio salvataggio");
        AbstractSaverLoaderExporter saver;
        JFileChooser fileChooser = new JFileChooser(lastPath);

        FileNameExtensionFilter binary = new FileNameExtensionFilter("Binary File (*.bin)", "bin");
        FileNameExtensionFilter csv = new FileNameExtensionFilter("CSV File (*.csv)", "csv");
        FileNameExtensionFilter text = new FileNameExtensionFilter("Text File (*.txt)", "txt");
        fileChooser.addChoosableFileFilter(binary);
        fileChooser.addChoosableFileFilter(csv);
        fileChooser.addChoosableFileFilter(text);
        fileChooser.setDialogTitle(e.getActionCommand()+" File");

        // set Default option for export or simply save

        int userSelection = JOptionPane.CANCEL_OPTION;

        switch (e.getActionCommand()){
            case "Save..." -> {
                fileChooser.setFileFilter(binary);
                userSelection = fileChooser.showSaveDialog(null);
            }
            case "Load from file" -> {
                fileChooser.setFileFilter(null);
                userSelection = fileChooser.showOpenDialog(null);
            }
            case "CSV" -> fileChooser.setFileFilter(csv);
            case "Text" -> fileChooser.setFileFilter(text);
        }

        if (userSelection == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String extension = "";
            String filterDescr = fileChooser.getFileFilter().getDescription();
            String filePath = file.getAbsolutePath();
            switch(e.getActionCommand()){
                case "Save..." -> {

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


                case "Load from file..."->{

                }
            }

        }
    }

}
