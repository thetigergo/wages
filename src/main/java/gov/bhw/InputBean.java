package gov.bhw;

/**
 *
 * @author USER
 */
public class InputBean implements java.io.Serializable {
    
    private final java.util.List<javax.faces.model.SelectItem> arBarangay   = new java.util.ArrayList<>(),
                                                               arDistrict  = new java.util.ArrayList<>();
    
    private String Suffix = "", LastName, FirstName, Middle, Address, Trabaho = "BHW";
    private Double BaseRate;
    private Boolean IsActive = true;
    private Short District, Barrangay;
    
    // 0 = First tab (Entry Form), 1 = Second tab (Search)
    private Short activeTabIndex = 1;
    
    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}
    
    public java.util.List<javax.faces.model.SelectItem> getDistricts() {return arDistrict;}
    public java.util.List<javax.faces.model.SelectItem> getBarangays() {return arBarangay;}

    public String getSuffix() {return Suffix;}
    public void setSuffix(String value) {Suffix = value;}

    public String getLastName() {return LastName;}
    public void setLastName(String value) {LastName = value;}

    public String getFirstName() {return FirstName;}
    public void setFirstName(String value) {FirstName = value;}

    public String getMiddle() {return Middle;}
    public void setMiddle(String value) {Middle = value;}

    public String getAddress() {return Address;}
    public void setAddress(String value) {Address = value;}

    public Short getDistrict() {return District;}
    public void setDistrict(Short value) {District = value;}

    public Short getBarrangay() {return Barrangay;}
    public void setBarrangay(Short value) {Barrangay = value;}

    public Double getBaseRate() {return BaseRate;}
    public void setBaseRate(Double value) {BaseRate = value;}

    public Boolean getIsActive() {return IsActive;}
    public void setIsActive(Boolean value) {IsActive = value;}

    public String getTrabaho() {return Trabaho;}
    public void setTrabaho(String value) {Trabaho = value;}

    public int getActiveTabIndex() {return activeTabIndex;}
    
    
    
    
    @javax.annotation.PreDestroy
    public void cleanUp() {
        System.out.println("PreDestroy");
    }
    
    @javax.annotation.PostConstruct
    protected void init() {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = gov.dbase.PgSQLink.dbLink();
            java.sql.Statement _smt = jdbc.createStatement()) {
            
            try (java.sql.ResultSet tbl = _smt.executeQuery("SELECT distid, district FROM bhw.distrito ORDER BY district")) {
                while (tbl.next()) 
                    arDistrict.add(new javax.faces.model.SelectItem(tbl.getShort(1), tbl.getString(2)));
            }


        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onDistrictChange() {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = gov.dbase.PgSQLink.dbLink();
            java.sql.Statement _smt = jdbc.createStatement()) {
            String query =
                "SELECT " +
                    "brgyid, " +
                    "towned " +
                "FROM " +
                    "bhw.barangays " +
                "WHERE " +
                    "(district = " + District + ") " +
                "ORDER BY " +
                    "towned";
            try (java.sql.ResultSet tbl = _smt.executeQuery(query)) {
                arBarangay.clear();
                while (tbl.next()) arBarangay.add(new javax.faces.model.SelectItem(tbl.getString(1), tbl.getString(2)));
            }


        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void clearForm() {
        // Optional: If you clear the district, reset the dependent barangay list
        arBarangay.clear();
        
        Suffix = "";
        LastName = null;
        FirstName = null;
        Middle = null;
        Address = null;
        Trabaho = "BHW";
        BaseRate = 0D;
        IsActive = true;
        Barrangay = 0;
    }
    
    public void loadRecord(String recordId) {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = gov.dbase.PgSQLink.dbLink();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(
                        "SELECT " +
                            "suffix, " +        // 1
                            "lastname, " +      // 2
                            "firstname, " +     // 3
                            "midname, " +       // 4
                            "address, " +       // 5
                            "brgyid, " +        // 6
                            "jobdesc, " +       // 7
                            "payrate, " +       // 8
                            "district, " +      // 9
                            "status " +         //10
                        "FROM " +
                            "bhw.workers " +
                        "WHERE " +
                            "(uniqkey = ?)")) {
            psmt.setString(1, recordId);
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                if (rst.next()) {
                    Suffix = rst.getString(1);
                    LastName = rst.getString(2);
                    FirstName = rst.getString(3);
                    Middle = rst.getString(4);
                    Address = rst.getString(5);
                    District = rst.getShort(9);
                    
                    onDistrictChange();
                    
                    Barrangay = rst.getShort(6);
                            
                    BaseRate = rst.getDouble(8);
                    Trabaho  = rst.getString(7);
                    IsActive = rst.getBoolean(10);
                    
                    activeTabIndex = 0;
                } else
                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARNING", "No record found!");
            }
            

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }        
    }
}
