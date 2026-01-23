package gov.audit;

/**
 *
 * @author felix
 */
public class BankBean implements java.io.Serializable {

    private static final long serialVersionUID = -3828832432176014458L;
    
    private String WorkerID, BankAcct;
    
    private final boolean NUMERIC = true; //, CONDITION = true;
    
    private final java.util.ArrayList<javax.faces.model.SelectItem> arWorker = new java.util.ArrayList<>();

    public java.util.List<javax.faces.model.SelectItem> getWorkers() {return arWorker;}

    public String getWorkerID() {return WorkerID;}
    public void setWorkerID(String value) {WorkerID = value;}

    public String getBankAcct() {return BankAcct;}
    public void setBankAcct(String value) {BankAcct = value;}

    @javax.annotation.PostConstruct
    protected void init() {
        javax.faces.application.FacesMessage msg = null;
        String  sqlCmd = "SELECT " +
                        "uniqkey, " +
                        "humane(lastname, firstname, suffix, midname) " +
                    "FROM " +
                        "psnl.jobworker " +
                    "WHERE " +
                        "(bank_acct IS NULL) " +
                    "ORDER BY " +
                        "lastname, firstname, midname";
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(sqlCmd);
                java.sql.ResultSet rst = psmt.executeQuery()) {
            while (rst.next())
                arWorker.add(new javax.faces.model.SelectItem(rst.getString(1), rst.getString(2)));
            
        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void updateJOs(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
        //tring buttonId = event.getComponent().getClientId();
        //System.out.println(buttonId);

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("psnl.jobworker");
            saver.FieldName("uniqkey",   !NUMERIC, gov.enums.Take.ConditionOnly, WorkerID);
            saver.FieldName("bank_acct", !NUMERIC, gov.enums.Take.UpdateOnly, BankAcct);
            _smt.executeUpdate(saver.Perform(gov.enums.Fire.doUpdate));

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Updated successfully");

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}