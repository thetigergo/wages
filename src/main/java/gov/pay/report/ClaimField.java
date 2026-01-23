package gov.pay.report;

/**
 *
 * @author felix
 */
public class ClaimField implements java.io.Serializable {

    private static final long serialVersionUID = -7191942692705237776L;

    private final Short Counter;
    private final Float Days;
    private final java.util.Date DateFr, DateTo;

    public ClaimField(
            /*0*/Short counts,
            /*2*/java.util.Date from,
            /*3*/java.util.Date tooo,
            /*4*/Float days) {
        DateFr    = from;
        DateTo    = tooo;
        Days      = days;
        Counter   = counts;
    }


    public Short getCounter() {return Counter;}

    public Float getDays() {return Days;}

    public java.util.Date getDateFr() {return DateFr;}

    public java.util.Date getDateTo() {return DateTo;}

}