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

/**
 *
 * @author abythell
 */
public class LiftDataAccess {

    static {
        ObjectifyService.register(Lift.class);
    }

    private Objectify ofy;

    LiftDataAccess() {
        ofy = ObjectifyService.begin();
    }
    
    public void insert(Lift lift) {
        ofy.put(lift);
    }

    public void update(Lift lift) {
        ofy.put(lift);
    }
    
    public void delete(Lift lift) {
        ofy.delete(lift);
    }

    public Lift find(Key k) {
        return (Lift) ofy.get(k);
    }

}
