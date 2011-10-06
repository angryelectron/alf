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

interface ResortDAO {
   public void save();
   public void load(LocalDate date);
}

/**
 * Create a subclass of this Resort class to populate the Lift array
 */

@Entity public abstract class Resort implements ResortDAO{

    @Transient public ArrayList<Lift> lift = new ArrayList<Lift>();
    private Date date = new Date();
    public String name = new String();
    @Id Long id;
    protected ArrayList<Long> liftKeys = new ArrayList<Long>();


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

    /**
     * Return a Lift object with the given ID number.  Useful when comparing
     * @param id
     * @return Lift
     */
    public Lift getLift(Long id) {
        LiftDataAccess lda = new LiftDataAccess();
        return lda.find(id);
    }

    

    /**
     * Compare this resort with another resort, update lift status, and save
     * to database
     * @param resort
     */
    public void compareAndUpdate(Resort resort) {
        Resort r = (Resort) resort;
        for (Lift l : this.lift) {
            l.compareAndUpdate(r.getLift(l.Id));
        }
        this.save();
    }

    public LocalDate getDate() {
        return new LocalDate(this.date);
    }

    public void setDate(LocalDate date) {
        this.date = date.toDate();
    }



}
