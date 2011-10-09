/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;

import org.joda.time.LocalDate;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

/**
 *
 * @author abythell
 */
public class ResortDataAccess <T> {

    static {
        ObjectifyService.register(WhistlerResort.class);
    }

    private Objectify ofy;
    private Class<T> type;

    ResortDataAccess(Class<T> type) {
        ofy = ObjectifyService.begin();
        this.type = type;
    }

    
    public void create(T resort) {
        ofy.put(resort);
        Resort r = (Resort)resort;
        //System.out.println("Creating: " + r.id);
    }

    public void update(T resort) {
        ofy.put(resort);
        Resort r = (Resort)resort;
        //System.out.println("Updating: " + r.id);
    }
    
    public void delete(Long id) {
        ofy.delete(type, id);
    }

    public T find(Long id) {
        return ofy.get(new Key<T>(type, id));
    }

    T findByDate(LocalDate date) {        
        Query<T> q = ofy.query(type).filter("date >=", date.toDate()).filter("date <", date.plusDays(1).toDate());
        return q.get();
    }

}
