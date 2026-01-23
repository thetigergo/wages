package gov.pay;

/**
 *
 * @author felix
 */
public class WageField implements java.io.Serializable {

    private static final long serialVersionUID = 5244314458043429397L;

    private final String Worker, Jobs, WorkID;
    private final Short Undertime, Counter;
    private final Float Days, Rate;
    private final Double Gross, Deduction, Bunos, PagIbig, SSSPrem, TaxHeld;
    private final java.util.Date DateFr, DateTo;

    public WageField(
            /* 1*/String workid,
            /* 2*/String worker,
            /* 3*/String jobs,
            /* 4*/java.util.Date from,
            /* 5*/java.util.Date tooo,
            /* 6*/Float days,
            /* 7*/Float rate,
            /* 8*/Double gross,
            /* 9*/Short utime,
            /*10*/Double lessall,
            /*11*/Double pagibig,
            /*12*/Double sssprem,
            /*13*/Short counts,
            /*14*/Double bunos,
            /*15*/Double wtax) {
        WorkID    = workid;
        Worker    = worker;
        Jobs      = jobs;
        DateFr    = from;
        DateTo    = tooo;
        Days      = days;
        Rate      = rate;
        Gross     = gross;
        Undertime = utime;
        Deduction = lessall;
        PagIbig   = pagibig;
        Counter   = counts;
        Bunos     = bunos;
        SSSPrem   = sssprem;
        TaxHeld   = wtax;
    }

    public Short getCounter() {return Counter;}

    public String getWorkID() {return WorkID;}

    public String getWorker() {return Worker;}

    public String getJobs() {return Jobs;}

    public Float getDays() {return Days;}

    public Short getUndertime() {return Undertime;}

    public Float getRate() {return Rate;}

    public Double getGross() {
        /*Gross = Double.valueOf(Days * Rate);*/
        return Gross;
    }

    public Double getDeduction() {
        /*long subtrahend = Math.round((Rate / 480 * Undertime) * 100D);
        Deduction = subtrahend * 0.01D;*/
        return Deduction;
    }

    public Double getPagIbig() {return PagIbig;}

    //public Double getNetAmount() {return getGross() - getDeduction() + getBunos() - getPagIbig();}
    public Double getNetAmount() {return Gross + Bunos - Deduction - PagIbig - SSSPrem - TaxHeld;}

    public java.util.Date getDateFr() {return DateFr;}

    public java.util.Date getDateTo() {return DateTo;}

    public Double getBunos() {return Bunos;}

    public Double getSSSPrem() {return SSSPrem;}

    public Double getTaxHeld() {return TaxHeld;}
    
    /**
     * Used by Contractual 004031
     * @return 
     */
    public Double getUtDeduct() {return Math.round((Rate / 10560D * ((Days * 480F) + Undertime)) * 100D) * 0.01D;}

}

