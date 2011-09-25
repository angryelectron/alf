/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;

/**
 *
 * @author abythell
 */
public class Lift {

    public enum LiftStatus {CLOSED, STANDBY, OPEN};
    private String name;
    private LiftStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LiftStatus getStatus() {
        return status;
    }

    public void setStatus(LiftStatus status) {
        this.status = status;
    }



    /**
     * Lift status has a precedence:  Open beats StandBy beats Closed
     * Compare two lifts and save the status with the highest precedence.
     * @param lift
     */
    public void compareAndUpdate(Lift l) {
        if ((this.status == LiftStatus.CLOSED) && (l.status != LiftStatus.CLOSED)) {
            this.status = l.status;
        }
        else if ((this.status == LiftStatus.STANDBY) && (l.status == LiftStatus.OPEN)) {
            this.status = l.status;
        }
    }
    
}
