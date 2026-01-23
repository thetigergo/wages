package gov.bhw;

/**
 *
 * @author felix
 */
public class BHWCAPBean implements java.io.Serializable {

    private static final long serialVersionUID = -5882945600971775656L;
    
    private final java.util.List<javax.faces.model.SelectItem> arWorkers   = new java.util.ArrayList<>(),
                                                               arDistrict  = new java.util.ArrayList<>(),
                                                               arBarangay  = new java.util.ArrayList<>();
    private final java.util.List<gov.pay.WageField> arFields                   = new java.util.ArrayList<>();



    private final boolean NUMERIC = true;
    private final Double PagIbig = 0D, SSSPrem = 0D;

    private boolean isUpdatable = true, disableProject = false;
    private String Certify1, Rank1, Certify2, Rank2; //, Certify3, Rank3;
    private String WorkerID;
    private String Jobs, CtrlNo, DateNow;
    private Short Minutes = 0;
    private Float Rate, UDays;
    private Double TotalGross = 0D, Others, TotalWage = 0D, TotalDeduct = 0D, Bunos = 0D, TotalBunos = 0D; //, TotalHDMF = 0D, TotalSSS = 0D;
    private java.util.Date /*DateFr, DateTo, */PayFr, PayTo;
    private Short Barangay, District;
    

    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}





    public String getWorkerID() {return WorkerID;}
    public void setWorkerID(String values) {WorkerID = values;}
    
    public String getJobs() {return Jobs;}
    public void setJobs(String value) {Jobs = value;}

    public Float getRate() {return Rate;}
    public void setRate(Float value) {Rate = value;}

    public Float getDays() {return UDays;}
    public void setDays(Float value) {UDays = value;}

    public Short getMinutes() {return Minutes;}
    public void setMinutes(Short value) {Minutes = value;}

    public Double getOthers() {return Others;}
    public void setOthers(Double value) {Others = value;}

    public String getCtrlNo() {return CtrlNo;}
    public void setCtrlNo(String value) {CtrlNo = value;}

//    public java.util.Date getDateFr() {return DateFr;}
//    public void setDateFr(java.util.Date value) {DateFr = value;}

//    public java.util.Date getDateTo() {return DateTo;}
//    public void setDateTo(java.util.Date value) {DateTo = value;}

    public java.util.Date getPayFr() {return PayFr;}
    public void setPayFr(java.util.Date value) {PayFr = value;}

    public java.util.Date getPayTo() {return PayTo;}
    public void setPayTo(java.util.Date value) {PayTo = value;}

    public String getCertify1() {return Certify1;}
    public void setCertify1(String value) {Certify1 = value;}

    public String getRank1() {return Rank1;}
    public void setRank1(String value) {Rank1 = value;}

    public String getCertify2() {return Certify2;}
    public void setCertify2(String value) {Certify2 = value;}

    public String getRank2() {return Rank2;}
    public void setRank2(String value) {Rank2 = value;}

//    public String getCertify3() {return Certify3;}
//    public void setCertify3(String value) {Certify3 = value;}
//
//    public String getRank3() {return Rank3;}
//    public void setRank3(String value) {Rank3 = value;}

    public boolean isUpdatable() {return isUpdatable;}
    public boolean isDisableProject() {return disableProject;}

    public Double getBunos() {return Bunos;}
    public void setBunos(Double value) {Bunos = value;}
    
    public Short getBarangay() {return Barangay;}
    public void setBarangay(Short value) {Barangay = value;}

    public Short getDistrict() {return District;}
    public void setDistrict(Short value) {District = value;}
    
    public boolean isRender() {
//        return PayFr != null;
        if (PayFr == null)
            return false;
        else {
            java.util.Calendar almanac = java.util.Calendar.getInstance();
            almanac.setTime(PayFr);
            return almanac.get(java.util.Calendar.MONTH) >= 9;
        }
    }

    public String getDateNow() {return DateNow;}
    
    

    public String getTotalBunos()  {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalBunos);}
    public String getTotalWage()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalWage);}
    public String getTotalGross()  {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalGross);}
    public String getTotalDeduct() {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalDeduct);}


    
    

    public java.util.List<javax.faces.model.SelectItem> getWorkers() {return arWorkers;}
    public java.util.List<javax.faces.model.SelectItem> getBarangays() {return arBarangay;}
    public java.util.List<javax.faces.model.SelectItem> getDistricts() {return arDistrict;}
    public java.util.List<gov.pay.WageField> getWages() {return arFields;}


    @javax.annotation.PreDestroy
    public void cleanUp() {
        System.out.println("PreDestroy");
    }

    @javax.annotation.PostConstruct
    protected void init() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            try (java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        "distid, " +
                        "district " +
                    "FROM " +
                        "bhw.distrito " +
                    "ORDER BY " +
                        "district")) {
                while (rst.next())
                    arDistrict.add(new javax.faces.model.SelectItem(rst.getShort(1), rst.getString(2)));
            }
            DateNow = new java.text.SimpleDateFormat("MMMM dd, yyyy").format(new java.util.Date());



        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onDistrictChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
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


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onBarangayChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            String query =
                "SELECT " +
                    "uniqkey, " +
                    "humane(lastname, firstname, suffix, midname), " +
                    "payrate " +
                "FROM " +
                    "bhw.workers " +
                "WHERE " +
                    "(brgyid = " + Barangay + ") AND " +
                    "(district = " + District + ") AND " +
                    "(status IS TRUE) " +
                "ORDER BY " +
                    "lastname, firstname, midname";
            try (java.sql.ResultSet tbl = _smt.executeQuery(query)) {
                arWorkers.clear();
                while (tbl.next()) arWorkers.add(new javax.faces.model.SelectItem(tbl.getString(1), tbl.getString(2)));
            }
            disableProject = true;


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onWorkerChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement();
            java.sql.ResultSet rst = smt.executeQuery(
                    "SELECT " +
                        "jobdesc, " +
                        "payrate " +
                    "FROM " +
                        "bhw.workers " +
                    "WHERE " +
                        "(uniqkey = '" + WorkerID + "')")) {
            if (rst.next()) {
                Jobs = rst.getString(1);
                Rate = rst.getFloat(2);
            }


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onPayFrSelect(org.primefaces.event.SelectEvent<?> event) {
        PayFr = (java.util.Date)event.getObject();
    }
    public void onPayToSelect(org.primefaces.event.SelectEvent<?> event) {
        PayTo = (java.util.Date)event.getObject();
    }    

    private void ReLoad() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        /* 1*/"workers.uniqkey, " +
                        /* 2*/"humane(workers.lastname, workers.firstname, suffix, workers.midname), " +
                        /* 3*/"workers.jobdesc, " +
                        /* 4*/"bhwwages.payfr, " +
                        /* 5*/"bhwwages.payto, " +
                        /* 6*/"bhwwages.udays, " +
                        /* 7*/"bhwwages.rate, " +
                        /* 8*/"bhwwages.rate * (EXTRACT(MONTH FROM AGE(bhwwages.payto, bhwwages.payfr)) + 1), " +
                        /* 9*/"bhwwages.utime, " +
                        /*10*/"0, " + //deductions
                        /*11*/"bhwwages.bunos, " +
                        /*12*/"bhwwages.pag_ibig, " +
                        /*13*/"bhwwages.sssprem, " +
                        /*14*/"bhwwages.officer1, " +
                        /*15*/"bhwwages.officer2, " +
                        /*16*/"bhwwages.designa1, " +
                        /*17*/"bhwwages.designa2 " +
                    "FROM " +
                        "bhw.workers JOIN pay.bhwwages " +
                        "ON workers.uniqkey = bhwwages.worker " +
                    "WHERE " +
                        "(bhwwages.ctrlno = '" + CtrlNo + "') " +
                    "ORDER BY " +
                        "workers.lastname, workers.firstname, workers.midname")) {
            arFields.clear(); TotalWage = 0D; TotalGross = 0D; TotalBunos = 0D; //TotalHDMF = 0D; TotalSSS = 0D;
            while (rst.next()) {
                arFields.add(new gov.pay.WageField(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDate  (4),
                        rst.getDate  (5),
                        rst.getFloat (6),
                        rst.getFloat (7),
                        rst.getDouble(8),
                        rst.getShort (9),
                        rst.getDouble(10),
                        rst.getDouble(12),
                        rst.getDouble(13),
                        (short)0,
                        rst.getDouble(11),
                        0D));               //withtax
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();
                TotalBunos  += payroll.getBunos();
                //TotalHDMF   += payroll.getPagIbig();
                //TotalSSS    += payroll.getSSSPrem();
                Certify1 = rst.getString(14);
                Certify2 = rst.getString(15);

                Rank1    = rst.getString(16);
                Rank2    = rst.getString(17);
                
            }


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void saveEntry(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {

            java.util.Calendar calfr = java.util.Calendar.getInstance(),
                               calto = java.util.Calendar.getInstance();
            calfr.setTime(PayFr);
            calto.setTime(PayTo);

            if (CtrlNo == null) {
                int year = calfr.get(java.util.Calendar.YEAR);
                try (java.sql.ResultSet rst = _smt.executeQuery("SELECT pay.next_seq(" + year + ");")) {
                    if (rst.next()) {
                        String temp = new java.text.DecimalFormat("000000").format(rst.getInt(1));
                        CtrlNo = year + "~" + temp;
                    }
                }
            }

            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.bhwwages");
            saver.FieldName("ctrlno",    !NUMERIC, gov.enums.Take.InsertOnly, CtrlNo);
            saver.FieldName("opesina",   !NUMERIC, gov.enums.Take.InsertOnly, District);
            saver.FieldName("worker",    !NUMERIC, gov.enums.Take.InsertOnly, WorkerID);
            saver.FieldName("frday",      NUMERIC, gov.enums.Take.InsertOnly, calfr.get(java.util.Calendar.DATE));
            saver.FieldName("today",      NUMERIC, gov.enums.Take.InsertOnly, calto.get(java.util.Calendar.DATE));
            saver.FieldName("udays",      NUMERIC, gov.enums.Take.InsertOnly, UDays);
            saver.FieldName("rate",       NUMERIC, gov.enums.Take.InsertOnly, Rate);
            saver.FieldName("utime",      NUMERIC, gov.enums.Take.InsertOnly, Minutes);
            saver.FieldName("station",   !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getClientIp());// java.net.InetAddress.getLocalHost().getHostName());
            saver.FieldName("userid",    !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getUserOnline());
            saver.FieldName("payfr",     !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayFr));
            saver.FieldName("payto",     !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayTo));
            saver.FieldName("bunos",      NUMERIC, gov.enums.Take.InsertOnly, Bunos);
            saver.FieldName("pag_ibig",   NUMERIC, gov.enums.Take.InsertOnly, PagIbig);
            saver.FieldName("sssprem",    NUMERIC, gov.enums.Take.InsertOnly, SSSPrem);
            _smt.executeUpdate(saver.Perform(gov.enums.Fire.doInsert));

            updateOfficer(event);
            
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Successfully saved");
            

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        ReLoad();
    }

    public void updateOfficer(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
//        if (event != null) {
//            String buttonId = event.getComponent().getClientId();
//            System.out.println(buttonId);
//        }

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement()) {
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.bhwwages");
            saver.FieldName("ctrlno",   !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("officer1", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify1);
            saver.FieldName("officer2", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify2);
//            saver.FieldName("officer3", !NUMERIC, enums.Take.UpdateOnly,    Certify3);
            saver.FieldName("designa1", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank1);
            saver.FieldName("designa2", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank2);
//            saver.FieldName("designa3", !NUMERIC, enums.Take.UpdateOnly,    Rank3);
            smt.executeUpdate(saver.Perform(gov.enums.Fire.doUpdate));

            if (event != null) msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Officers updated successfully");

        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
            
            isUpdatable = false;
        }
    }
}
