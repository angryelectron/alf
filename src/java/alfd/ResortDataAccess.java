/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import java.util.Date;

/**
 *
 * @author abythell
 */
public class ResortDataAccess {

    static {
        ObjectifyService.register(Resort.class);
    }

    private Objectify ofy;

    ResortDataAccess() {
        ofy = ObjectifyService.begin();
    }

    
    public void insert(Resort resort) {
        ofy.put(resort);
        assert(resort.id != 0);
    }

   
    /**
     * Update lift status information for a resort
     * If no lift status information is found, calls Insert instead
     * @param resort
     */
    public void update(Resort resort) {
        //TODO: fix this so it doesn't fail when updating a resort that doesn't exist
        if (resort.id != null) {
            ofy.delete(resort);
        }
        insert(resort);
    }
    
    public void delete(Long id) {
        ofy.delete(Resort.class, id);
    }

    public Resort find(Resort resort) {
        //Key<Resort> k = new Key(Resort.class, resort.id);
        return ofy.get(new Key<Resort>(Resort.class, resort.id));
    }

    public Resort findByDate(String name, Date date) {
        Query<Resort> q = ofy.query(Resort.class).filter("name=", name).filter("date=", date);
        return q.get();
    }

}
