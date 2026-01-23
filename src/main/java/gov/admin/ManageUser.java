package gov.admin;

/**
 *
 * @author felix
 */
public class ManageUser /*implements java.io.Serializable*/ {

    private final String mUser, mUserName, mOpesina;

    public ManageUser(String userid, String username, String opesina) {mUser = userid; mUserName = username; mOpesina = opesina;}
    
    public String getUserID() {return mUser;}

    public String getUserName() {return mUserName;}

    public String getOpesina() {return mOpesina;}
    
}