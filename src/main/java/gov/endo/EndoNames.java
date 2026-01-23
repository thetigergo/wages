package gov.endo;

/**
 *
 * @author felix
 */
public class EndoNames {
    
    private final String WorkerPK, EmpID, LastName, FirstName, MidName, Address, Ranggo, BankAcct;
    private final Double PayRate;

    public EndoNames(String workerid, String empid, String surname, String ngalan, String pantonga, String address, String ranggo, Double payrate, String accntno) {
        WorkerPK = workerid; EmpID = empid; LastName = surname; FirstName = ngalan; MidName = pantonga; Address = address; Ranggo = ranggo; PayRate = payrate; BankAcct = accntno;
    }

    public String getWorkerPK() {return WorkerPK;}
    
    public String getEmpID()   {return EmpID;}

    public String getLastName() {return LastName;}

    public String getFirstName() {return FirstName;}

    public String getMidName() {return MidName;}

    public String getAddress() {return Address;}

    public String getRanggo() {return Ranggo;}

    public String getBankAcct() {return BankAcct;}

    public Double getPayRate() {return PayRate;}

}
