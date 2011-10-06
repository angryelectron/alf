/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;

import org.joda.time.LocalDate;
import com.googlecode.objectify.annotation.Subclass;
import javax.persistence.Transient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A Resort Subclass that populates the Lifts object with WB-specific lift info
 */
@Subclass public final class WhistlerResort extends Resort implements ResortDAO {

    @Transient private String url = "http://www.whistlerblackcomb.com/weather/lift/status.htm";
    @Transient private ResortDataAccess<WhistlerResort> dao = new ResortDataAccess<WhistlerResort>(WhistlerResort.class);

    public WhistlerResort() {
        this.name = "WhistlerBlackcomb";
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
        dao.update(this);
    }

    /**
     * Populate Lift data from the database
     * If no data has been saved, call Scrape() instead
     */
    public void load(LocalDate date) {
        assert(date != null);

        //load resort info
        Resort r = dao.findByDate(date);
        if (r == null) {
            //no entry exists for this resort/date combo.  fetch new data for
            //today from the Resort's datasource
            this.setDate(new LocalDate());
            this.scrape();
        }
        else {
            //load resort and lift info from the datastore
            this.id = r.id;
            this.liftKeys = r.liftKeys;
            this.setDate(r.getDate());
            LiftDataAccess lda = new LiftDataAccess();
            lift = lda.findByKeyList(liftKeys);
        }
    }

    private void scrape() {
        try {
            //File input = new File("/Users/abythell/Development/alf/lift-status-mixed.htm");
            //Document doc = Jsoup.parse(input, "UTF-8", url);
            Document doc = Jsoup.connect(url).get();

            Elements tables = doc.getElementsByClass("groomtable");
            for (Element table : tables) {
                Elements rows = table.getElementsByTag("tr");
                for (Element row : rows) {
                    Elements columns = row.getElementsByTag("td");
                    if (!columns.isEmpty()) {
                        Lift newLift = new Lift();
                        for (Element column : columns) {
                            if (column.getElementsByTag("div").isEmpty()) {
                                newLift.setName(column.text());
                                //TODO: fix this
                                //lift.id = db.getLiftIdByName(lift.name, this.id);
                            }
                            else {
                                if (column.text().equals("OPEN")) {newLift.setStatus(Lift.LiftStatus.OPEN);}
                                else if (column.text().equals("CLOSED")) {newLift.setStatus(Lift.LiftStatus.CLOSED);}
                                else if (column.text().equals("STANDBY")) {newLift.setStatus(Lift.LiftStatus.STANDBY);}
                            }
                        }
                        this.addLift(newLift);
                    }
                }
            }
        }
        catch (Exception e) {
            System.err.println("Scrape: " + e.getMessage());
        }
    }
}
