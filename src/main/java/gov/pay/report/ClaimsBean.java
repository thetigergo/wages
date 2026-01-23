package gov.pay.report;

/**
 *
 * @author felix
 */
public class ClaimsBean implements java.io.Serializable {

    private static final long serialVersionUID = -6913858753250673254L;
    
    private final java.util.List<gov.pay.report.ClaimField>        arFields = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arWorker = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arJONum  = new java.util.ArrayList<>();
    
    
    
    private String EmpID, JONumber;
    private Short Counter = 0;
    private Float TotalDays = 0F;

    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}

    
    public Float getTotalDays() {return TotalDays;}
    
    public String getEmpID() {return EmpID;}
    public void setEmpID(String value) {EmpID = value;}

    public String getJONumber() {return JONumber;}
    public void setJONumber(String value) {JONumber = value;}



    
    public java.util.List<gov.pay.report.ClaimField> getClaims() {return arFields;}
    public java.util.List<javax.faces.model.SelectItem> getWorkers() {return arWorker;}
    public java.util.List<javax.faces.model.SelectItem> getJONoList() {return arJONum;}
    
    
    @javax.annotation.PostConstruct
    protected void init() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT DISTINCT " +
                        "timebook.worker, " +
                        "humane(jobworker.lastname, jobworker.firstname, jobworker.suffix, jobworker.midname) " +
                    "FROM " +
                        "pay.timebook INNER JOIN psnl.jobworker " +
                        "ON timebook.worker = jobworker.uniqkey " +
                    "WHERE " +
                        "(timebook.opesina = '" + onlineUser.getOpesina() + "') AND " +
                        "(DATE_PART('YEAR', dateto) = DATE_PART('YEAR', NOW())) " +
                    "ORDER BY " +
                        "jobworker.lastname, jobworker.firstname, jobworker.midname")) {
            while (rst.next())
                arWorker.add(new javax.faces.model.SelectItem(rst.getString(1), rst.getString(2)));

            
        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onNameChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT DISTINCT " +
                        "jobordno " +
                    "FROM " +
                        "pay.timebook " +
                    "WHERE " +
                        "(opesina = '" + onlineUser.getOpesina() + "') AND " +
                        "(worker = '" + EmpID + "') AND " +
                        "(DATE_PART('YEAR', dateto) = DATE_PART('YEAR', NOW())) " +
                    "ORDER BY " +
                        "jobordno")) {
            arJONum.clear();
            while (rst.next())
                arJONum.add(new javax.faces.model.SelectItem(rst.getString(1)));

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onJOChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                    /* 2*/"datefr, " +
                    /* 3*/"dateto, " +
                    /* 4*/"days " +
                    "FROM " +
                        "pay.timebook " +
                    "WHERE " +
                        "(opesina = '" + onlineUser.getOpesina() + "') AND " +
                        "(worker = '" + EmpID + "') AND " +
                        "(jobordno = '" + JONumber + "') " +
                    "ORDER BY " +
                        "datefr")) {
            TotalDays = 0F; arFields.clear(); Counter = 0;
            while (rst.next()) {
                Counter++;
                arFields.add(new gov.pay.report.ClaimField(Counter, rst.getDate(1), rst.getDate(2), rst.getFloat(3)));
                TotalDays += rst.getFloat(3);
            }


        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
