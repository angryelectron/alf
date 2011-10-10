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
import java.util.ArrayList;
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
    //@Transient private String url = "http://localhost:8080/status.htm";
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
        ArrayList<Long>newKeys = new ArrayList<Long>();
        for (Lift l : lift) {
            newKeys.add(lda.insert(l));
        }

        //write resort information to datastore
        liftKeys = newKeys;
        this.setDate(new LocalDate());
        dao.update(this);
    }

    /**
     * Populate Lift data from the database
     * If no data has been saved, call Scrape() instead
     */

    public void fetch() {
        this.setDate(new LocalDate());
        this.scrape();
    }

    public void load(LocalDate date) {
        Resort r = dao.findByDate(date);
        if (r == null) {
            this.status = ResortStatus.NODATA;
            return;
        }
        this.id = r.id;
        this.liftKeys = r.liftKeys;
        this.setDate(r.getDate());
        LiftDataAccess lda = new LiftDataAccess();
        this.lift = lda.findByKeyList(liftKeys);
        this.status = ResortStatus.OK;
        //System.out.println("Loaded: " + this.id);
    }

    public void scrape() {
        ResortStatus scrapeStatus = ResortStatus.OK;
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
            scrapeStatus = ResortStatus.OFFLINE;
        }
        setStatus(scrapeStatus);
    }
}
