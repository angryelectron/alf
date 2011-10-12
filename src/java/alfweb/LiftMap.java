/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alfweb;

import alfd.Lift;
import alfd.Resort;
import alfd.WhistlerResort;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.joda.time.DateMidnight;

/**
 *
 * @author abythell
 */
public class LiftMap {

    //HashMap<Date, HashMap<liftName, status>>
    HashMap<String, HashMap<String, String>> liftMap = new HashMap<String, HashMap<String, String>>();
    HashMap<Integer, String>nameMap = new HashMap<Integer, String>();


    public LiftMap (String date, Integer days) {
        for (int i = 0; i < days; i++) {
            HashMap<String, String> statusMap = new HashMap<String, String>();
            Resort resort = new WhistlerResort();
            DateMidnight startDate = resort.getLocalDate();
            resort.load(startDate.minusDays(i));
            for (Lift l : resort.getLifts()) {
                statusMap.put(l.getName(), l.getStatus().toString());
            }
            this.put(startDate.minusDays(i).toString(), statusMap);
        }
    }

    final public void put(String date, HashMap<String, String> liftstatus) {
        if (!liftMap.containsKey(date)) {
            liftMap.put(date, liftstatus);

        }
        else {
            HashMap<String, String> statusMap = liftMap.get(date);
            statusMap.putAll(liftstatus);
        }
        this.putLiftNames(liftstatus);
    }
    
    final public String get(String liftname, String date) {
        if (!liftMap.containsKey(date)) {
            return "Not Found";
        }
        HashMap<String, String> statusMap = liftMap.get(date);
        return statusMap.get(liftname);
    }

    private void putLiftNames(HashMap<String, String> liftstatus) {
        Set names = liftstatus.keySet();
        Iterator it = names.iterator();
        while (it.hasNext()) {
            String name = it.next().toString();
            if (!nameMap.containsValue(name)) {
                int key = nameMap.size();
                nameMap.put(key, name);
            }
        }
    }

    public String getLiftNameByIndex(Integer index) {
        return nameMap.get(index);
    }

    public Integer getLiftCount() {
        return nameMap.size();
    }

}
