package TableModel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe che definisce il dato base per il record di voci di bilancio. Ogni oggetto di tipo Invoice contiene una data,
 * la spesa appena registrata e una breve descrizione
 */
public class Invoice implements Serializable {
    private String desc;
    private double amount;
    private LocalDate date;

    /**
     *
     * @param desc descrizione della nuova voce di bilancio
     * @param amount ammontare della spesa/guadagno
     * @param date data dell'operazione
     */
    public Invoice(String desc, double amount, LocalDate date) {
        this.desc = desc;
        this.amount = amount;
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "data: "+date+" amount: "+amount+" description: "+desc;
    }
}
