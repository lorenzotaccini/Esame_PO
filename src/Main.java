import Frames.InvoicesTableFrame;
import TableModel.Invoice;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;


public class Main {
    public static void initUI(){
        try {

            //FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#521fa3"));
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
     */
    public static void main(String[] args) {

        //applicazione di look and feel personalizzati basati su FlatLaF
        initUI();

        //generazione frame principale
        InvoicesTableFrame frame=new InvoicesTableFrame();

        //aggiunta dati di test
        frame.mainModel.addInvoice(new Invoice("miao",10, LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.ofYearDay(2013,201)));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now().minus(89, ChronoUnit.DAYS)));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10, LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now().minus(589, ChronoUnit.DAYS)));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDate.now().minus(75, ChronoUnit.WEEKS)));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.ofYearDay(2001,256)));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now()));


    }
}