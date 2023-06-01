package Filters;

import javax.swing.*;
import java.time.LocalDate;

public class LocalDateRowFilter extends RowFilter<Object,Object> {
    private LocalDate startLocalDate;
    private LocalDate endLocalDate;

    public void setStartLocalDate(LocalDate startdate){
        this.startLocalDate=startdate;
        System.out.println("start date is "+startLocalDate.toString());
    }

    public void setEndLocalDate(LocalDate enddate){
        this.endLocalDate=enddate;
        System.out.println("end date is "+endLocalDate.toString());
    }

    public LocalDateRowFilter() {
        super();
    }




    @Override
    public boolean include(Entry entry) {
        LocalDate current=(LocalDate)entry.getValue(0);
        //System.out.println(current+ " is after "+ startLocalDate.minusDays(1)+" and before "+endLocalDate.plusDays(1)+"?");
        if(startLocalDate.isEqual(endLocalDate)){
            return current.isEqual(startLocalDate);
        }
        return current.isAfter(startLocalDate.minusDays(1)) && current.isBefore(endLocalDate.plusDays(1));
    }
}