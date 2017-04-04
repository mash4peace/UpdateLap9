package Mohamed.company;

import java.util.Date;

/**
 * Created by mash4 on 3/31/2017.
 */
public class ResolvedTicket extends TicketConstr {
    private static  int ticketIdCounter = 1;
    public void setTicketID(int ticketID) {
        TicketID = ticketID;

    }
    private String res;

    private int TicketID;

    public String getRes() {
        return res;
    }


    public ResolvedTicket(String desc, String rep,String p, String resol, Date date) {
        super(desc, rep, p, date);
        this.res = resol;
        this.ticketID = ticketIdCounter;
    }

    @Override
    public String toString() {
        return ("ID : " + this.ticketID + ",   Issue : "+ this.description +
                ",    Priority : " + this.priority +  ",  Reported by :  "+ this.reporter +
                ",     Resolution for :     "+ res+ ",      Reported on    " +
                this.date);
    }
}
