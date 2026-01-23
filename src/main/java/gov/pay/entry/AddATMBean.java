package gov.pay.entry;

/**
 *
 * @author felix
 */
public class AddATMBean implements java.io.Serializable {

    private static final long serialVersionUID = -8514238072386218067L;
    
    private final java.util.List<javax.faces.model.SelectItem> arWorkers   = new java.util.ArrayList<>();
    private final java.util.List<gov.pay.WageField> arFields                   = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arJobOrdNos = new java.util.ArrayList<>();
    private final java.util.List<WorkerAttrib> mapWorker                   = new java.util.ArrayList<>();
    private final java.util.List<Double> arAllotment                       = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arCtrls     = new java.util.ArrayList<>();



    private final boolean NUMERIC = true; //, CONDITION = true;
    private final long ONE_DAY = 1000 * 3600 * 24;
    
//    private Boolean isATM;


    private Boolean isSenior = false;
    private String Certify1, Rank1, Certify2, Rank2, Certify3, Rank3, Certification;
    private String WorkerID, Opesina, OpesinaID;
    private String Jobs, CtrlNo, Project, JobOrder, Proyek2;
    private Short Minutes = 0;
    private Float Rate, Days, Adlaw;
    private Double TotalGross = 0D, Others, TotalWage = 0D, TotalDeduct = 0D, Bunos = 0D, TotalBunos = 0D, PagIbig = 100D, TotalHDMF = 0D, SSSPrem = 360D, TotalSSS = 0D;
    private java.util.Date DateFr, DateTo, PayFr, PayTo;
    

    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}
    
    
    public String getWorkerID() {return WorkerID;}
    public void setWorkerID(String values) {WorkerID = values;}
    
    public String getJobs() {return Jobs;}
    public void setJobs(String value) {Jobs = value;}

    public Float getDays() {return Days;}
    public void setDays(Float value) {Days = value;}

    public Float getRate() {return Rate;}
    public void setRate(Float value) {Rate = value;}

    public Short getMinutes() {return Minutes;}
    public void setMinutes(Short value) {Minutes = value;}

    public Double getOthers() {return Others;}
    public void setOthers(Double value) {Others = value;}

    public Double getPagIbig() {return PagIbig;}
    public void setPagIbig(Double value) {PagIbig = value;}

    public Double getSSSPrem() {return SSSPrem;}
    public void setSSSPrem(Double value) {SSSPrem = value;}

    public String getCtrlNo() {return CtrlNo;}
    public void setCtrlNo(String value) {CtrlNo = value;}

    public String getOpesina() {return Opesina;}

    public String getProject() {return Project;}
    public void setProject(String value) {Project = value;}

    public String getProyek2() {return Proyek2;}
    public void setProyek2(String value) {Proyek2 = value;}

    public java.util.Date getDateFr() {return DateFr;}
    public void setDateFr(java.util.Date value) {DateFr = value;}

    public java.util.Date getDateTo() {return DateTo;}
    public void setDateTo(java.util.Date value) {DateTo = value;}

    public java.util.Date getPayFr() {return PayFr;}
    public void setPayFr(java.util.Date value) {PayFr = value;}

    public java.util.Date getPayTo() {return PayTo;}
    public void setPayTo(java.util.Date value) {PayTo = value;}

    public String getJobOrder() {return JobOrder;}
    public void setJobOrder(String value) {JobOrder = value;}

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
    
    public String getTotalBunos() {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalBunos);}
    public String getTotalWage()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalWage);}
    public String getTotalGross()  {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalGross);}
    public String getTotalDeduct() {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalDeduct);}
    public String getTotalHDMF()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalHDMF);}
    public String getTotalSSS()    {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalSSS);}
    

    public java.util.List<javax.faces.model.SelectItem> getWorkers() {return arWorkers;}
//    public java.util.List<javax.faces.model.SelectItem> getProjects() {return arProjects;}
    public java.util.List<javax.faces.model.SelectItem> getJobOrderNos() {return arJobOrdNos;}
    public java.util.List<gov.pay.WageField> getWages() {return arFields;}
    public java.util.List<javax.faces.model.SelectItem> getControls() {return arCtrls;}


    @javax.annotation.PostConstruct
    protected void init() {
        OpesinaID = onlineUser.getOpesina();
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement()) {
            
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

            try (java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        "timebook.ctrlno, " +
                        "COUNT(jobworker.bank_acct) " +
                    "FROM " +
                        "pay.timebook JOIN psnl.jobworker " +
                        "ON timebook.worker = jobworker.uniqkey " +
                    "WHERE " +
                        "(jobworker.bank_acct IS NOT NULL) AND " +
                        "(timebook.opesina = '" + onlineUser.getOpesina() + "') AND " +
                        "(timebook.acctg_ref IS NULL) AND " +
                        "(timebook.pay_time IS NULL) AND " +
                        "(timebook.entered::DATE >= (NOW()::DATE - (INTERVAL '1 YEAR'))::DATE) " +
                    "GROUP BY " +
                        "timebook.ctrlno " +
                    "ORDER BY " +
                        "timebook.ctrlno")) {
                while (rst.next())
                    arCtrls.add(new javax.faces.model.SelectItem(rst.getString(1)));
            }

            
        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }    

    public void onWorkerChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement();
                java.sql.ResultSet rst = _smt.executeQuery(
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


        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    private void onProjectChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement()) {
            String dateFr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayFr),
                   dateTo   = new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayTo);
            _smt.execute("EXEC cbo.payrollan('" + 
                    OpesinaID +"', '" + 
                    Project + "', '" + 
                    onlineUser.getUserOnline() + "', '" +
                    dateFr + "'::DATE)");
            try (java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        "SUM(jopayroll.allotted) AS allotted, " +
                        "jopayroll.jobnum " +
                    "FROM " +
                        //"cbo.jopayroll " +
                        "cbo.jopayroll INNER JOIN psnl.jobworker ON jopayroll.empkey = jobworker.uniqkey " +
                    "WHERE " +
                        "(jopayroll.userkey = '" + onlineUser.getUserOnline() + "') AND " +
                        //"('" + dateTo + "' BETWEEN jopayroll.frdate AND jopayroll.todate) " + 
                        "(('" + dateFr + "'::DATE BETWEEN jopayroll.frdate AND jopayroll.todate) OR ('" + dateTo + "'::DATE BETWEEN jopayroll.frdate AND jopayroll.todate)) AND " + 
                        "(jobworker.bank_acct IS NOT NULL) " + 
                    "GROUP BY " +
                        "jopayroll.jobnum " +
                    "ORDER BY " +
                        "jopayroll.jobnum")) {
                arJobOrdNos.clear(); arAllotment.clear();
                while (rst.next()) {
                    arAllotment.add(rst.getDouble(1));
                    arJobOrdNos.add(new javax.faces.model.SelectItem(rst.getString(2)));
                }
            }


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onJOChange() {
        String query =
                "SELECT " +
                    "humane, " +
                    "jobnum, " +
                    "frdate, " +
                    "todate, " +
                    "wages, " +
                    "days::SMALLINT - public.dontnull(paiday, 0::SMALLINT), " +
                    "empid " +
                "FROM " +
                    //"cbo.upload" + (isATM ? "atm" : "pay") + "('" +
                    "cbo.uploadatm('" +
                        OpesinaID + "', '" +
                        Project + "', '" +
                        JobOrder + "', '" +
                        onlineUser.getUserOnline() + "') " +
                "WHERE " +
                    "NOT (frdate IS NULL) OR NOT (todate IS NULL) " +
                "ORDER BY " +
                    "humane";
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement();
                java.sql.ResultSet rst = _smt.executeQuery(query)) {
            arWorkers.clear(); mapWorker.clear();
            while (rst.next()) {
                mapWorker.add(new WorkerAttrib(rst.getString(7), rst.getFloat(6)));
                arWorkers.add(new javax.faces.model.SelectItem(rst.getString(7), rst.getString(1)));
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
    public void onDateFrSelect(org.primefaces.event.SelectEvent<?> event) {
        if (DateTo == null)
            Days = 0F;
        else {
            DateFr = (java.util.Date)event.getObject();
            long diffdays = DateTo.getTime() - DateFr.getTime();
            Days = Float.valueOf(String.valueOf((diffdays / ONE_DAY) + 1));
            Adlaw = Days;
            //if (PagIbig.equals(0D)) CheckPagibigPremium(DateFr);
        }
    }
    public void onDateToSelect(org.primefaces.event.SelectEvent<?> event) {
        if (DateFr == null)
            Days = 0F;
        else {
            DateTo = (java.util.Date)event.getObject();
            long diffdays = DateTo.getTime() - DateFr.getTime();
            Days = Float.valueOf(String.valueOf((diffdays / ONE_DAY) + 1));
            Adlaw = Days;
            //if (PagIbig.equals(0D)) CheckPagibigPremium(DateTo);
        }
    }

    public void onCtrlChangeReload() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement();
                java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                    /* 1*/"jobworker.uniqkey, " +
                    /* 2*/"humane(jobworker.lastname, jobworker.firstname, jobworker.suffix, jobworker.midname), " +
                    /* 3*/"jobworker.jobdesc, " +
                    /* 4*/"timebook.datefr, " +
                    /* 5*/"timebook.dateto, " +
                    /* 6*/"timebook.days, " +
                    /* 7*/"timebook.rate, " +
                    /* 8*/"(timebook.days * timebook.rate), " +
                    /* 9*/"timebook.utime, " +
                    /*10*/"(timebook.rate / 480.0 * timebook.utime)::NUMERIC(18, 2), " +
                    /*11*/"timebook.payfr, " +
                    /*12*/"timebook.payto, " +
                    /*13*/"timebook.proyekto, " +
                    /*14*/"projects.projtitle, " +
                    /*15*/"timebook.officer1, " +
                    /*16*/"timebook.officer2, " +
                    /*17*/"timebook.officer3, " +
                    /*18*/"timebook.designa1, " +
                    /*19*/"timebook.designa2, " +
                    /*20*/"timebook.designa3, " +
                    /*21*/"timebook.bunos, " +
                    /*22*/"timebook.pag_ibig, " +
                    /*23*/"timebook.sssprem " +
                    "FROM " +
                        /*"pay.jobworker('" + OpesinaID + "', DATE_PART('YEAR', NOW())::SMALLINT) jobworker JOIN pay.timebook " +
                        "ON timebook.worker = jobworker.uniqkey " +
                        "JOIN cbo.projects " +
                        "ON jobworker.project = projects.projid " +*/
                        "psnl.jobworker JOIN pay.timebook " +
                        "ON jobworker.uniqkey = timebook.worker " +
                        "JOIN cbo.projects " +
                        "ON timebook.proyekto = projects.projid " +
                    "WHERE " +
                        "(timebook.ctrlno = '" + CtrlNo + "') " +
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
                        rst.getDouble(22),
                        rst.getDouble(23),
                        (short)0,
                        rst.getDouble(21),
                        0D));
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();// + payroll.getPagIbig();
                TotalBunos  += payroll.getBunos();
                TotalHDMF   += payroll.getPagIbig();
                TotalSSS    += payroll.getSSSPrem();

                PayFr    = rst.getDate(11);
                PayTo    = rst.getDate(12);
                Project  = rst.getString(13);
                Proyek2  = rst.getString(14);
                Certify1 = rst.getString(15);
                Certify2 = rst.getString(16);
                Certify3 = rst.getString(17);
                Rank1    = rst.getString(18);
                Rank2    = rst.getString(19);
                Rank3    = rst.getString(20);
                //isATM    = rst.getBoolean(22);
            }
            onProjectChange();


        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        Certification = "CERTIFICATION - This is to certify that the above mentioned job order employees have rendered services in the " + Opesina + " on the date stated.";
    }

    public void saveEntry(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
//        String buttonId = event.getComponent().getClientId();
//        System.out.println(buttonId);

        Float avail = 0F; Double allotment = 0D;
        for (short abc = 0; abc < mapWorker.size(); abc++) {
            if (mapWorker.get(abc).getMyID().equals(WorkerID)) {
                avail = mapWorker.get(abc).getAvialable();
                break;
            }
        }
        for (short abc = 0; abc < arAllotment.size(); abc++) {
            if (arJobOrdNos.get(abc).getValue().equals(JobOrder))
                allotment = arAllotment.get(abc);
        }
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement()) {
            if (Adlaw < Days) throw new Exception("Working Days exceeded its limit.");
            if (avail < Days) throw new Exception("Insufficient available working days.");


            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.timebook");
            saver.FieldName("ctrlno",    !NUMERIC, gov.enums.Take.InsertOnly, CtrlNo);
            saver.FieldName("datefr",    !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateFr));
            saver.FieldName("dateto",    !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateTo));
            saver.FieldName("opesina",   !NUMERIC, gov.enums.Take.InsertOnly, OpesinaID);
            saver.FieldName("worker",    !NUMERIC, gov.enums.Take.InsertOnly, WorkerID);
            saver.FieldName("days",       NUMERIC, gov.enums.Take.InsertOnly, Days);
            saver.FieldName("rate",       NUMERIC, gov.enums.Take.InsertOnly, Rate);
            saver.FieldName("utime",      NUMERIC, gov.enums.Take.InsertOnly, Minutes);
            saver.FieldName("station",   !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getClientIp());// java.net.InetAddress.getLocalHost().getHostName());
            saver.FieldName("userid",    !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getUserOnline());
            saver.FieldName("payfr",     !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayFr));
            saver.FieldName("payto",     !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayTo));
            saver.FieldName("proyekto",  !NUMERIC, gov.enums.Take.InsertOnly, Project);
            saver.FieldName("jobordno",  !NUMERIC, gov.enums.Take.InsertOnly, JobOrder);
            saver.FieldName("available",  NUMERIC, gov.enums.Take.InsertOnly, avail);
            saver.FieldName("allotted",   NUMERIC, gov.enums.Take.InsertOnly, allotment);
            saver.FieldName("bunos",      NUMERIC, gov.enums.Take.InsertOnly, Bunos);
            saver.FieldName("pag_ibig",   NUMERIC, gov.enums.Take.InsertOnly, PagIbig);
            saver.FieldName("sssprem",    NUMERIC, gov.enums.Take.InsertOnly, SSSPrem);
            _smt.executeUpdate(saver.Perform(gov.enums.Fire.doInsert));

            updateOfficer(null);
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Successfully saved");

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        onCtrlChangeReload();
    }
    
    private class WorkerAttrib {
        private final String MyID;
        private final Float  Avialable;

        public WorkerAttrib(String id, Float avail) {
            MyID = id; Avialable = avail;
        }

        public String getMyID() {return MyID;}

        public Float getAvialable() {return Avialable;}
        
    }

    public void updateOfficer(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
//        if (event != null) {
//            String buttonId = event.getComponent().getClientId();
//            System.out.println(buttonId);
//        }

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

            if (event != null) msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Officers updated successfully");

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
