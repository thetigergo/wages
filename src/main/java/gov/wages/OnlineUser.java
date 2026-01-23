package gov.wages;

/**
 *
 * @author felix
 */
//@javax.enterprise.context.Dependent
public class OnlineUser implements java.io.Serializable {

    private static final long serialVersionUID = -2257676679464365694L;

    private String
            userOnline,
            mCSS,
            Opesina,
            Temporary,
            ipClient,
            routing;
    private Short  userLevel, WhichOf;
    private java.util.Date MgaPetsa;

    public String getUserOnline() {return userOnline;}
    protected void setUserOnline(String value) {userOnline = value;}

    public Short getUserLevel() {return userLevel;}
    protected void setUserLevel(Short value) {userLevel = value;}
 
    protected void setCSS(String value) {mCSS = value;}
    public String getCSS() {return mCSS;}

    public String getOpesina() {return Opesina;}
    protected void setOpesina(String value) {Opesina = value;}

    public String getTemporary() {return Temporary;}
    public void setTemporary(String value) {Temporary = value;}

    public String getClientIp() {return ipClient;}
    protected void setClientIp(String value) {ipClient = value;}

    public String getRouting() {return routing;}
    protected void setRouting(String value) {routing = value;}

    public java.util.Date getMgaPetsa() {return MgaPetsa;}
    public void setMgaPetsa(java.util.Date value) {MgaPetsa = value;}

//    public void checkPermissions(javax.faces.event.ComponentSystemEvent event){
//	javax.faces.context.FacesContext fc = javax.faces.context.FacesContext.getCurrentInstance();
//	if (!"admin".equals(fc.getExternalContext().getSessionMap().get("role"))) {
//            javax.faces.application.ConfigurableNavigationHandler nav = (javax.faces.application.ConfigurableNavigationHandler)fc.getApplication().getNavigationHandler();
//            nav.performNavigation("access-denied");
//	}		
//    }

    public Short getWhichOf() {return WhichOf;}
    public void setWhichOf(Short value) {WhichOf = value;}
    
}
