package gov.audit;

/**
 *
 * @author felix
 */
public class ReprintBean implements java.io.Serializable {

    private static final long serialVersionUID = 7443060770557005210L;
    
    private final java.util.List<javax.faces.model.SelectItem> arRefNos = new java.util.ArrayList<>();
    
    private java.util.Date DateATM;
    private java.lang.String RefNo;
    private java.lang.Short  WhichOf;
    

    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}

    public java.util.Date getDateATM() {return DateATM;}
    public void setDateATM(java.util.Date value) {DateATM = value;}

    public String getRefNo() {return (RefNo == null ? onlineUser.getTemporary() : RefNo);}
    public void setRefNo(String value) {RefNo = value;}

    public Short getWhichOf() {return WhichOf;}
    public void setWhichOf(Short value) {this.WhichOf = value;}
    
    
    
    public java.util.List<javax.faces.model.SelectItem> getRefNos() {return arRefNos;}
    
    
    
    
    @javax.annotation.PostConstruct
    protected void init() {
        javax.faces.application.FacesMessage msg = null;
        String sqlComm = 
                "SELECT " +
                    "refkey " +
                "FROM " +
                    "pay.advicepay " +
                "WHERE " +
                    "(refdate >= (NOW()::DATE - 92)) " +
                "ORDER BY " +
                    "refdate";
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(sqlComm);
                java.sql.ResultSet rst = psmt.executeQuery()) {
            arRefNos.clear();
            while (rst.next())
                arRefNos.add(new javax.faces.model.SelectItem(rst.getString(1)));

            RefNo   = onlineUser.getTemporary();
            DateATM = onlineUser.getMgaPetsa();
            WhichOf = onlineUser.getWhichOf();

        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onChanging() {
        onlineUser.setTemporary(RefNo);
        javax.faces.application.FacesMessage msg = null;
        String sqlComm = 
                "SELECT " +
                    "refdate " +
                "FROM " +
                    "pay.advicepay " +
                "WHERE " +
                    "(refkey = ?)";
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(sqlComm)) {
            
            psmt.setString(1, RefNo);
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                if (rst.next()) onlineUser.setMgaPetsa(rst.getDate(1));
            }

        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onWhichOf() {onlineUser.setWhichOf(WhichOf);}
}