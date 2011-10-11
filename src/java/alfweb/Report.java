/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alfweb;

import alfd.Lift;
import alfd.Resort;
import alfd.Resort.ResortStatus;
import alfd.WhistlerResort;
import java.util.List;
import org.joda.time.LocalDate;

/**
 *
 * @author abythell
 */
public class Report {

    private Resort resort;
    private LocalDate date;
    public LiftMap liftMap;
    
    public Report(LocalDate date, Integer days) {
        this.date = date;
        liftMap = new LiftMap(date.toString(), days);
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Lift> getLifts() {
        return resort.getLifts();
    }

}
