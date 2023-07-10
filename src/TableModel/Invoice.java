package TableModel;

import java.io.Serializable;
import java.time.LocalDate;

public class Invoice implements Serializable {
    private String desc;
    private double amount;
    private LocalDate date;

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
