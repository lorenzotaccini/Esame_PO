import Frames.InvoicesTableFrame;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;


public class Main {
    /**
     * Metodo che carica un tema personalizzato di swing (FlatLaf) e ne setta alcuni parametri
     * tramite {@link UIManager}
     */
    public static void initUI(){
        try {

            UIManager.setLookAndFeel( new FlatMacLightLaf() );
            UIManager.put("Table.showVerticalLines",true);
            UIManager.put("Table.showHorizontalLines",true);
            UIManager.put( "Button.arc", 10 );
            UIManager.put( "Component.arc", 10 );
            UIManager.put( "ProgressBar.arc", 10 );
            UIManager.put( "TextComponent.arc", 10 );
            UIManager.put( "ScrollBar.thumbArc", 999 );
            UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
            UIManager.put( "Table.intercellSpacing", new Dimension( 0, 1 ) );
            UIManager.put( "Table.selectionBackground", new ColorUIResource(216, 223, 255));
            UIManager.put( "Table.selectionForeground", new ColorUIResource(0, 0, 0));
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize FlatLaF" );
        }
    }



    /**
     * @author Lorenzo Taccini
     * Funzione main del progetto "gestione di bilancio", esame di Programmazione ad Oggetti, AA 2022/2023
     */
    public static void main(String[] args) {

        //applicazione di look and feel personalizzati basati su FlatLaF
        initUI();

        //generazione frame principale
        InvoicesTableFrame frame = new InvoicesTableFrame();
        frame.setVisible(true);

    }
}