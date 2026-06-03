package gov.bhw;

/**
 *
 * @author USER
 */
public class SearchBean implements java.io.Serializable {
    
    private final java.util.List<SearchField> arFields = new java.util.ArrayList<>();
    
    private String Pangita;
    private SearchField napiliPerson;
    
//    @javax.inject.Inject
//    private InputBean inputBean;
    
    private InputBean inputBean;
    public void setPassKey(InputBean searcher) {inputBean = searcher;}
    private gov.dbase.PgDBbind pgDBlink;
    public void setPgDBlink(gov.dbase.PgDBbind value) {pgDBlink = value;}
    
    public java.util.List<SearchField> getPeople() {return arFields;}

    public String getPangita() {return Pangita;}
    public void setPangita(String value) {Pangita = value;}

    public SearchField getNapiliPerson() {return napiliPerson;}
    public void setNapiliPerson(SearchField value) {napiliPerson = value;}
    
    
    
    @javax.annotation.PreDestroy
    public void cleanUp() {
        System.out.println("PreDestroy");
    }
    
    @javax.annotation.PostConstruct
    protected void init() {}
    
    public void findNames() {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
            java.sql.PreparedStatement psmt = jdbc.prepareStatement(
                "SELECT " +
                    "uniqkey, " +
                    "suffix, " +
                    "lastname, " +
                    "firstname, " +
                    "midname " +
                "FROM " +
                    "bhw.workers " +
                "WHERE " +
                    "(UPPER(CONCAT(lastname, firstname, midname)) LIKE ?) " +
                "ORDER BY " +
                    "lastname, firstname, midname;")) {
            
            psmt.setString(1, "%" + Pangita.toUpperCase() + "%");
            try (java.sql.ResultSet tbl = psmt.executeQuery()) {
                arFields.clear();
                while (tbl.next()) 
                    arFields.add(new SearchField(
                            tbl.getString(1),
                            tbl.getString(2),
                            tbl.getString(3),
                            tbl.getString(4),
                            tbl.getString(5)));
            }


        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onRowSelect(org.primefaces.event.SelectEvent<SearchField> event) {
        javax.faces.application.FacesMessage msg = null;
        
        // Pass the ID to the entry form bean directly
        String recordId = event.getObject().getUniqKey();
        if (inputBean != null) {
            inputBean.loadRecord(recordId);
        } else {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", "CRITICAL ERROR: bhwNames instance could not be found in current ViewScope context.");
        }
        if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
