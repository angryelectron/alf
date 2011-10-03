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
        ObjectifyService.register(WhistlerResort.class);
    }

    private Objectify ofy;

    ResortDataAccess() {
        ofy = ObjectifyService.begin();
    }

    
    public void create(Resort resort) {
        ofy.put(resort);
    }

    public void update(Resort resort) {
        ofy.put(resort);
    }
    
    public void delete(Long id) {
        ofy.delete(Resort.class, id);
    }

    public Resort find(Resort resort) {
        return ofy.get(new Key<Resort>(Resort.class, resort.id));
    }

    public Resort findByDate(String name, Date date) {
        Query<Resort> q = ofy.query(Resort.class).filter("name=", name).filter("date=", date);
        return q.get();
    }

}
