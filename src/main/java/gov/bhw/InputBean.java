package gov.bhw;

/**
 *
 * @author USER
 */
public class InputBean implements java.io.Serializable {
    
    private final java.util.List<javax.faces.model.SelectItem> arBarangay   = new java.util.ArrayList<>(),
                                                               arDistrict  = new java.util.ArrayList<>();
    
    private String Suffix = "", LastName, FirstName, Middle, Address, Trabaho = "BHW", uuidkey;
    private Double BaseRate = 0D;
    private Boolean IsActive = true;
    private Short District, Barrangay, activeTabIndex = 0;
    
    private gov.dbase.PgDBbind pgDBlink;
    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}
    public void setDBlink(gov.dbase.PgDBbind value) {pgDBlink = value;}
    
    
    
    public java.util.List<javax.faces.model.SelectItem> getDistricts() {return arDistrict;}
    public java.util.List<javax.faces.model.SelectItem> getBarangays() {return arBarangay;}

    public String getUuidkey() {return uuidkey;}
    public void setUuidkey(String value) {uuidkey = value;}

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

    public Short getActiveTabIndex() {return activeTabIndex;}
    
    public Boolean getHasNoKey() {return uuidkey == null || uuidkey.isEmpty() || uuidkey.isBlank();}
    
    
    
    
    @javax.annotation.PreDestroy
    public void cleanUp() {
        System.out.println("PreDestroy");
    }
    
    @javax.annotation.PostConstruct
    protected void init() {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
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
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
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
        
        uuidkey = "";
    }
    
    public void loadRecord(String recordId) {
        javax.faces.application.FacesMessage msg = null;
        boolean triggerDistrictChange = false; // Flag to defer the call
        
        // 1. Open and completely CLOSE the connection for loading the worker
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
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
                    
                    //onDistrictChange();
                    
                    Barrangay = rst.getShort(6);
                            
                    BaseRate = rst.getDouble(8);
                    Trabaho  = rst.getString(7);
                    IsActive = rst.getBoolean(10);
                    
                    activeTabIndex = 0;
                    uuidkey = recordId;
                    
                    // Set flag to true instead of calling the method inside the try block
                    triggerDistrictChange = true;
                } else
                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARNING", "No record found!");
            }
            

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        // 2. Safely trigger the dependent dropdown query AFTER connection #1 is released back to Hikari
        if (triggerDistrictChange) onDistrictChange(); 
    }
    
    public java.util.List<String> completeSurname(String query) {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(
                        "SELECT DISTINCT " +
                            "lastname " +
                        "FROM " +
                            "bhw.workers " +
                        "WHERE " +
                            "(LOWER(lastname) LIKE ?) " +
                        "ORDER BY " +
                            "lastname;")) {
            java.util.List<String> namesList = new java.util.ArrayList<>();
            psmt.setString(1, "%" + query.toLowerCase() + "%");
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                while (rst.next()) 
                    namesList.add(rst.getString(1));
            }
            return namesList;

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }
    
    public java.util.List<String> completeNgalan(String query) {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(
                        "SELECT DISTINCT " +
                            "firstname " +       
                        "FROM " +
                            "bhw.workers " +
                        "WHERE " +
                            "(LOWER(firstname) LIKE ?) " +
                        "ORDER BY " +
                            "firstname;")) {
            java.util.List<String> namesList = new java.util.ArrayList<>();
            psmt.setString(1, "%" + query.toLowerCase() + "%");
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                while (rst.next()) 
                    namesList.add(rst.getString(1));
            }
            return namesList;

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }
    
    public java.util.List<String> completeMiddle(String query) {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(
                        "SELECT DISTINCT " +
                            "midname " +       
                        "FROM " +
                            "bhw.workers " +
                        "WHERE " +
                            "(LOWER(midname) LIKE ?) " +
                        "ORDER BY " +
                            "midname;")) {
            java.util.List<String> namesList = new java.util.ArrayList<>();
            psmt.setString(1, "%" + query.toLowerCase() + "%");
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                while (rst.next()) 
                    namesList.add(rst.getString(1));
            }
            return namesList;

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }
    
    public void postSaveForm() {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(
                        "INSERT INTO " +
                            "bhw.workers( " +
                                "suffix, " +        // 1
                                "lastname, " +      // 2
                                "firstname, " +     // 3
                                "midname, " +       // 4
                                "address, " +       // 5
                                "jobdesc, " +       // 6
                                "inputby, " +       // 7
                                "payrate, " +       // 8
                                "brgyid, " +        // 9
                                "district " +       //10
                            ") " +       
                        "VALUES " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                        "ON CONFLICT (lastname, firstname, midname) " +
                        "DO UPDATE SET " +
                            "suffix = EXCLUDED.suffix, " +
                            "lastname = EXCLUDED.lastname, " +
                            "firstname = EXCLUDED.firstname, " +
                            "midname = EXCLUDED.midname, " +
                            "address = EXCLUDED.address, " +
                            "jobdesc = EXCLUDED.jobdesc, " +
                            "payrate = EXCLUDED.payrate, " +
                            "brgyid = EXCLUDED.brgyid, " +
                            "district = EXCLUDED.district " +
                        "RETURNING uniqkey;")) {
            
            psmt.setString( 1, Suffix == null ? "" : Suffix);
            psmt.setString( 2, LastName.toUpperCase());
            psmt.setString( 3, FirstName.toUpperCase());
            psmt.setString( 4, Middle.toUpperCase());
            psmt.setString( 5, Address);
            psmt.setString( 6, Trabaho);
            psmt.setString( 7, onlineUser.getUserOnline());
            psmt.setDouble( 8, BaseRate);
            psmt.setShort ( 9, Barrangay);
            psmt.setShort (10, District);
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                if (rst.next()) {
                    uuidkey = rst.getString(1);
                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Record save successfully!");
                    clearForm();
                }
            }

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void eraseInfo() {
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = pgDBlink.dbLink();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement("DELETE FROM bhw.workers WHERE (uniqkey = ?)")) {
            psmt.setString(1, uuidkey);
            psmt.executeUpdate();
            
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Record deleted successfully!");
            clearForm();
            

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
