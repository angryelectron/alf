/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alfweb;

import alfd.Lift;
import alfd.Resort;
import java.util.List;
import org.joda.time.DateMidnight;

/**
 *
 * @author abythell
 */
public class Report {

    private Resort resort;
    private DateMidnight date;
    public LiftMap liftMap;
    
    public Report(DateMidnight date, Integer days) {
        this.date = date;
        liftMap = new LiftMap(date.toString(), days);
    }

    public DateMidnight getDate() {
        return date;
    }

    public List<Lift> getLifts() {
        return resort.getLifts();
    }

}
