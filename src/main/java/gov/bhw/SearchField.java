package gov.bhw;

/**
 *
 * @author USER
 */
public class SearchField implements java.io.Serializable {

    private final String UniqKey, Suffix, Lastname, Firstname, Midname;

    public SearchField(String uidkey, String suffix, String lastname, String firstname, String middle) {
        UniqKey = uidkey; Suffix = suffix; Lastname = lastname; Firstname = firstname; Midname = middle;
    }

    public String getUniqKey() {return UniqKey;}

    public String getSuffix() {return Suffix;}

    public String getLastname() {return Lastname;}

    public String getFirstname() {return Firstname;}

    public String getMidname() {return Midname;}
    
}
