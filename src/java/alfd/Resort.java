/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;


import org.joda.time.LocalDate;
import com.googlecode.objectify.annotation.Entity;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.joda.time.DateTimeZone;

interface ResortDAO {
   public void save();
   public void load(LocalDate date);
   public void fetch();
}

@Entity public abstract class Resort implements ResortDAO{

    @Transient public ArrayList<Lift> lift = new ArrayList<Lift>();
    private Date date;
    public String name = new String();
    @Id Long id;
    protected ArrayList<Long> liftKeys = new ArrayList<Long>();
    public enum ResortStatus {UNKNOWN, OK, OFFLINE, NODATA};
    @Transient protected ResortStatus status = ResortStatus.UNKNOWN;

    public ResortStatus getStatus() {
        return status;
    }

    public void setStatus(ResortStatus status) {
        this.status = status;
    }

    /**
     * Add a new Lift object to this resort
     * @param Lift
     */
    public void addLift(Lift l) {
       this.lift.add(l);
    }

    /**
     * Get an array of Lift objects associated with this resort
     * @return ArrayList<Lift>
     */
    public ArrayList<Lift> getLifts() {
        return lift;
    }

    public LocalDate getDate() {
        return new LocalDate(this.date, DateTimeZone.UTC);
    }

    public void setDate(LocalDate date) {
        this.date = date.toDate();
    }

}
