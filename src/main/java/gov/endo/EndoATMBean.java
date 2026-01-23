package gov.endo;

/**
 *
 * @author felix
 */
public class EndoATMBean implements java.io.Serializable {

    private static final long serialVersionUID = -790925076834694796L;
    
    private final java.util.List<javax.faces.model.SelectItem> arWorkers   = new java.util.ArrayList<>();
    private final java.util.List<gov.pay.WageField> arFields                   = new java.util.ArrayList<>();



    private final boolean NUMERIC = true;

    private Boolean isUpdatable = true, isSenior = false;
    private String Certify1, Rank1, Certify2, Rank2, Certify3, Rank3, Certification;
    private String WorkerID, Opesina, OpesinaID;
    private String Jobs, CtrlNo;
    private Short Minutes = 0;
    private Float Rate, Days = 0F;
    private Double TotalGross = 0D, Others, TotalWage = 0D, TotalDeduct = 0D, Bunos = 0D, TotalBunos = 0D, PagIbig = 0D, TotalHDMF = 0D, SSSPrem = 0D, TotalSSS = 0D, TotalTAX = 0D, TaxHeld = 0D;
    private java.util.Date DateFr, DateTo, PayFr, PayTo;
    

    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}

    

    
    
    public String getWorkerID() {return WorkerID;}
    public void setWorkerID(String values) {WorkerID = values;}
    
    public String getJobs() {return Jobs;}
    public void setJobs(String value) {Jobs = value;}

    public Float getRate() {return Rate;}
    public void setRate(Float value) {Rate = value;}

    public Float getDays() {return Days;}
    public void setDays(Float value) {Days = value;}

    public Short getMinutes() {return Minutes;}
    public void setMinutes(Short value) {Minutes = value;}

    public Double getPagIbig() {return PagIbig;}
    public void setPagIbig(Double value) {PagIbig = value;}

    public Double getSSSPrem() {return SSSPrem;}
    public void setSSSPrem(Double value) {SSSPrem = value;}

    public Double getOthers() {return Others;}
    public void setOthers(Double value) {Others = value;}

    public Double getTaxHeld() {return TaxHeld;}
    public void setTaxHeld(Double value) {TaxHeld = value;}

    public String getCtrlNo() {return CtrlNo;}
    public void setCtrlNo(String value) {CtrlNo = value;}

    public String getOpesina() {return Opesina;}

    public java.util.Date getDateFr() {return DateFr;}
    public void setDateFr(java.util.Date value) {DateFr = value;}

    public java.util.Date getDateTo() {return DateTo;}
    public void setDateTo(java.util.Date value) {DateTo = value;}

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

    public String getCertify3() {return Certify3;}
    public void setCertify3(String value) {Certify3 = value;}

    public String getRank3() {return Rank3;}
    public void setRank3(String value) {Rank3 = value;}

    public boolean isUpdatable() {return isUpdatable;}

    public String getCertification() {return Certification;}
    public void setCertification(String value) {Certification = value;}

    public Double getBunos() {return Bunos;}
    public void setBunos(Double value) {Bunos = value;}


    public boolean isRender() {
        if (PayFr == null)
            return false;
        else {
            java.util.Calendar almanac = java.util.Calendar.getInstance();
            almanac.setTime(PayFr);
            return almanac.get(java.util.Calendar.MONTH) == 11;
        }
    }
    
    public Boolean getIsSenior() {return isSenior;}


    public Double getTotalWage() {return TotalWage;}
    public Double getTotalGross() {return TotalGross;}
    public Double getTotalDeduct() {return TotalDeduct;}
    public Double getTotalBunos() {return TotalBunos;}
    public Double getTotalHDMF() {return TotalHDMF;}
    public Double getTotalSSS() {return TotalSSS;}
    public Double getTotalTAX() {return TotalTAX;}

    
    

    public java.util.List<javax.faces.model.SelectItem> getWorkers() {return arWorkers;}
    public java.util.List<gov.pay.WageField> getWages() {return arFields;}

    @javax.annotation.PreDestroy
    public void cleanUp() {
        System.out.println("PreDestroy");
    }

    @javax.annotation.PostConstruct
    protected void init() {
        OpesinaID = onlineUser.getOpesina();
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        "office " +
                    "FROM " +
                        "psnl.offices " +
                    "WHERE " +
                        "(offcid = '" + OpesinaID + "')")) {
            if (rst.next())
                Opesina = rst.getString(1);

            String query =
                "SELECT " +
                    "uniqkey, " +
                    "humane(lastname, firstname, suffix, midname), " +
                    "payrate " +
                "FROM " +
                    "psnl.jobworker " +
                "WHERE " +
                    "(bank_acct IS NOT NULL) AND " +
                    "(offcloc = '" + OpesinaID + "') " +
                "ORDER BY " +
                    "lastname, firstname, midname";
            try (java.sql.ResultSet tbl = _smt.executeQuery(query)) {
                while (tbl.next()) arWorkers.add(new javax.faces.model.SelectItem(tbl.getString(1), tbl.getString(2)));
            }


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        Certification = "CERTIFICATION - This is to certify that the above mentioned job order employees have rendered services in the " + Opesina + " on the date stated.";
    }    

    public void onWorkerChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement();
            java.sql.ResultSet rst = smt.executeQuery(
                    "SELECT " +
                        "jobdesc, " +
                        "payrate, " +
                        "DATE_PART('YEAR', AGE(NOW(), birthday))::SMALLINT " +
                    "FROM " +
                        "psnl.jobworker " +
                    "WHERE " +
                        "(uniqkey = '" + WorkerID + "')")) {
            if (rst.next()) {
                Jobs = rst.getString(1);
                Rate = rst.getFloat(2);
                isSenior = rst.getShort(3) >= 60;
                if (isSenior) {
                    SSSPrem = 0D;
                    PagIbig = 0D;
                }
            }


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onPayFrSelect(org.primefaces.event.SelectEvent<?> event) {
        PayFr = (java.util.Date)event.getObject();
        
//        java.util.Calendar cals = java.util.Calendar.getInstance();
//        cals.setTime(PayFr);
//        cals.set(cals.get(java.util.Calendar.YEAR), cals.get(java.util.Calendar.MONTH) + 1, 0);

//        MaxDate = PayFr;
//        MaxDate.setTime(cals.getTimeInMillis());
    }
    public void onPayToSelect(org.primefaces.event.SelectEvent<?> event) {
        PayTo = (java.util.Date)event.getObject();
    }
    
//    public void onDateFrSelect(org.primefaces.event.SelectEvent event) {
//        if (DateTo == null)
//            Days = 0F;
//        else {
//            DateFr = (java.util.Date)event.getObject();
//            long diffdays = DateTo.getTime() - DateFr.getTime();
//            Days = Float.valueOf(String.valueOf((diffdays / ONE_DAY) + 1));
//            Adlaw = Days;
//            //if (PagIbig.equals(0D)) CheckPagibigPremium(DateFr);
//        }
//    }
//    public void onDateToSelect(org.primefaces.event.SelectEvent event) {
//        if (DateFr == null)
//            Days = 0F;
//        else {
//            DateTo = (java.util.Date)event.getObject();
//            long diffdays = DateTo.getTime() - DateFr.getTime();
//            Days = Float.valueOf(String.valueOf((diffdays / ONE_DAY) + 1));
//            Adlaw = Days;
//            //if (PagIbig.equals(0D)) CheckPagibigPremium(DateTo);
//        }
//    }

    private void ReLoad() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        /* 1*/"jobworker.uniqkey, " +
                        /* 2*/"humane(jobworker.lastname, jobworker.firstname, jobworker.suffix, jobworker.midname), " +
                        /* 3*/"jobworker.jobdesc, " +
                        /* 4*/"laborpaid.datefr, " +
                        /* 5*/"laborpaid.dateto, " +
                        /* 6*/"laborpaid.udays, " +
                        /* 7*/"laborpaid.rate, " +
                        /* 8*/"CASE WHEN (laborpaid.frday < 16 AND laborpaid.today < 16) OR (laborpaid.frday = 16 AND laborpaid.today > 16) THEN (laborpaid.rate / 2.0)::REAL ELSE laborpaid.rate END, " +
                        /* 9*/"laborpaid.utime, " +
                        /*10*/"(laborpaid.rate / 10560.0 * ((laborpaid.udays * 480.0) + laborpaid.utime))::NUMERIC(18, 2), " +
                        /*11*/"laborpaid.bunos, " +
                        /*12*/"laborpaid.pag_ibig, " +
                        /*13*/"laborpaid.sssprem, " +
                        /*14*/"laborpaid.withtax " +
                    "FROM " +
                        "psnl.jobworker JOIN pay.laborpaid " +
                        "ON jobworker.uniqkey = laborpaid.worker " +
                    "WHERE " +
                        "(laborpaid.ctrlno = '" + CtrlNo + "') " +
                    "ORDER BY " +
                        "jobworker.lastname, jobworker.firstname, jobworker.midname")) {
            arFields.clear(); TotalWage = 0D; TotalGross = 0D; TotalBunos = 0D; TotalHDMF = 0D; TotalSSS = 0D;
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
                        rst.getDouble(14)));               //withtax
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();
                TotalBunos  += payroll.getBunos();
                TotalHDMF   += payroll.getPagIbig();
                TotalSSS    += payroll.getSSSPrem();
                TotalTAX    += payroll.getTaxHeld();
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
                               calto    = java.util.Calendar.getInstance();
            calfr.setTime(DateFr);
            calto.setTime(DateTo);

            if (CtrlNo == null) {
                int year = calfr.get(java.util.Calendar.YEAR);
                try (java.sql.ResultSet rst = _smt.executeQuery("SELECT pay.next_seq(" + year + ");");
                        java.sql.PreparedStatement psmt = jdbc.prepareStatement("INSERT INTO pay.suholan(ctrlno, payfr, payto, userid, typed) VALUES (?, ?, ?, ?, ?);")) {
                    if (rst.next()) {
                        String temp = new java.text.DecimalFormat("000000").format(rst.getInt(1));
                        CtrlNo = year + "~" + temp;
                    }
                    psmt.setString(1, CtrlNo);
                    psmt.setDate  (2, new java.sql.Date(PayFr.getTime()));
                    psmt.setDate  (3, new java.sql.Date(PayTo.getTime()));
                    psmt.setString(4, onlineUser.getUserOnline());
                    psmt.setString(5, "ATM");
                    psmt.executeUpdate();
                }
            }
            

            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.laborpaid");
            saver.FieldName("ctrlno",    !NUMERIC, gov.enums.Take.InsertOnly, CtrlNo);
            saver.FieldName("datefr",    !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateFr));
            saver.FieldName("dateto",    !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateTo));
            saver.FieldName("opesina",   !NUMERIC, gov.enums.Take.InsertOnly, OpesinaID);
            saver.FieldName("worker",    !NUMERIC, gov.enums.Take.InsertOnly, WorkerID);
            saver.FieldName("frday",      NUMERIC, gov.enums.Take.InsertOnly, calfr.get(java.util.Calendar.DATE));
            saver.FieldName("today",      NUMERIC, gov.enums.Take.InsertOnly, calto.get(java.util.Calendar.DATE));
            saver.FieldName("udays",      NUMERIC, gov.enums.Take.InsertOnly, Days);
            saver.FieldName("rate",       NUMERIC, gov.enums.Take.InsertOnly, Rate);
            saver.FieldName("utime",      NUMERIC, gov.enums.Take.InsertOnly, Minutes);
            saver.FieldName("station",   !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getClientIp());// java.net.InetAddress.getLocalHost().getHostName());
            saver.FieldName("userid",    !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getUserOnline());
            saver.FieldName("payfr",     !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayFr));
            saver.FieldName("payto",     !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayTo));
            saver.FieldName("bunos",      NUMERIC, gov.enums.Take.InsertOnly, Bunos);
            saver.FieldName("pag_ibig",   NUMERIC, gov.enums.Take.InsertOnly, PagIbig);
            saver.FieldName("sssprem",    NUMERIC, gov.enums.Take.InsertOnly, SSSPrem);
            saver.FieldName("withtax",    NUMERIC, gov.enums.Take.InsertOnly, TaxHeld);
            _smt.executeUpdate(saver.Perform(gov.enums.Fire.doInsert));

            updateOfficer(null);
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
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.laborpaid");
            saver.FieldName("ctrlno",   !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("officer1", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify1);
            saver.FieldName("officer2", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify2);
            saver.FieldName("officer3", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify3);
            saver.FieldName("designa1", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank1);
            saver.FieldName("designa2", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank2);
            saver.FieldName("designa3", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank3);
            saver.FieldName("certify",  !NUMERIC, gov.enums.Take.UpdateOnly,    Certification.replaceAll("'", "''"));
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
