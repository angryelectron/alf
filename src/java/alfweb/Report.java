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
    
    public Report(LocalDate date) {
        this.date = date;
        resort = new WhistlerResort();
        resort.load(date);
    }

    public ResortStatus getStatus() {
        return resort.getStatus();
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Lift> getLifts() {
        return resort.getLifts();
    }

}
