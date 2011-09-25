/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A Resort Subclass that populates the Lifts object with WB-specific lift info
 */
public final class WhistlerResort extends Resort {

    private String url = "http://www.whistlerblackcomb.com/weather/lift/status.htm";

    public WhistlerResort() {
        this.name = "WhistlerBlackcomb";
    }

    @Override
    public void scrape() {
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
                        Lift lift = new Lift();
                        for (Element column : columns) {
                            if (column.getElementsByTag("div").isEmpty()) {
                                lift.setName(column.text());
                                //TODO: fix this
                                //lift.id = db.getLiftIdByName(lift.name, this.id);
                            }
                            else {
                                if (column.text().equals("OPEN")) {lift.setStatus(Lift.LiftStatus.OPEN);}
                                else if (column.text().equals("CLOSED")) {lift.setStatus(Lift.LiftStatus.CLOSED);}
                                else if (column.text().equals("STANDBY")) {lift.setStatus(Lift.LiftStatus.STANDBY);}
                            }
                        }
                        this.addLift(lift);
                    }
                }
            }
        }
        catch (Exception e) {
            System.err.println("Scrape: " + e.getMessage());
        }
    }
}
