package gov.endo;

/**
 *
 * @author felix
 */
public class ModifyEndoBean implements java.io.Serializable {

    private static final long serialVersionUID = 4018886714904788385L;
    
    private final java.util.List<gov.pay.WageField>                arFields = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arCtrls = new java.util.ArrayList<>();
    
    private final boolean NUMERIC = true; //, CONDITION = true;
//    private final long ONE_DAY = 1000 * 3600 * 24;

    private Boolean isSenior = false, HasATM;
    private String Certify1, Rank1, Certify2, Rank2, Certify3, Rank3, Certification;
    private String Worker, WorkerID, Opesina, OpesinaID;
    private String JobDesc, CtrlNo;
    private Short Minutes;
    private Float Days;
    private Double Rate, Gross, Others, TotalWage = 0D, TotalGross = 0D, TotalDeduct = 0D, Bunos = 0D, TotalBunos = 0D, PagIbig, TotalHDMF = 0D, TotalSSS = 0D, SSSPrem = 0D, TotalTAX = 0D, TaxHeld = 0D;
    private java.util.Date DateFr, DateTo, PayFr, PayTo;

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

    public String getCertification() {return Certification;}
    public void setCertification(String value) {Certification = value;}

    public Double getBunos() {return Bunos;}
    public void setBunos(Double value) {Bunos = value;}

    public Double getSSSPrem() {return SSSPrem;}
    public void setSSSPrem(Double value) {SSSPrem = value;}

    public Double getTaxHeld() {return TaxHeld;}
    public void setTaxHeld(Double value) {TaxHeld = value;}
 

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

    public Boolean getHasATM() {return HasATM;}
    
    
    

    public String getOpesina() {return Opesina;}
    public String getTotalWage()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalWage);}
    public String getTotalDeduct() {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalDeduct);}
    public String getTotalHDMF()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalHDMF);}
    public String getTotalSSS()    {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalSSS);}
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
        //return TotalBunos;
    }
    public String getTotalTAX() {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalTAX);}

    
    
    
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
                        "pay.laborpaid " +
                    "WHERE " +
                        "(opesina = '" + onlineUser.getOpesina() + "') AND " +
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
                    /* 1*/"laborpaid.worker, " +
                    /* 2*/"humane(jobworker.lastname, jobworker.firstname, jobworker.suffix, jobworker.midname), " +
                    /* 3*/"jobworker.jobdesc, " +
                    /* 4*/"laborpaid.datefr, " +
                    /* 5*/"laborpaid.dateto, " +
                    /* 6*/"laborpaid.udays, " +
                    /* 7*/"laborpaid.rate, " +
                    /* 8*/"CASE WHEN (laborpaid.frday < 16 AND laborpaid.today < 16) OR (laborpaid.frday = 16 AND laborpaid.today > 16) THEN (laborpaid.rate / 2.0)::REAL ELSE laborpaid.rate END, " +
                    /* 9*/"laborpaid.utime, " +
                    /*10*/"(laborpaid.rate / 10560.0 * ((laborpaid.udays * 480.0) + laborpaid.utime))::NUMERIC(18, 2), " +
                    /*11*/"laborpaid.payfr, " +
                    /*12*/"laborpaid.payto, " +
                    /*13*/"laborpaid.officer1, " +
                    /*14*/"laborpaid.officer2, " +
                    /*15*/"laborpaid.officer3, " +
                    /*16*/"laborpaid.designa1, " +
                    /*17*/"laborpaid.designa2, " +
                    /*18*/"laborpaid.designa3, " +
                    /*19*/"laborpaid.bunos, " +
                    /*20*/"laborpaid.pag_ibig, " +
                    /*21*/"laborpaid.sssprem, " +
                    /*22*/"laborpaid.withtax, " +
                    /*23*/"jobworker.bank_acct IS NOT NULL " +
                    "FROM " +
                        "psnl.jobworker JOIN pay.laborpaid " +
                        "ON jobworker.uniqkey = laborpaid.worker " +
                    "WHERE " +
                        "(laborpaid.ctrlno = '" + CtrlNo + "') " + // AND
                        //"(laborpaid.paid_up = FALSE) " +
                    "ORDER BY " +
                        "jobworker.lastname, jobworker.firstname, jobworker.midname")) {
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
                        rst.getDouble(20),
                        rst.getDouble(21),
                        (short)0,
                        rst.getDouble(19),
                        rst.getDouble(22)));
                HasATM = rst.getBoolean(23);
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();// + payroll.getPagIbig();
                TotalBunos  += payroll.getBunos();
                TotalHDMF   += payroll.getPagIbig();
                TotalSSS    += payroll.getSSSPrem();
                TotalTAX    += payroll.getTaxHeld();

                PayFr    = rst.getDate(11);
                PayTo    = rst.getDate(12);
                Certify1 = rst.getString(13);
                Certify2 = rst.getString(14);
                Certify3 = rst.getString(15);
                Rank1    = rst.getString(16);
                Rank2    = rst.getString(17);
                Rank3    = rst.getString(18);
            }



        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

//    public void onPayFrSelect(org.primefaces.event.SelectEvent event) {
//        PayFr = (java.util.Date)event.getObject();
//    }
//    public void onPayToSelect(org.primefaces.event.SelectEvent event) {
//        PayTo = (java.util.Date)event.getObject();
//    }
    
    public String onEditJOs(String value) {
        WorkerID = value;
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                    /* 1*/"humane(jobworker.lastname, jobworker.firstname, jobworker.suffix, jobworker.midname), " +
                    /* 2*/"jobworker.jobdesc, " +
                    /* 3*/"laborpaid.datefr, " +
                    /* 4*/"laborpaid.dateto, " +
                    /* 5*/"laborpaid.udays, " +
                    /* 6*/"laborpaid.rate, " +
                    /* 7*/"CASE WHEN (laborpaid.frday < 16 AND laborpaid.today < 16) OR (laborpaid.frday = 16 AND laborpaid.today > 16) THEN (laborpaid.rate / 2.0)::REAL ELSE laborpaid.rate END, " +
                    /* 8*/"laborpaid.utime, " +
                    /* 9*/"(laborpaid.rate / 10560.0 * ((laborpaid.udays * 480.0) + laborpaid.utime))::NUMERIC(18, 2), " +
                    /*10*/"laborpaid.bunos, " +
                    /*11*/"laborpaid.pag_ibig, " +
                    /*12*/"laborpaid.sssprem, " +
                    /*13*/"laborpaid.withtax, " +
                    /*14*/"DATE_PART('YEAR', AGE(NOW(), jobworker.birthday))::SMALLINT " +
                    "FROM " +
                        "pay.laborpaid JOIN psnl.jobworker " +
                        "ON laborpaid.worker = jobworker.uniqkey " +
                    "WHERE " +
                        "(laborpaid.ctrlno = '" + CtrlNo + "') AND " +
                        "(laborpaid.worker = '" + WorkerID + "')")) {
            if (rst.next()) {
                Worker    = rst.getString(1);
                JobDesc   = rst.getString(2);
                DateFr    = rst.getDate(3);
                DateTo    = rst.getDate(4);
                Days      = rst.getFloat(5);
                Rate      = rst.getDouble(6);
                Gross     = rst.getDouble(7);
                Minutes   = rst.getShort(8);
                Bunos     = rst.getDouble(10);
                PagIbig   = rst.getDouble(11);
                SSSPrem   = rst.getDouble(12);
                TaxHeld   = rst.getDouble(13);
                
                isSenior = rst.getShort(14) >= 60;
                if (isSenior) {
                    SSSPrem = 0D;
                    PagIbig = 0D;
                }
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
        try (java.sql.Connection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement smt = jdbc.createStatement()) {
            
            java.util.Calendar calfr = java.util.Calendar.getInstance(),
                               calto    = java.util.Calendar.getInstance();
            calfr.setTime(DateFr);
            calto.setTime(DateTo);


            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.laborpaid");
            saver.FieldName("ctrlno",    !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("datefr",    !NUMERIC, gov.enums.Take.UpdateOnly,    new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateFr));
            saver.FieldName("dateto",    !NUMERIC, gov.enums.Take.UpdateOnly,    new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateTo));
            saver.FieldName("opesina",   !NUMERIC, gov.enums.Take.UpdateOnly,    onlineUser.getOpesina());
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
            saver.FieldName("withtax",    NUMERIC, gov.enums.Take.UpdateOnly,    TaxHeld);
            smt.executeUpdate(saver.Perform(gov.enums.Fire.doUpdate));

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Updated successfully");

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
            
            retrieveJOs();
        }
    }
    
    public void updateOfficer(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
//        String buttonId = event.getComponent().getClientId();
//        System.out.println(buttonId);

        try (java.sql.Connection jdbc = new gov.dbase.PgSQLConn();
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

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Officers updated successfully");

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String onEraseJOs(String value) {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.laborpaid");
            saver.FieldName("ctrlno",  !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("worker",  !NUMERIC, gov.enums.Take.ConditionOnly, value);
            saver.FieldName("station", !NUMERIC, gov.enums.Take.ConditionOnly, onlineUser.getClientIp());// java.net.InetAddress.getLocalHost().getHostName());
            int success = _smt.executeUpdate(saver.Perform(gov.enums.Fire.doDelete));
            if (success == 0) {
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
