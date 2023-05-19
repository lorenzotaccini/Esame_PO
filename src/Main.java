import Frames.InvoicesTableFrame;
import TableModel.Invoice;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void initUI(){
        try {

            UIManager.setLookAndFeel( new FlatDarculaLaf() );
            UIManager.put("Table.showVerticalLines",true);
            UIManager.put("Table.showHorizontalLines",true);
            UIManager.put( "Button.arc", 30 );
            UIManager.put( "Component.arc", 30 );
            UIManager.put( "ProgressBar.arc", 30 );
            UIManager.put( "TextComponent.arc", 30 );
            UIManager.put( "ScrollBar.thumbArc", 999 );
            UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }



    /**
     * @author Lorenzo Taccini
     */
    public static void main(String[] args) {

        //applicazione di look and feel personalizzati
        initUI();

        //generazione frame principale
        InvoicesTableFrame frame=new InvoicesTableFrame();
        frame.mainModel.addInvoice(new Invoice("miao",10, LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDate.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDate.now()));

    }
}