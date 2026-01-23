package gov.bhw;

/**
 *
 * @author felix
 */
public class BHWmodifyBean implements java.io.Serializable {

    private static final long serialVersionUID = 2433750266522307389L;
    
    private final java.util.List<gov.pay.WageField>                arFields = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arCtrls = new java.util.ArrayList<>();
    
    private final boolean NUMERIC = true; //, CONDITION = true;

    private String Certify1, Rank1, Certify2, Rank2; //, Certify3, Rank3, Certification;
    private String Worker, WorkerID, Opesina, OpesinaID;
    private String JobDesc, CtrlNo, DateNow, Barangay, District;
    private Short Minutes;
    private Float Days;
    private Double Rate, Gross, Others, TotalWage = 0D, TotalGross = 0D, TotalDeduct = 0D, Bunos = 0D, TotalBunos = 0D, PagIbig, TotalHDMF = 0D, TotalSSS = 0D, SSSPrem = 0D;
    private java.util.Date /*DateFr, DateTo, */PayFr, PayTo;

    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}
    
    public java.util.List<javax.faces.model.SelectItem> getControls() {return arCtrls;}
    public java.util.List<gov.pay.WageField> getWages() {return arFields;}
    
    
    public String getWorkerID() {return WorkerID;}

    public String getWorker() {return Worker;}

    public String getJobDesc() {return JobDesc;}

    public Float getDays() {return Days;}
    public void setDays(Float value) {Days = value;}

    public Short getMinutes() {return Minutes;}
    public void setMinutes(Short value) {Minutes = value;}

    public Double getRate() {return Rate;}
    public void setRate(Double value) {Rate = value;}

    public Double getGross() {return Gross;}
    public void setGross(Double value) {Gross = value;}

    public Double getOthers() {return Others;}
    public void setOthers(Double value) {Others = value;}

    public Double getPagIbig() {return PagIbig;}
    public void setPagIbig(Double value) {PagIbig = value;}

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
//
//    public String getCertification() {return Certification;}
//    public void setCertification(String value) {Certification = value;}

    public Double getBunos() {return Bunos;}
    public void setBunos(Double value) {Bunos = value;}

    public Double getSSSPrem() {return SSSPrem;}
    public void setSSSPrem(Double value) {SSSPrem = value;}

    public boolean isRender() {
        if (PayFr == null)
            return false;
        else {
            java.util.Calendar almanac = java.util.Calendar.getInstance();
            almanac.setTime(PayFr);
            return almanac.get(java.util.Calendar.MONTH) >= 9;
        }
    }
    
    public String getDateNow() {return DateNow;}
    
    public String getBarangay() {return Barangay;}

    public String getDistrict() {return District;}    

    public String getOpesina()     {return Opesina;}
    public String getTotalWage()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalWage);  }
    public String getTotalDeduct() {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalDeduct);}
    public String getTotalHDMF()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalHDMF);  }
    public String getTotalSSS()    {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalSSS);   }
    public String getTotalGross()  {
        java.text.DecimalFormatSymbols dfs = new java.text.DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator(' ');
        java.text.DecimalFormat currency;
        currency = new java.text.DecimalFormat("#,##0.00");
        currency.setDecimalFormatSymbols(dfs);
        return currency.format(TotalGross.doubleValue());
        //if (TotalGross instanceof Double)
        //return new java.text.DecimalFormat("#,##0.00", null)TotalGross;
    }
    public String getTotalBunos()  {
        java.text.DecimalFormatSymbols dfs = new java.text.DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator(' ');
        java.text.DecimalFormat currency;
        currency = new java.text.DecimalFormat("#,##0.00");
        currency.setDecimalFormatSymbols(dfs);
        return currency.format(TotalBunos.doubleValue());
    }

    
    
    
    @javax.annotation.PostConstruct
    protected void init() {
        OpesinaID = onlineUser.getOpesina();
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            try (java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT DISTINCT " +
                        "ctrlno " +
                    "FROM " +
                        "pay.bhwwages " +
                    "WHERE " +
//                        "(opesina = '" + onlineUser.getOpesina() + "') AND " +
                        //"(paid_up = FALSE) AND " +
                        "(acctg_ref IS NULL) " +
                    "ORDER BY " +
                        "ctrlno")) {
                while (rst.next())
                    arCtrls.add(new javax.faces.model.SelectItem(rst.getString(1)));
            }
            try (java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        "office " +
                    "FROM " +
                        "psnl.offices " +
                    "WHERE " +
                        "(offcid = '" + OpesinaID + "')")) {
                if (rst.next())
                    Opesina = rst.getString(1);
            }
            DateNow = new java.text.SimpleDateFormat("MMMM dd, yyyy").format(new java.util.Date());

            
        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    } 
    
    public void retrieveJOs() {    //public void retrieveJOs(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                    /* 1*/"bhwwages.worker, " +
                    /* 2*/"humane(workers.lastname, workers.firstname, workers.suffix, workers.midname), " +
                    /* 3*/"workers.jobdesc, " +
                    /* 4*/"bhwwages.payfr, " +
                    /* 5*/"bhwwages.payto, " +
                    /* 6*/"bhwwages.udays, " +
                    /* 7*/"bhwwages.rate, " +
                    /* 8*/"bhwwages.rate * (EXTRACT(MONTH FROM AGE(bhwwages.payto, bhwwages.payfr)) + 1), " +
                    /* 9*/"bhwwages.utime, " +
                    /*10*/"0.0, " +
                    /*11*/"bhwwages.bunos, " +
                    /*12*/"bhwwages.pag_ibig, " +
                    /*13*/"bhwwages.sssprem, " +
                            
                    /*14*/"bhwwages.officer1, " +
                    /*15*/"bhwwages.officer2, " +
                    /*16*/"bhwwages.designa1, " +
                    /*17*/"bhwwages.designa2, " +
                    /*18*/"barangays.towned, " +
                    /*19*/"distrito.district " +
                    "FROM " +
                        "bhw.workers JOIN pay.bhwwages " +
                        "ON workers.uniqkey = bhwwages.worker " +
                        "INNER JOIN bhw.barangays " +
                        "ON workers.brgyid = barangays.brgyid " +
                        "INNER JOIN bhw.distrito " +
                        "ON barangays.district = distrito.distid " +
                    "WHERE " +
                        "(bhwwages.ctrlno = '" + CtrlNo + "') AND " +
                        "(bhwwages.paid_up = FALSE) " +
                    "ORDER BY " +
                        "workers.lastname, workers.firstname, workers.midname")) {
            arFields.clear(); TotalWage = 0D; TotalGross = 0D; TotalDeduct = 0D; TotalBunos = 0D; TotalHDMF = 0D; TotalSSS = 0D;
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
                TotalDeduct += payroll.getDeduction();// + payroll.getPagIbig();
                TotalBunos  += payroll.getBunos();
                TotalHDMF   += payroll.getPagIbig();
                TotalSSS    += payroll.getSSSPrem();

                PayFr    = rst.getDate(4);
                PayTo    = rst.getDate(5);
                Certify1 = rst.getString(14);
                Certify2 = rst.getString(15);
//                Certify3 = rst.getString(15);
                Rank1    = rst.getString(16);
                Rank2    = rst.getString(17);
//                Rank3    = rst.getString(18);
                Barangay = rst.getString(18);
                District = rst.getString(19);
            }



        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String onEditWork(String value) {
        WorkerID = value;
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                    /*1*/"humane(workers.lastname, workers.firstname, workers.suffix, workers.midname), " +
                    /*2*/"workers.jobdesc, " +
                    /*3*/"bhwwages.udays, " +
                    /*4*/"bhwwages.rate, " +
                    /*5*/"bhwwages.rate * (EXTRACT(MONTH FROM AGE(bhwwages.payto, bhwwages.payfr)) + 1), " +
                    /*6*/"bhwwages.utime, " +
                    /*7*/"bhwwages.bunos, " +
                    /*8*/"bhwwages.pag_ibig, " +
                    /*9*/"bhwwages.sssprem " +
                    "FROM " +
                        "pay.bhwwages JOIN bhw.workers " +
                        "ON bhwwages.worker = workers.uniqkey " +
                    "WHERE " +
                        "(bhwwages.ctrlno = '" + CtrlNo + "') AND " +
                        "(bhwwages.worker = '" + WorkerID + "')")) {
            if (rst.next()) {
                Worker    = rst.getString(1);
                JobDesc   = rst.getString(2);
                Days      = rst.getFloat(3);
                Rate      = rst.getDouble(4);
                Gross     = rst.getDouble(5);
                Minutes   = rst.getShort(6);
                Bunos     = rst.getDouble(7);
                PagIbig   = rst.getDouble(8);
                SSSPrem   = rst.getDouble(9);
            }


        } catch (NumberFormatException | java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        retrieveJOs();
        return null;
    }

    public void updateEntry(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
//        String buttonId = event.getComponent().getClientId();
//        System.out.println(buttonId);
        java.sql.Connection jdbc = null;
        try {
            jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement();
            
            java.util.Calendar calfr = java.util.Calendar.getInstance(),
                               calto    = java.util.Calendar.getInstance();
            calfr.setTime(PayFr);
            calto.setTime(PayTo);


            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.bhwwages");
            saver.FieldName("ctrlno",    !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
//            saver.FieldName("opesina",   !NUMERIC, enums.Take.UpdateOnly,    District);
            saver.FieldName("worker",    !NUMERIC, gov.enums.Take.ConditionOnly, WorkerID);
            saver.FieldName("frday",      NUMERIC, gov.enums.Take.UpdateOnly,    calfr.get(java.util.Calendar.DATE));
            saver.FieldName("today",      NUMERIC, gov.enums.Take.UpdateOnly,    calto.get(java.util.Calendar.DATE));
            saver.FieldName("udays",      NUMERIC, gov.enums.Take.UpdateOnly,    Days);
            saver.FieldName("rate",       NUMERIC, gov.enums.Take.UpdateOnly,    Rate);
            saver.FieldName("utime",      NUMERIC, gov.enums.Take.UpdateOnly,    Minutes);
            saver.FieldName("station",   !NUMERIC, gov.enums.Take.UpdateOnly,    onlineUser.getClientIp());// java.net.InetAddress.getLocalHost().getHostName());
            saver.FieldName("userid",    !NUMERIC, gov.enums.Take.UpdateOnly,    onlineUser.getUserOnline());
            saver.FieldName("payfr",     !NUMERIC, gov.enums.Take.UpdateOnly,    new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayFr));
            saver.FieldName("payto",     !NUMERIC, gov.enums.Take.UpdateOnly,    new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayTo));
            saver.FieldName("bunos",      NUMERIC, gov.enums.Take.UpdateOnly,    Bunos);
            saver.FieldName("pag_ibig",   NUMERIC, gov.enums.Take.UpdateOnly,    PagIbig);
            saver.FieldName("sssprem",    NUMERIC, gov.enums.Take.UpdateOnly,    SSSPrem);
            smt.executeUpdate(saver.Perform(gov.enums.Fire.doUpdate));

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Updated successfully");

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (jdbc != null) try {
                jdbc.close();
            } catch (Exception ef) {
                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ef.getMessage());
            }
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
            
            retrieveJOs();
        }
    }
    
    public void updateOfficer(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
//        String buttonId = event.getComponent().getClientId();
//        System.out.println(buttonId);

        java.sql.Connection jdbc = null;
        try {
            jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement();
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.bhwwages");
            saver.FieldName("ctrlno",   !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("officer1", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify1);
            saver.FieldName("officer2", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify2);
//            saver.FieldName("officer3", !NUMERIC, enums.Take.UpdateOnly,    Certify3);
            saver.FieldName("designa1", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank1);
            saver.FieldName("designa2", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank2);
//            saver.FieldName("designa3", !NUMERIC, enums.Take.UpdateOnly,    Rank3);
//            saver.FieldName("certify",  !NUMERIC, enums.Take.UpdateOnly,    Certification.replaceAll("'", "''"));
            smt.executeUpdate(saver.Perform(gov.enums.Fire.doUpdate));

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Officers updated successfully");

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (jdbc != null) try {
                jdbc.close();
            } catch (Exception ef) {
                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ef.getMessage());
            }
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String onEraseJOs(String value) {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.bhwwages");
            saver.FieldName("ctrlno",  !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("worker",  !NUMERIC, gov.enums.Take.ConditionOnly, value);
            saver.FieldName("station", !NUMERIC, gov.enums.Take.ConditionOnly, onlineUser.getClientIp());// java.net.InetAddress.getLocalHost().getHostName());
            int success = _smt.executeUpdate(saver.Perform(gov.enums.Fire.doDelete));
            if (success == 0) {
                jdbc.rollback();
                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARN", "Invalid workstation. User can only delete on its workstation where it created.");
            } else {
                for (short abc = 0; abc < arFields.size(); abc++) {
                    if (arFields.get(abc).getWorkID().equals(value)) {
                        arFields.remove(abc);
                        break;
                    }
                }
                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Record deleted successfully");
            }
            
            

        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }
}

