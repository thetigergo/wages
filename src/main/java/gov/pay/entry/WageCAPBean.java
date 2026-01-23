package gov.pay.entry;

/**
 *
 * @author felix
 */
public class WageCAPBean implements java.io.Serializable {

    private static final long serialVersionUID = 7211352121865474501L;
    
    private final java.util.List<javax.faces.model.SelectItem> arWorkers   = new java.util.ArrayList<>();
    private final java.util.List<gov.pay.WageField> arFields                   = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arProjects  = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arJobOrdNos = new java.util.ArrayList<>();
    private final java.util.List<WorkerAttrib> mapWorker                   = new java.util.ArrayList<>();
    private final java.util.List<Double> arAllotment                       = new java.util.ArrayList<>();



    private final boolean NUMERIC = true; //, CONDITION = true;
    private final long ONE_DAY = 1000 * 3600 * 24;

    private Boolean isUpdatable = true, disableProject = false, isSenior = false;
    private String Certify1, Rank1, Certify2, Rank2, Certify3, Rank3, Certification;
    private String WorkerID, Opesina, OpesinaID;
    private String Jobs, CtrlNo, Project, JobOrder;
    private Short Minutes = 0;
    private Float Rate, Days, Adlaw;
    private Double TotalGross = 0D, Others, TotalWage = 0D, TotalDeduct = 0D, Bunos = 0D, TotalBunos = 0D, PagIbig = 100D, TotalHDMF = 0D, SSSPrem = 0D, TotalSSS = 0D;
    private java.util.Date DateFr, DateTo, PayFr, PayTo, MaxDate;
    

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

    public boolean isUpdatable() {return isUpdatable;}
    public boolean isDisableProject() {return disableProject;}

    public String getCertification() {return Certification;}
    public void setCertification(String value) {Certification = value;}

    public Double getBunos() {return Bunos;}
    public void setBunos(Double value) {Bunos = value;}

    public java.util.Date getMaxDate() {return MaxDate;}

    
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
    

    public String getTotalBunos()  {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalBunos);}
    public String getTotalWage()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalWage);}
    public String getTotalGross()  {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalGross);}
    public String getTotalDeduct() {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalDeduct);}
    public String getTotalHDMF()   {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalHDMF);}
    public String getTotalSSS()    {return new java.text.DecimalFormat("#,##0.00;(#,##0.00)").format(TotalSSS);}

    
    

    public java.util.List<javax.faces.model.SelectItem> getWorkers() {return arWorkers;}
    public java.util.List<javax.faces.model.SelectItem> getProjects() {return arProjects;}
    public java.util.List<javax.faces.model.SelectItem> getJobOrderNos() {return arJobOrdNos;}
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
                    "jobarrange.project, " +
                    "projects.projtitle, " +
                    "dontnull(cbo.allotted(projects.projid), 0::DOUBLE PRECISION) " +
                "FROM " +
                    "cbo.projects JOIN cbo.jobarrange " +
                    "ON projects.projid = jobarrange.project " +
                    "JOIN cbo.parameters " +
                    "ON projects.atyear = parameters.present " +
                "WHERE " +
                    "(jobarrange.offcloc = '" + OpesinaID + "') " +
                "GROUP BY " +
                    "jobarrange.project, " +
                    "projects.projtitle, " +
                    "projects.projid " +
                "ORDER BY " +
                    "projects.projtitle ";
            try (java.sql.ResultSet tbl = _smt.executeQuery(query)) {
                    /*"SELECT DISTINCT " +
                        "jobworker.project, " +
                        "projects.projtitle " +
                    "FROM " +
                        "pay.jobworker('" + OpesinaID + "', DATE_PART('YEAR', NOW())::SMALLINT) jobworker JOIN cbo.projects " +
                        "ON jobworker.project = projects.projid " +
                    "ORDER BY " +
                        "projects.projtitle");*/
                while (tbl.next())
                    arProjects.add(new javax.faces.model.SelectItem(tbl.getString(1), tbl.getString(2)));
            }
            //projectOnChange();
            
        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        Certification = "CERTIFICATION - This is to certify that the above mentioned job order employees have rendered services in the " + Opesina + " on the date stated.";
    }    

//    public void checkPermissions(javax.faces.event.ComponentSystemEvent event) {
//        javax.faces.context.FacesContext fc = javax.faces.context.FacesContext.getCurrentInstance();
//        //javax.servlet.http.HttpSession httpSession = (javax.servlet.http.HttpSession)(fc.getExternalContext().getSession(false)); 
//        //String cid = (String) httpSession.getAttribute(AttributeName.ADMINISTRATOR_CLIENT_LOGIN_ID);
//        //if( cid == null){
//        if(onlineUser == null) {
//            javax.faces.application.ConfigurableNavigationHandler handler = (javax.faces.application.ConfigurableNavigationHandler)fc.getApplication().getNavigationHandler();
//            handler.performNavigation("/timebook/");
//        }
//    }
    
    public void onWorkerChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement();
            java.sql.ResultSet rst = smt.executeQuery(
                    "SELECT " +
                        "jobdesc, " +
                        "payrate, " +
                        "DATE_PART('YEAR', AGE(NOW(), jobworker.birthday))::SMALLINT " +
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
    
    public void onProjectChange() {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            String dateFrom = new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayFr),
                   dateTo   = new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayTo);
            _smt.execute("EXEC cbo.payrollan('" + 
                    OpesinaID +"', '" + 
                    Project + "', '" + 
                    onlineUser.getUserOnline() + "', '" +
                    dateFrom + "'::DATE)");
            try (java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                        "SUM(jopayroll.allotted) AS allotted, " +
                        "jopayroll.jobnum " +
                    "FROM " +
                        //"cbo.jopayroll " +
                        "cbo.jopayroll INNER JOIN psnl.jobworker ON jopayroll.empkey = jobworker.uniqkey " +
                    "WHERE " +
                        "(jopayroll.userkey = '" + onlineUser.getUserOnline() + "') AND " +
                        "('" + dateTo + "' BETWEEN jopayroll.frdate AND jopayroll.todate) AND " + 
                        "(jobworker.bank_acct IS NULL) " + 
                    "GROUP BY " +
                        "jopayroll.jobnum " +
                    "ORDER BY " +
                        "jopayroll.jobnum")) {
                arJobOrdNos.clear(); arAllotment.clear();
                while (rst.next()) {
                    //if (JobOrder == null) JobOrder = rst.getString(2);
                    arAllotment.add(rst.getDouble(1));
                    arJobOrdNos.add(new javax.faces.model.SelectItem(rst.getString(2)));
                }
            }
            disableProject = true;


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onJOChange() {
        //String datefr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayFr),
        //       dateto = new java.text.SimpleDateFormat("yyyy-MM-dd").format(PayTo);
        String query =
                "SELECT " +
                    "humane, " +
                    "jobnum, " +
                    "frdate, " +
                    "todate, " +
                    "wages, " +
                    "days::SMALLINT - public.dontnull(paiday, 0::SMALLINT), " +
                    "empid, " +
                    "edad " +
                "FROM " +
                    "cbo.uploadpay('" +
                        OpesinaID + "', '" +
                        Project + "', '" +
                        JobOrder + "', '" +
                        onlineUser.getUserOnline() + "') " +
                        //"'" + datefr + "', " +
                        //"'" + dateto + "') " +
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
                mapWorker.add(new WorkerAttrib(rst.getString(7), rst.getFloat(6), rst.getDate(3), rst.getDate(4), rst.getShort(8)));
                arWorkers.add(new javax.faces.model.SelectItem(rst.getString(7), rst.getString(1)));
            }


        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onPayFrSelect(org.primefaces.event.SelectEvent<?> event) {
        PayFr = (java.util.Date)event.getObject();
            
        java.util.Calendar cals = java.util.Calendar.getInstance();
        cals.setTime(PayFr);
        cals.set(cals.get(java.util.Calendar.YEAR), cals.get(java.util.Calendar.MONTH) + 1, 0);

        MaxDate = new java.util.Date();
        MaxDate.setTime(cals.getTimeInMillis());
    }
    public void onPayToSelect(org.primefaces.event.SelectEvent<?> event) {
        PayTo = (java.util.Date)event.getObject();
    }    
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
    
    private void ReLoad() {
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
                        /*11*/"timebook.bunos, " +
                        /*12*/"timebook.pag_ibig, " +
                        /*13*/"timebook.sssprem " +
                    "FROM " +
                        /*"pay.jobworker('" + OpesinaID + "', DATE_PART('YEAR', NOW())::SMALLINT) jobworker JOIN pay.timebook " +
                        "ON timebook.worker = jobworker.uniqkey " +*/
                        "psnl.jobworker JOIN pay.timebook " +
                        "ON jobworker.uniqkey = timebook.worker " +
                        /*"JOIN cbo.projects " +
                        "ON timebook.proyekto = projects.projid " +
                        "JOIN psnl.offices " +
                        "ON timebook.opesina = offices.offcid " +*/
                    "WHERE " +
                        "(timebook.ctrlno = '" + CtrlNo + "') " +
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
                        0D));
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();// + payroll.getPagIbig();
                TotalBunos  += payroll.getBunos();
                TotalHDMF   += payroll.getPagIbig();
                TotalSSS    += payroll.getSSSPrem();
            }

        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void saveEntry(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
//        String buttonId = event.getComponent().getClientId();
//        System.out.println(buttonId);

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement()) {
            
            Float avail = 0F; Double allotment = 0D;
            java.util.Date gikan = new java.util.Date(), ngadto = new java.util.Date();
            
            for (short abc = 0; abc < mapWorker.size(); abc++) {
                if (mapWorker.get(abc).getMyID().equals(WorkerID)) {
                    avail = mapWorker.get(abc).getAvialable();
                    gikan.setTime(mapWorker.get(abc).getFrDate().getTime());
                    ngadto.setTime(mapWorker.get(abc).getToDate().getTime());
                    break;
                }
            }
            for (short abc = 0; abc < arAllotment.size(); abc++) {
                if (arJobOrdNos.get(abc).getValue().equals(JobOrder))
                    allotment = arAllotment.get(abc);
            }
            
            
            if (Adlaw < Days) throw new Exception("Working Days exceeded its limit.");
            if (avail < Days) throw new Exception("Insufficient available working days.");
            
            if (DateFr.getTime() >= gikan.getTime() & DateTo.getTime() <= ngadto.getTime())
                System.out.println("valid date cover.");
            else
                throw new Exception("HOY! Tan-awa ang date cover sa imo JO ayaw'g pataka'g tuslok!");
                //throw new Exception("Invalid date cover in J.O. # " + JobOrder + ".");
            
            
            //if (onlineUser.getTemporary() == null) {
            if (CtrlNo == null) {
                //java.sql.ResultSet rst = smt.executeQuery("SELECT NEXTVAL('pay.timebook_seq'), DATE_PART('YEAR', NOW())");
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(DateFr);
                int year = calendar.get(java.util.Calendar.YEAR);
                try (java.sql.ResultSet rst = _smt.executeQuery("SELECT pay.next_seq(" + year + ");");
                        java.sql.PreparedStatement psmt = jdbc.prepareStatement("INSERT INTO pay.suholan(ctrlno, payfr, payto, userid, typed) VALUES (?, ?, ?, ?, ?);")) {
                    if (rst.next()) {
                        String temp = new java.text.DecimalFormat("000000").format(rst.getInt(1));
                        //if (year == rst.getInt(2))
                            CtrlNo = year + "~" + temp;
                        //else {
                        //    temp = "CREATE SEQUENCE pay.timebook_seq" + year + " INCREMENT 1 MINVALUE 1 MAXVALUE 32767 START 1 CACHE 1 CYCLE;";
                        //    smt.executeUpdate(temp);
                        //}
                    }
                    psmt.setString(1, CtrlNo);
                    psmt.setDate  (2, new java.sql.Date(PayFr.getTime()));
                    psmt.setDate  (3, new java.sql.Date(PayTo.getTime()));
                    psmt.setString(4, onlineUser.getUserOnline());
                    psmt.setString(5, "C/A");
                    psmt.executeUpdate();
                }
            } /*else 
                CtrlNo = onlineUser.getTemporary();*/

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
        ReLoad();
    }
    
    private class WorkerAttrib {
        private final String MyID;
        private final Float  Avialable;
        private final java.util.Date frDate = new java.util.Date(), toDate = new java.util.Date();
        private final Boolean SeniorNa;

        public WorkerAttrib(String id, Float avail, java.util.Date datefr, java.util.Date dateto, Short edad) {
            MyID = id;Avialable = avail;frDate.setTime(datefr.getTime());toDate.setTime(dateto.getTime()); SeniorNa = edad == 60;
        }

        public String getMyID() {return MyID;}

        public Float getAvialable() {return Avialable;}

        public java.util.Date getFrDate() {return frDate;}

        public java.util.Date getToDate() {return toDate;}
        
        public Boolean getSeniorNa() {return SeniorNa;}
    }

    public void updateOfficer(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
//        if (event != null) {
//            String buttonId = event.getComponent().getClientId();
//            System.out.println(buttonId);
//        }

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement()) {
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.timebook");
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
