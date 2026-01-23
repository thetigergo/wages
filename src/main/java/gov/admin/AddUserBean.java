package gov.admin;

/**
 *
 * @author felix
 */
public class AddUserBean implements java.io.Serializable {

    private static final long serialVersionUID = -4976537887220973032L;
    
    private String mUserID, mUserName, mOpesina, mPassword;
    private Short[]  Pilion;
    
    private final boolean NUMERIC = true; //, CONDITION = true;

//    private java.util.ArrayList<ManageUser> arUsers = new java.util.ArrayList<ManageUser>();
    private final java.util.ArrayList<javax.faces.model.SelectItem> arOffice = new java.util.ArrayList<>();
    

    public java.util.List<javax.faces.model.SelectItem> getOffices() {return arOffice;}

    @javax.annotation.PostConstruct
    protected void init() {
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement();
                java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        "offcid, " +
                        "office " +
                    "FROM " +
                        "jos.offices " +
                    "ORDER BY " +
                        "office")) {
            while (rst.next())
                arOffice.add(new javax.faces.model.SelectItem(rst.getString(1), rst.getString(2)));
            
            
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

    public String getPassword() {return mPassword;}
    public void setPassword(String value) {mPassword = value;}
    
    public Short[] getPilion() {return Pilion;}
    public void setPilion(Short[] value) {Pilion = value;}
    
    public void appendUser(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
        //String buttonId = event.getComponent().getClientId();
        //System.out.println(buttonId);

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement()) {
            
            StringBuilder level = new StringBuilder();
            for (short abc = 0; abc < Pilion.length; abc++) level.append(Pilion[abc]);

            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("public.userlogon");
            saver.FieldName("userid",     !NUMERIC, gov.enums.Take.InsertOnly, mUserID);
            saver.FieldName("passwrd",     NUMERIC, gov.enums.Take.InsertOnly, "MD5('" + mPassword + "')");
            saver.FieldName("userdescrp", !NUMERIC, gov.enums.Take.InsertOnly, mUserName);
            saver.FieldName("opesina",    !NUMERIC, gov.enums.Take.InsertOnly, mOpesina);
            saver.FieldName("sec_level",  !NUMERIC, gov.enums.Take.InsertOnly, level.toString());
            _smt.executeUpdate(saver.Perform(gov.enums.Fire.doInsert));

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Added successfully");


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}  
