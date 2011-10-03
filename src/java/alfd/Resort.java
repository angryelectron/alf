/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;

import com.googlecode.objectify.Key;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Transient;

interface ResortInterface {
    public void addLift(Lift l);
    ArrayList<Lift> getLifts();
    Lift getLift(String name);
    public void load();
    public void save();
    public void scrape();
    public void compareAndUpdate(Object resort);
}

/**
 * Create a subclass of this Resort class to populate the Lift array
 */

public class Resort {

    @Transient public ArrayList<Lift> lift = new ArrayList<Lift>();
    public Date date = new Date();
    public String name = new String();
    @Transient private ResortDataAccess dao = new ResortDataAccess();
    @Id Long id;
    private ArrayList<Long> liftKeys = new ArrayList<Long>();


    /**
     * Add a new Lift object to this resort
     * @param Lift
     */
    public void addLift(Lift l) {
       this.lift.add(l);
    }

    public Long getId() {
        return id;
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
     * Scrape lift status info for this resort
     * Override to customize
     */
    public void scrape() {
        //override in subclass
    }

    /**
     * Save the Resort info to the data store
     * Creates new entries or updates existing info
     */
    public void save() {
        //write lift information to datastore
        LiftDataAccess lda = new LiftDataAccess();
        for (Lift l : lift) {
            liftKeys.add(lda.insert(l));
        }
        
        //write resort information to datastore
        ResortDataAccess rda = new ResortDataAccess();
        rda.update(this);
    }

    /**
     * Populate Lift data from the database
     * If no data has been saved, call Scrape() instead
     */
    public void load(Date date) {
        assert(date != null);
        
        //load resort info
        Resort r = dao.findByDate(this.name, date);
        if (r.id == 0) {
            this.date = date; //TODO: set this to today's date
            this.scrape();
        }
        else {
            this.id = r.id;
            this.liftKeys = r.liftKeys;
            this.date = r.date;
        }

        LiftDataAccess lda = new LiftDataAccess();
        lift = lda.findByKeyList(liftKeys);
    }

    /**
     * Compare this resort with another resort, update lift status, and save
     * to database
     * @param resort
     */
    public void compareAndUpdate(Object resort) {
        Resort r = (Resort) resort;
        for (Lift l : this.lift) {
            l.compareAndUpdate(r.getLift(l.Id));
        }
        this.save();
    }

}
