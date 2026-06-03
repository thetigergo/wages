package gov.wages;

/**
 *
 * @author felix
 */
public class LoginBean implements java.io.Serializable {

    private static final long serialVersionUID = 5462080788417868321L;

    private String mUserID, mGatePass;
    private Short  Pilion = 0;

    private gov.dbase.PgDBbind pgDBlink;
    private OnlineUser onlineUser;
    public void setOnlineBean(OnlineUser activeUser) {onlineUser = activeUser;}
    public void setPgDBlink(gov.dbase.PgDBbind value) {pgDBlink = value;}

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
        
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
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
            boolean isSuccess = false;
            psmt.setString(1, mUserID);
            psmt.setString(2, mGatePass);
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                if (rst.next()) {
                    if (rst.getString(3).contains(String.valueOf(Pilion))) {
                        retval = switch (Pilion) {
                            case 0 -> "/pays/pay_central.xhtml";
                            case 1 -> "/acctg/acctg_0central.xhtml";
                            case 2, 32767 -> "/admin/admin_0center.xhtml";
                            case 3 -> "/endo/endo_centre.xhtml";
                            case 4 -> "/bhw/bhw_centre.xhtml";
                            case 5 -> "/dcw/endo_centre.xhtml";
                            case 7 -> "/sch/sch_centre.xhtml";
                            default -> "";
                        };

                        onlineUser.setUserOnline(mUserID);
                        //onlineUser.setCurrentPage("content.xhtml");
                        onlineUser.setUserLevel(rst.getShort(1));
                        onlineUser.setOpesina(rst.getString(2));
                        onlineUser.setMgaPetsa(rst.getDate(4));

                        //psmt.executeUpdate("UPDATE userlogon SET lastinn = NOW() WHERE (userid = '" + mUserID + "')");
                        isSuccess = true;
                        

                    } else {
                        msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARNING", "INVALID PASSWORD");
                        retval = null;
                    }

                } else {
                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARNING", "INVALID PASSWORD");
                    retval = null;
                }
            }
            if (isSuccess) {
                try (java.sql.PreparedStatement pzmt = jdbc.prepareStatement("UPDATE userlogon SET lastinn = NOW() WHERE (userid = ?)")) {
                    pzmt.setString(1, mUserID);
                    pzmt.executeUpdate();
                }
            }


            javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest)javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequest();
            
            // Get the raw client IP
            //String clientIp = request.getRemoteAddr();
            
            // Prefer X-Forwarded-For if behind proxy
            String clientIp = request.getHeader("X-Forwarded-For");
            if (clientIp == null || clientIp.isEmpty()) clientIp = request.getRemoteAddr();            
            
            // If you want the hostname (reverse DNS lookup)
            String hostName;
            try {
                java.net.InetAddress inetAddr = java.net.InetAddress.getByName(clientIp);
                hostName = inetAddr.getHostName();
            } catch (java.net.UnknownHostException uhe) {
                hostName = clientIp; // fallback to IP if lookup fails
            }

            // Truncate to max 15 chars
            if (hostName.length() > 15) hostName = hostName.substring(0, 15);

            // Save into your session bean
            onlineUser.setClientIp(hostName);            
            
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
