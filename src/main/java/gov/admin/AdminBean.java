package gov.admin;

/**
 *
 * @author felix
 */
public class AdminBean implements java.io.Serializable {

    private static final long serialVersionUID = 387386861481468098L;
    
    private String mUserID, mUserName, mOpesina;

    private final java.util.ArrayList<ManageUser> arUsers = new java.util.ArrayList<>();


    @javax.annotation.PostConstruct
    protected void init() {
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
                        "(NOT userlogon.opesina IS NULL) " +
                    "ORDER BY " +
                        "userlogon.userdescrp")) {
            arUsers.clear();
            while (rst.next())
                arUsers.add(new ManageUser(rst.getString(1), rst.getString(2), rst.getString(3)));
            

        } catch (Exception ex) {
            javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage()));
        } finally {

        }
    }

//    public AdminBean() {
//
//    }

    public java.util.ArrayList<ManageUser> getSelected() {return arUsers;}

    public String getUserID() {return mUserID;}
    public void setUserID(String value) {mUserID = value;}

    public String getUserName() {return mUserName;}
    public void setUserName(String value) {mUserName = value;}

    public String getOpesina() {return mOpesina;}
    public void setOpesina(String value) {mOpesina = value;}

}
