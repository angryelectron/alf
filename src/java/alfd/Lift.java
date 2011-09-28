/*
 * Alpine Lift Forcast Daemon
 * (C)2011 Andrew Bythell <abythell@ieee.org>
 *
 * $Id$
 *
 */

package alfd;

import javax.persistence.Id;

/**
 *
 * @author abythell
 */
public class Lift {

    public enum LiftStatus {CLOSED, STANDBY, OPEN};
    @Id Long Id;
    private String Name;
    private LiftStatus Status;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public LiftStatus getStatus() {
        return Status;
    }

    public void setStatus(LiftStatus status) {
        this.Status = status;
    }

    /**
     * Lift status has a precedence:  Open beats StandBy beats Closed
     * Compare two lifts and save the status with the highest precedence.
     * @param lift
     */
    public void compareAndUpdate(Lift l) {
        if ((this.Status == LiftStatus.CLOSED) && (l.Status != LiftStatus.CLOSED)) {
            this.Status = l.Status;
        }
        else if ((this.Status == LiftStatus.STANDBY) && (l.Status == LiftStatus.OPEN)) {
            this.Status = l.Status;
        }
    }
    
}
