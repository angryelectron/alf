/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alfweb;

import alfd.Lift;
import alfd.Lift.LiftStatus;
import alfd.Resort;
import alfd.Resort.ResortStatus;
import alfd.WhistlerResort;
import org.joda.time.LocalDate;

/**
 *
 * @author abythell
 */
public class Cron {

    private Integer liftCount = 0;
    private Integer updateCount = 0;
    private LocalDate date = new LocalDate();
    private Resort current = new WhistlerResort();
    private Resort saved = new WhistlerResort();
    private String status;

    public Cron() {
        
        //get historical data
        saved.load(date);
        if (saved.getStatus() == ResortStatus.NODATA) {
            saved.fetch();
            if (saved.getStatus() != ResortStatus.OK) {
                status = saved.getStatus().toString();
                return;
            }
        }

        //get current data
        current.fetch();
        if (current.getStatus() != ResortStatus.OK) {
            status = saved.getStatus().toString();
            return;
        }

        //merge the two and save
        merge();
        saved.save();
        liftCount = current.lift.size();
        status = saved.getStatus().toString();
    }

    public String getStatus() {
        return status;
    }

    private void merge() {
        
        for (int i=0; i<saved.lift.size(); i++) {
            if ((saved.lift.get(i).getStatus() == LiftStatus.CLOSED) && (current.lift.get(i).getStatus() != LiftStatus.CLOSED)) {
                Lift update = saved.lift.get(i);
                update.setStatus(current.lift.get(i).getStatus());
                saved.lift.set(i, update);
                updateCount++;
            }
            else if((saved.lift.get(i).getStatus() == LiftStatus.STANDBY) && (current.lift.get(i).getStatus() == LiftStatus.OPEN)) {
                Lift update = saved.lift.get(i);
                update.setStatus(current.lift.get(i).getStatus());
                saved.lift.set(i, update);
                updateCount++;
            }
        }
    }

     public LocalDate getDate() {
        return date;
    }

    public Integer getLiftCount() {
        return liftCount;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }


}
