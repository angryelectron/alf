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
import java.util.ArrayList;

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
    
    public Long insert(Lift lift) {
        Key<Lift> k = ofy.put(lift);
        return k.getId();
    }

    public void update(Lift lift) {
        ofy.put(lift);
    }
    
    public void delete(Lift lift) {
        ofy.delete(lift);
    }

    public Lift find(Long id) {
        return (Lift) ofy.get(Lift.class, id);
    }

    ArrayList<Lift> findByKeyList(ArrayList<Long> liftKeys) {
        ArrayList<Key<Lift>> keys = new ArrayList<Key<Lift>>();

        //turn Longs into Keys
        for (Long l : liftKeys) {
            keys.add(new Key<Lift>(Lift.class, l));
        }

        return (ArrayList)ofy.get(keys).values();
    }

}
