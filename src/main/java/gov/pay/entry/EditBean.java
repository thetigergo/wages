package gov.pay.entry;

/**
 *
 * @author felix
 */
public class EditBean implements java.io.Serializable {

    private static final long serialVersionUID = 632263719387980384L;
    
    private final java.util.List<gov.pay.WageField> arWages = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arCtrls = new java.util.ArrayList<>();
    
    private final boolean NUMERIC = true; //, CONDITION = true;
    private final long ONE_DAY = 1000 * 3600 * 24;

    private String Certify1, Rank1, Certify2, Rank2, Certify3, Rank3, Certification;
    private String Worker, WorkerID, ProjectTitle, ProjectID, Opesina/*, OpesinaID*/;
    private String JobDesc, CtrlNo, JobOrder;
    private Short Minutes, Counter = 0;
    private Float Rate, Days, Available, Adlaw;
    private Double Gross, Others, TotalWage = 0D, TotalGross = 0D, TotalDeduct = 0D, Bunos = 0D, TotalBunos = 0D, PagIbig, TotalHDMF = 0D, TotalSSS = 0D, SSSPrem;
    private java.util.Date DateFr, DateTo, PayFr, PayTo;
    private Boolean HasATM;

    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}
    
    public java.util.List<javax.faces.model.SelectItem> getControls() {return arCtrls;}
    public java.util.List<gov.pay.WageField> getWages() {return arWages;}
    
    
    public String getWorkerID() {return WorkerID;}

    public String getWorker() {return Worker;}

    public String getJobDesc() {return JobDesc;}

    public Float getDays() {return Days;}
    public void setDays(Float value) {Days = value;}

    public Short getMinutes() {return Minutes;}
    public void setMinutes(Short value) {Minutes = value;}

    public Float getRate() {return Rate;}
    public void setRate(Float value) {Rate = value;}

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

    public String getJobOrder() {return JobOrder;}

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

    public Float getAvailable() {return Available;}
    public void setAvailable(Float value) {Available = value;}
    
    public boolean isRender() {
        if (PayFr == null)
            return false;
        else {
            java.util.Calendar almanac = java.util.Calendar.getInstance();
            almanac.setTime(PayFr);
            return almanac.get(java.util.Calendar.MONTH) == 11;
        }
    }

    public Boolean getHasATM() {return HasATM;}
    

    public String getOpesina() {return Opesina;}
    public String getProject() {return ProjectTitle;}
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

    
    
    
    @javax.annotation.PostConstruct
    protected void init() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT DISTINCT " +
                        "ctrlno " +
                    "FROM " +
                        "pay.timebook " +
                    "WHERE " +
                        "(opesina = '" + onlineUser.getOpesina() + "') AND " +
                        //"(paid_up = FALSE) AND " +
                        "(acctg_ref IS NULL) " +
                    "ORDER BY " +
                        "ctrlno")) {
            while (rst.next())
                arCtrls.add(new javax.faces.model.SelectItem(rst.getString(1)));

            
        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    } 
    
    public void retrieveJOs() {
    //public void retrieveJOs(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                    /* 1*/"timebook.worker, " +
                    /* 2*/"humane(jobworker.lastname, jobworker.firstname, jobworker.suffix, jobworker.midname), " +
                    /* 3*/"jobworker.jobdesc, " +
                    /* 4*/"timebook.datefr, " +
                    /* 5*/"timebook.dateto, " +
                    /* 6*/"timebook.days, " +
                    /* 7*/"timebook.rate, " +
                    /* 8*/"(timebook.days * timebook.rate), " +
                    /* 9*/"timebook.utime, " +
                    /*10*/"(timebook.rate / 480.0 * timebook.utime)::NUMERIC(18, 2), " +
                    /*11*/"timebook.proyekto, " +
                    /*12*/"projects.projtitle, " +
                    /*13*/"timebook.jobordno, " +
                    /*14*/"offices.office, " +
                    /*15*/"timebook.payfr, " +
                    /*16*/"timebook.payto, " +
                    /*17*/"timebook.officer1, " +
                    /*18*/"timebook.officer2, " +
                    /*19*/"timebook.officer3, " +
                    /*20*/"timebook.designa1, " +
                    /*21*/"timebook.designa2, " +
                    /*22*/"timebook.designa3, " +
                    /*23*/"timebook.bunos, " +
                    /*24*/"timebook.pag_ibig, " +
                    /*25*/"timebook.sssprem, " +
                    /*26*/"jobworker.bank_acct IS NOT NULL " +
                    "FROM " +
                        "psnl.jobworker JOIN pay.timebook " +
                        "ON jobworker.uniqkey = timebook.worker " +
                        "JOIN cbo.projects " +
                        "ON timebook.proyekto = projects.projid " +
                        "JOIN psnl.offices " +
                        "ON timebook.opesina = offices.offcid " +
                    "WHERE " +
                        "(timebook.ctrlno = '" + CtrlNo + "') AND " +
                        "(timebook.paid_up = FALSE) " +
                    "ORDER BY " +
                        "jobworker.lastname, jobworker.firstname, jobworker.midname")) {
            arWages.clear(); TotalWage = 0D; TotalGross = 0D; TotalDeduct = 0D; Counter = 0; TotalBunos = 0D; TotalHDMF = 0D; TotalSSS = 0D;
            while (rst.next()) {
                Counter++;
                arWages.add(new gov.pay.WageField(
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
                        rst.getDouble(24),
                        rst.getDouble(25),
                        Counter,
                        rst.getDouble(23),
                        0D));
                gov.pay.WageField payroll = arWages.get(arWages.size() - 1);
                TotalWage    += payroll.getNetAmount();
                TotalGross   += payroll.getGross();
                TotalDeduct  += payroll.getDeduction();
                TotalBunos   += payroll.getBunos();
                TotalHDMF    += payroll.getPagIbig();
                TotalSSS     += payroll.getSSSPrem();

                ProjectID    = rst.getString(11);
                ProjectTitle = rst.getString(12);
                Opesina      = rst.getString(14);
                PayFr        = rst.getDate  (15);
                PayTo        = rst.getDate  (16);
                Certify1     = rst.getString(17);
                Certify2     = rst.getString(18);
                Certify3     = rst.getString(19);
                Rank1        = rst.getString(20);
                Rank2        = rst.getString(21);
                Rank3        = rst.getString(22);
                //SSSPrem      = rst.getDouble(25);
                HasATM       = rst.getBoolean(26);
            }
            Certification = "CERTIFICATION - This is to certify that the above mentioned job order employees have rendered services in the " + Opesina + " on the date stated.";



        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onDateFrSelect(org.primefaces.event.SelectEvent<?> event) {
        if (DateTo == null)
            Days = 0F;
        else {
            DateFr = (java.util.Date)event.getObject();
            long diffdays = DateTo.getTime() - DateFr.getTime();
            Days = Float.valueOf(String.valueOf((diffdays / ONE_DAY) + 1));
            Adlaw = Days;
        }
    }
    public void onDateToSelect(org.primefaces.event.SelectEvent<?> event) {
        DateTo = (java.util.Date)event.getObject();
        long diffdays = DateTo.getTime() - DateFr.getTime();
        Days = Float.valueOf(String.valueOf((diffdays / ONE_DAY) + 1));
        Adlaw = Days;
    }

    public String onEditJOs(String value) {
        WorkerID = value;
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                    /* 1*/"timebook.jobordno, " +
                    /* 2*/"humane(jobworker.lastname, jobworker.firstname, jobworker.suffix, jobworker.midname), " +
                    /* 3*/"jobworker.jobdesc, " +
                    /* 4*/"timebook.datefr, " +
                    /* 5*/"timebook.dateto, " +
                    /* 6*/"timebook.days, " +
                    /* 7*/"timebook.rate, " +
                    /* 8*/"timebook.utime, " +
                    /* 9*/"(SELECT " +
                               "jobarrange.days - SUM(isitnull(jobcontrol.days, 0::REAL)) " +
                          "FROM " +
                               "cbo.jobarrange LEFT JOIN cbo.jobcontrol " +
                               "ON jobarrange.empkey = jobcontrol.empkey " +
                               "AND jobarrange.jobnum = jobcontrol.jobref " +
                               "AND jobarrange.jobyear = jobcontrol.jobyear " +
                          "WHERE " +
                               "(jobarrange.empkey = timebook.worker) AND " +
                               "(jobarrange.jobnum = timebook.jobordno) AND " +
                               "(jobarrange.jobyear = DATE_PART('YEAR', timebook.dateto)) " +
                          "GROUP BY " +
                               "jobarrange.days) AS available, " + //"timebook.available, " +
                    /*10*/"timebook.bunos, " +
                    /*11*/"timebook.pag_ibig, " +
                    /*12*/"timebook.sssprem " +
                    "FROM " +
                        "pay.timebook JOIN psnl.jobworker " +
                        "ON timebook.worker = jobworker.uniqkey " +
                    "WHERE " +
                        "(timebook.ctrlno = '" + CtrlNo + "') AND " +
                        "(timebook.worker = '" + WorkerID + "')")) {
            if (rst.next()) {
                JobOrder  = rst.getString(1);
                Worker    = rst.getString(2);
                JobDesc   = rst.getString(3);
                DateFr    = rst.getDate(4);
                DateTo    = rst.getDate(5);

                long diffdays = DateTo.getTime() - DateFr.getTime();
                Adlaw = Float.valueOf(String.valueOf((diffdays / ONE_DAY) + 1));

                Days      = rst.getFloat(6);
                Rate      = rst.getFloat(7);
                Minutes   = rst.getShort(8);
                Available = rst.getFloat(9) + Days;
                Bunos     = rst.getDouble(10);
                PagIbig   = rst.getDouble(11);
                SSSPrem   = rst.getDouble(12);
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
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement smt = jdbc.createStatement();) {
//            if (Available < Adlaw) throw new Exception("Working Days exceeded its limit.");
//            if (Adlaw < Days) throw new Exception("Working Days exceeded its limit.");

            if (Adlaw < Days) throw new Exception("Working Days exceeded its limit.");
            if (Available < Days) throw new Exception("Insufficient available working days.");


            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.timebook");
            saver.FieldName("ctrlno",    !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("datefr",    !NUMERIC, gov.enums.Take.UpdateOnly,    new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateFr));
            saver.FieldName("dateto",    !NUMERIC, gov.enums.Take.UpdateOnly,    new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateTo));
            saver.FieldName("opesina",   !NUMERIC, gov.enums.Take.UpdateOnly,    onlineUser.getOpesina());
            saver.FieldName("worker",    !NUMERIC, gov.enums.Take.ConditionOnly, WorkerID);
            saver.FieldName("days",       NUMERIC, gov.enums.Take.UpdateOnly,    Days);
            saver.FieldName("rate",       NUMERIC, gov.enums.Take.UpdateOnly,    Rate);
            saver.FieldName("utime",      NUMERIC, gov.enums.Take.UpdateOnly,    Minutes);
            saver.FieldName("station",   !NUMERIC, gov.enums.Take.UpdateOnly,    onlineUser.getClientIp());// java.net.InetAddress.getLocalHost().getHostName());
            saver.FieldName("userid",    !NUMERIC, gov.enums.Take.UpdateOnly,    onlineUser.getUserOnline());
            saver.FieldName("payfr",     !NUMERIC, gov.enums.Take.UpdateOnly,    new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayFr));
            saver.FieldName("payto",     !NUMERIC, gov.enums.Take.UpdateOnly,    new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayTo));
            saver.FieldName("proyekto",  !NUMERIC, gov.enums.Take.UpdateOnly,    ProjectID);
            saver.FieldName("jobordno",  !NUMERIC, gov.enums.Take.UpdateOnly,    JobOrder);
            saver.FieldName("available",  NUMERIC, gov.enums.Take.UpdateOnly,    Available);
            saver.FieldName("bunos",      NUMERIC, gov.enums.Take.UpdateOnly,    Bunos);
            saver.FieldName("pag_ibig",   NUMERIC, gov.enums.Take.UpdateOnly,    PagIbig);
            saver.FieldName("sssprem",    NUMERIC, gov.enums.Take.UpdateOnly,    SSSPrem);
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

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement()) {
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.timebook");
            saver.FieldName("ctrlno",   !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("officer1", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify1);
            saver.FieldName("officer2", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify2);
            saver.FieldName("officer3", !NUMERIC, gov.enums.Take.UpdateOnly,    Certify3);
            saver.FieldName("designa1", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank1);
            saver.FieldName("designa2", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank2);
            saver.FieldName("designa3", !NUMERIC, gov.enums.Take.UpdateOnly,    Rank3);
            saver.FieldName("certify",  !NUMERIC, gov.enums.Take.UpdateOnly,    Certification.replaceAll("'", "''"));
            _smt.executeUpdate(saver.Perform(gov.enums.Fire.doUpdate));

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Officers updated successfully");

        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String onEraseJOs(String value) {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.timebook");
            saver.FieldName("ctrlno",  !NUMERIC, gov.enums.Take.ConditionOnly, CtrlNo);
            saver.FieldName("worker",  !NUMERIC, gov.enums.Take.ConditionOnly, value);
            saver.FieldName("station", !NUMERIC, gov.enums.Take.ConditionOnly, onlineUser.getClientIp());// java.net.InetAddress.getLocalHost().getHostName());
            int success = _smt.executeUpdate(saver.Perform(gov.enums.Fire.doDelete));
            if (success == 0) {
                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARN", "Invalid workstation. User can only delete on its workstation where it created.");
            } else {
                for (short abc = 0; abc < arWages.size(); abc++) {
                    if (arWages.get(abc).getWorkID().equals(value)) {
                        arWages.remove(abc);
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
