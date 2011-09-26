/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;

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

    public ArrayList<Lift> lift = new ArrayList<Lift>();
    public Date date = new Date();
    public String name = new String();
    @Transient private ResortDataAccess dao = new ResortDataAccess();
    @Id Long id;

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
    public Lift getLift(String name) {
        //TODO:  see if there is a better way to search Lists
        for (Lift l : lift) {
            if (l.getName().equals(name)) {
                return l;
            }
        }
        return null;
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
        dao.update((Resort)this);
    }

    /**
     * Populate Lift data from the database
     * If no data has been saved, call Scrape() instead
     */
    public void load() {
        Resort resort = dao.find(this);
        if (resort.id == 0) {
            this.scrape();
        }
    }

    /**
     * Compare this resort with another resort, update lift status, and save
     * to database
     * @param resort
     */
    public void compareAndUpdate(Object resort) {
        Resort r = (Resort) resort;
        for (Lift l : this.lift) {
            l.compareAndUpdate(r.getLift(l.getName()));
        }
        this.save();
    }

}
