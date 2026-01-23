package gov.wages;

/**
 *
 * @author felix
 */
public class LoginBean implements java.io.Serializable {

    private static final long serialVersionUID = 5462080788417868321L;

    private String mUserID, mGatePass;
    private Short  Pilion = 0;

    private OnlineUser onlineUser;
    public void setOnlineBean(OnlineUser activeUser) {onlineUser = activeUser;}

    public void setUserID(String value) {mUserID = value;}
    public String getUserID() {return mUserID;}

    public void setGatePass(String value) {mGatePass = value;}
    public String getGatePass() {return mGatePass;}

    public Short getPilion() {return Pilion;}
    public void setPilion(Short value) {Pilion = value;}

    @javax.annotation.PostConstruct
    public void init() {
        
    }

    public String reDirectURL() {
        javax.faces.application.FacesMessage msg = null;
        String retval = null;
        
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(
                        "SELECT " +
                            "accessctrl, " +
                            "opesina, " +
                            "sec_level, " +
                            "NOW() " +
                        "FROM " +
                            "userlogon " +
                        "WHERE " +
                            "(userid = ?) AND " +
                            "(passwrd = MD5(?))")) {
            psmt.setString(1, mUserID);
            psmt.setString(2, mGatePass);
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                if (rst.next()) {
                    if (rst.getString(3).contains(String.valueOf(Pilion))) {
                        switch (Pilion) {
                            case 0:
                                retval = "/pays/pay_central.xhtml";
                                break;

                            case 1:
                                retval = "/acctg/acctg_0central.xhtml";
                                break;

                            case 2:
                            case 32767:
                                retval = "/admin/admin_0center.xhtml";
                                break;

                            case 3:
                                retval = "/endo/endo_centre.xhtml";
                                break;

                            case 4:
                                retval = "/bhw/bhw_centre.xhtml";
                                break;

                            case 5:
                                retval = "/dcw/endo_centre.xhtml";
                                break;
                                
                            case 7:
                                retval = "/sch/sch_centre.xhtml";
                                break;
                                
                            default:
                                retval = "";
                        }

                        onlineUser.setUserOnline(mUserID);
                        //onlineUser.setCurrentPage("content.xhtml");
                        onlineUser.setUserLevel(rst.getShort(1));
                        onlineUser.setOpesina(rst.getString(2));
                        onlineUser.setMgaPetsa(rst.getDate(4));

                        psmt.executeUpdate("UPDATE userlogon SET lastinn = NOW() WHERE (userid = '" + mUserID + "')");

                    } else {
                        msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARNING", "INVALID PASSWORD");
                        retval = null;
                    }

                } else {
                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARNING", "INVALID PASSWORD");
                    retval = null;
                }
            }


            javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest)javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String local = request.getRemoteAddr(); 
            java.net.InetAddress inetadds = java.net.InetAddress.getByName(local);
            local = inetadds.getHostName();
            int taas = local.length(); taas = (taas > 15 ? 15 : taas);
            onlineUser.setClientIp(local.substring(0, taas));
            //javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("theUser", mUserID);
            javax.faces.application.ResourceHandler rh = javax.faces.context.FacesContext.getCurrentInstance().getApplication().getResourceHandler();
            onlineUser.setCSS(rh.createResource("primes.css", "pfo").getRequestPath());
            onlineUser.setRouting(request.getContextPath());

        } catch (Exception fex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", fex.getMessage());
        } finally {

        }
        if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        
        return retval;
    }
}
