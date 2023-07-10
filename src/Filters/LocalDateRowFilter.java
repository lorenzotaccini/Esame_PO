package Filters;

import javax.swing.*;
import java.time.LocalDate;

/**
 * classe per il filtraggio di date tramite RowSorter utilizzando il formato data "LocalDate" (nuovo standard, la classe
 * RowFilter utilizza invece di default l'ormai obsoleto tipo "Date", che non comprende informazioni sulla TimeZone
 */
public class LocalDateRowFilter extends RowFilter<Object,Object> {
    private LocalDate startLocalDate;
    private LocalDate endLocalDate;

    /**
     * set della data di inizio
     */
    public void setStartLocalDate(LocalDate startdate){
        this.startLocalDate=startdate;
    }

    /**
     * set della data di fine
     */
    public void setEndLocalDate(LocalDate enddate){
        this.endLocalDate=enddate;
    }

    public LocalDateRowFilter() {
        super();
    }




    @Override
    public boolean include(Entry entry) {
        LocalDate current=(LocalDate)entry.getValue(0);

        if(startLocalDate.isEqual(endLocalDate)){
            return current.isEqual(startLocalDate);
        }
        return current.isAfter(startLocalDate.minusDays(1)) && current.isBefore(endLocalDate.plusDays(1));
    }
}