package gov.audit;

/**
 *
 * @author felix
 */
public class ForATMList implements java.io.Serializable {

    private static final long serialVersionUID = -5972877158466363146L;

    private final String  mCtrlNo, mgaEtAl, mAcctgNo;
    //private Boolean mNapili;
    private final Double  nNetPay;
    private final Short   Ehap;

    public ForATMList(/*Boolean napili,*/
                      String acctgno,
                      String etal,
                      String ctrlno,
                      Short ihap,
                      Double netpay) {
        //mNapili  = napili;
        mAcctgNo = acctgno;
        mgaEtAl  = etal;
        mCtrlNo  = ctrlno;
        Ehap     = ihap;
        nNetPay  = netpay;
    }
    
//    public Boolean getNapili() {return mNapili;}
//    public void setNapili(Boolean value) {mNapili = value;}

    public String getAcctgNo() {return mAcctgNo;}
    public String getMgaEtAl() {return mgaEtAl;}
    public String getCtrlNo() {return mCtrlNo;}
    public Short  getEhap() {return Ehap;}
    public Double getNetPay() {return nNetPay;}
    
}
