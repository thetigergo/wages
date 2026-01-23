package gov.admin;

/**
 *
 * @author felix
 */
public class EraseUserBean implements java.io.Serializable {

    private static final long serialVersionUID = -7283891416272038261L;
    
    private String mUserID, mUserName, mOpesina;
    
    private final boolean NUMERIC = true; //, CONDITION = true;

    private final java.util.ArrayList<javax.faces.model.SelectItem> arUsers = new java.util.ArrayList<>();
    
//    private wages.OnlineUser onlineUser;
//    public void setOnlineBean(wages.OnlineUser activeUser) {onlineUser = activeUser;}

    public java.util.List<javax.faces.model.SelectItem> getUsers() {return arUsers;}

    /*public EraseUserBean() {
        dbase.PgSQLConn jdbc = null;
        try {
            jdbc = new dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement();
            java.sql.ResultSet rst = smt.executeQuery(
                    "SELECT " +
                        "offcid, " +
                        "office " +
                    "FROM " +
                        "jos.offices " +
                    "ORDER BY " +
                        "office");
            while (rst.next())
                arOffice.add(new javax.faces.model.SelectItem(rst.getString(1), rst.getString(2)));
            rst.close();
            
            
        } catch (Exception ex) {
            javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage()));
        } finally {
            if (jdbc != null) try {
                jdbc.close();
            } catch (Exception exp) {
                javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", exp.getMessage()));
            }
        }
    }*/

    @javax.annotation.PostConstruct
    protected void init() {
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement();
                java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        "userid, " +
                        "userdescrp, " +
                        "opesina " +
                    "FROM " +
                        "public.userlogon " +
                    "WHERE " +
                        "(userlogon.phaseoff = FALSE) AND " +
                        "(NOT userlogon.opesina IS NULL)")) {
            while (rst.next())
                arUsers.add(new javax.faces.model.SelectItem(rst.getString(1), rst.getString(2)));

            
        } catch (java.sql.SQLException ex) {
            javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage()));
        } finally {

        }
    }


    public String getUserID() {return mUserID;}
    public void setUserID(String value) {mUserID = value;}

    public String getUserName() {return mUserName;}
    public void setUserName(String value) {mUserName = value;}

    public String getOpesina() {return mOpesina;}
    public void setOpesina(String value) {mOpesina = value;}
    
    public void onUserChange() {
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " + //DISTINCT
                        "userlogon.userid, " +
                        "userlogon.userdescrp, " +
                        "offices.office " +
                    "FROM " +
                        "public.userlogon JOIN jos.offices " +
                        "ON userlogon.opesina = offices.offcid " +
                    "WHERE " +
                        "(userlogon.phaseoff = FALSE) AND " +
                        "(userlogon.userid = '" + mUserID + "') " +
                    "ORDER BY " +
                        "userlogon.userdescrp")) {
            if (rst.next()) {
                mUserName = rst.getString(2);
                mOpesina  = rst.getString(3);
            }
            
            
        } catch (java.sql.SQLException ex) {
            javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage()));
        } finally {

        }
    }
    
    public void eraseUser(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
        //String buttonId = event.getComponent().getClientId();
        //System.out.println(buttonId);

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement()) {

            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("public.userlogon");
            saver.FieldName("userid",     !NUMERIC, gov.enums.Take.ConditionOnly, mUserID);
            _smt.executeUpdate(saver.Perform(gov.enums.Fire.doDelete));

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Removed successfully");

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}  

