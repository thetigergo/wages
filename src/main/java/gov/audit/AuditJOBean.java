package gov.audit;

/**
 *
 * @author felix
 */
public class AuditJOBean implements java.io.Serializable {

    private static final long serialVersionUID = 3954102898389269663L;
    
    private final java.util.List<gov.pay.WageField> arFields = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arCtrls = new java.util.ArrayList<>();
    
    private String ProjectTitle, Opesina, Certify1, Certify2, Certify3, Rank1, Rank2, Rank3, CtrlNo, PayDate, Remark, Reference, OfficeID;
    private Double TotalWage = 0D, TotalGross = 0D, TotalDeduct = 0D, TotalBunos = 0D, TotalHDMF = 0D, TotalSSS = 0D, TotalTAX = 0D;
    private Short Counter = 0;
    private Boolean SwitchOff = false;


    private final boolean NUMERIC = true; //, CONDITION = true;
    private final String /*SECTIONED = "receiving", */NEXTSECTION = "carding";


    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}
    
    
//    public String getProjectID() {return ProjectID;}
    public String getProject() {return ProjectTitle;}
    public String getOpesina() {return Opesina;}
    public String getCertify1() {return Certify1;}
    public String getCertify2() {return Certify2;}
    public String getCertify3() {return Certify3;}
    public String getRank1() {return Rank1;}
    public String getRank2() {return Rank2;}
    public String getRank3() {return Rank3;}
    public String getPayDate() {return PayDate;}
    
    public Boolean getSwitchOff() {return SwitchOff;}
    
    public Double getTotalWage() {return TotalWage;}
    public Double getTotalGross() {return TotalGross;}
    public Double getTotalDeduct() {return TotalDeduct;}
    public Double getTotalHdmf() {return TotalHDMF;}
    public Double getTotalSSS() {return TotalSSS;}
    public Double getTotalBunos() {return TotalBunos;}
    public Double getTotalTAX() {return TotalTAX;}

    
    public String getCtrlNo() {return CtrlNo;}
    public void setCtrlNo(String value) {CtrlNo = value;}

    public String getReference() {return Reference;}
    public void setReference(String value) {Reference = value;}
    
    public String getRemark() {return Remark;}
    public void setRemark(String value) {Remark = value;}

    
    public java.util.List<gov.pay.WageField> getWages() {return arFields;}
    public java.util.List<javax.faces.model.SelectItem> getControls() {return arCtrls;}

    @javax.annotation.PostConstruct
    protected void init() {
        
    }
    
    public void kuhaaALOBS(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection adbc = new gov.dbase.PgSQLConn("accounting");
                org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _zmt = jdbc.createStatement();
                java.sql.Statement _smt = adbc.createStatement()) {
            String[] acctref = Reference.split("-"); String cboNo = "", alobsNo = "";
            try (java.sql.ResultSet rst = _smt.executeQuery("SELECT budget_no FROM doc.main_data WHERE (ref_no_year = " + acctref[0] + ") AND (ref_no = " + acctref[1] + ");")) {
                if (rst.next()) cboNo = rst.getString(1);
            }
            try (java.sql.ResultSet rst = _smt.executeQuery("SELECT alobs FROM doc.get_alobs(" + acctref[0] + "::smallint, '" + cboNo + "');")) {
                if (rst.next()) alobsNo = rst.getString(1);
            }
            try (java.sql.ResultSet rst = _zmt.executeQuery("SELECT DISTINCT ctrlno FROM pay.timebook WHERE (alobs = '" + alobsNo + "') AND (acctg_ref IS NULL);")) {
                arCtrls.clear();
                while (rst.next()) arCtrls.add(new javax.faces.model.SelectItem(rst.getString(1)));
            }


        } catch (java.sql.SQLException ex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void retrieveJOs() {//javax.faces.event.ActionEvent event
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
                   //*23*/"(NOT timebook.pay_ref IS NULL), " +
                    /*23*/"timebook.pay_ref, " +
                    /*24*/"(timebook.acctg_ref IS NULL), " +
                    /*25*/"timebook.bunos, " +
                    /*26*/"offices.offcid, " +
                    /*27*/"timebook.pag_ibig, " +
                    /*28*/"timebook.sssprem, " +
                    /*29*/"timebook.withtax " +
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
                        //"(NOT timebook.pay_ref IS NULL) AND " +
                        //"(timebook.acctg_ref IS NULL) " +
                    "ORDER BY " +
                        "jobworker.lastname, jobworker.firstname, jobworker.midname")) {
            
            TotalWage = 0D; arFields.clear(); TotalGross = 0D; TotalDeduct = 0D; Counter = 0; TotalHDMF = 0D; TotalBunos = 0D; TotalSSS = 0D;
            while (rst.next()) {
                if (rst.getObject(23) == null) {
                //if (!rst.getBoolean(23)) {
                    Counter = -1;
                    continue;
                } else if (!rst.getBoolean(24)) {
                    Counter = -2;
                    continue;
                }
                Counter++;
                arFields.add(new gov.pay.WageField(
                        rst.getString(1),       //workerid
                        rst.getString(2),       //humane
                        rst.getString(3),         //jobdesc
                        rst.getDate  (4),         //datefr
                        rst.getDate  (5),         //dateto
                        rst.getFloat (6),         //days
                        rst.getFloat (7),         //rate
                        rst.getDouble(8),        //basic
                        rst.getShort (9),        //utime
                        rst.getDouble(10),      //deduction
                        rst.getDouble(27),      //pag-ibig
                        rst.getDouble(28),      //SSS Prem
                        Counter,
                        rst.getDouble(25),       //bunos
                        rst.getDouble(29)));      //withtax
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();
                TotalBunos  += payroll.getBunos();
                TotalHDMF   += payroll.getPagIbig();
                TotalSSS    += payroll.getSSSPrem();
                TotalTAX    += payroll.getTaxHeld();

                ProjectTitle = rst.getString(12);
                Opesina      = rst.getString(14);
                PayDate      = DateRange(rst.getDate(15), rst.getDate(16));
                Certify1     = rst.getString(17);
                Certify2     = rst.getString(18);
                Certify3     = rst.getString(19);
                Rank1        = rst.getString(20);
                Rank2        = rst.getString(21);
                Rank3        = rst.getString(22);
                OfficeID     = rst.getString(26);
            }
            if (null != Counter)
                switch (Counter) {
                case -1:
                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARN", "Not yet approved from the Budget.");
                    break;
                case -2:
                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARN", "Already posted on Carding.");
                    break;
                case 0:
                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARN", "Control Number does not Exist.");
                    break;
                default:
            }
            

//            String[] control = CtrlNo.split("~");
//            try (org.postgresql.core.BaseConnection zdbc = new dbase.PgSQLConn("accounting");
//                    java.sql.Statement smt = zdbc.createStatement();
//                    java.sql.ResultSet tbl = smt.executeQuery("SELECT ref_no_year, ref_no_var FROM doc.main_data WHERE (ref_no_year = " + control[0] + ") AND (budget_no = '" + Reference + "')")) {
//                if (tbl.next()) Reference = tbl.getShort(1) + "-" + tbl.getString(2);
//            }
            SwitchOff = false;


        } catch (java.sql.SQLException ex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

//    public void retrieveJO2(javax.faces.event.ActionEvent event) {
//        javax.faces.application.FacesMessage msg = null;
//        java.sql.Connection jdbc = null;
//        try {
//            jdbc = new dbase.PgSQLConn("accounting");
//            java.sql.Statement smt = jdbc.createStatement();
//            java.sql.ResultSet rst = smt.executeQuery(
//                    "SELECT " +
//                        "budget_no " +
//                    "FROM " +
//                        "doc.main_data " +
//                    "WHERE " +
//                        "(ref_no_year || '-' || ref_no_var = '" + Reference + "')");
//            TotalWage = 0D; arFields.clear(); TotalGross = 0D; TotalDeduct = 0D; Counter = 0;
//            if (rst.next()) {
//                java.sql.ResultSet tbl = smt.executeQuery("SELECT psnl.ctrlno FROM doc.joborder('" + rst.getString(1) + "')");
//                if (!tbl.next())
//                    msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARN", "Not yet approved from the Budget.");
//
//            rst.close();
//
//            if (Counter == -1)
//                
//            else if (Counter == -2)
//                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARN", "Already posted on Carding.");
//            else if (Counter == 0)
//                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_WARN, "WARN", "Control Number does not Exist.");
//            
//            SwitchOff = false;
//
//
//        } catch (Exception sex) {
//            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
//        } finally {
//            if (jdbc != null) try {
//                jdbc.close();
//            } catch (Exception ef) {
//                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ef.getMessage());
//            }
//            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }

    private String DateRange(java.util.Date dateFr, java.util.Date dateTo) {
        String strFr = new java.text.SimpleDateFormat("MM/dd/yyyy").format(dateFr),
               strTo = new java.text.SimpleDateFormat("MM/dd/yyyy").format(dateTo);

        java.util.Calendar calFr = java.util.Calendar.getInstance(),
                           calTo = java.util.Calendar.getInstance();
        
        calFr.setTime(dateFr); calTo.setTime(dateTo);
        
        if (strFr.contentEquals(strTo)) {
            return new java.text.SimpleDateFormat("MMMM d, yyyy").format(dateTo);
        } else if (calFr.get(java.util.Calendar.MONTH) == calTo.get(java.util.Calendar.MONTH) & calFr.get(java.util.Calendar.YEAR) == calTo.get(java.util.Calendar.YEAR)) {
            strFr = new java.text.SimpleDateFormat("MMMM d ").format(dateFr);
            strTo = new java.text.SimpleDateFormat("- d, yyyy").format(dateTo);
            return strFr + strTo;

        } else if (calFr.get(java.util.Calendar.MONTH) != calTo.get(java.util.Calendar.MONTH) & calFr.get(java.util.Calendar.YEAR) == calTo.get(java.util.Calendar.YEAR)) {
            strFr = new java.text.SimpleDateFormat("MMMM d - ").format(dateFr);
            strTo = new java.text.SimpleDateFormat("MMMM d, yyyy").format(dateTo);
            return strFr + strTo;
        } else {
            strFr = new java.text.SimpleDateFormat("MMMM d, yyyy").format(dateFr);
            strTo = new java.text.SimpleDateFormat("MMMM d, yyyy").format(dateTo);
            return strFr + " - " + strTo;
        }
    }

    public void approveRecord(javax.faces.event.ActionEvent ae) {
        javax.faces.application.FacesMessage msg = null;
        boolean opened = false;
        try (org.postgresql.core.BaseConnection zdbc = new gov.dbase.PgSQLConn("accounting");
                java.sql.Statement smt = zdbc.createStatement()) {

            String anios = Reference.split("-")[0],
                   refno = Reference.split("-")[1],
                   details;

            try (java.sql.ResultSet rst = smt.executeQuery(
                    "SELECT " +
                        "main_data.details, " +
                        "approved.up_status = 0 " +
                    "FROM " +
                        "doc.main_data JOIN doc.approved " +
                        "ON main_data.ref_no_year = approved.ref_no_year " +
                        "AND main_data.ref_no = approved.ref_no " +
                    "WHERE " +
                        "(main_data.ref_no_year = " + anios + ") AND " +
                        "(main_data.ref_no = " + refno + ") AND " +
                        "(approved.section = 'receiving')")) {
                if (rst.next()) {
                    details = rst.getString(1);
                    opened = rst.getBoolean(2);
                } else
                    throw new Exception("Invalid Acctg Ref. #");
            }
            
//            try (java.sql.ResultSet rst = smt.executeQuery(
//                    "SELECT " +
//                        "main_data.details, " +
//                        "approved.up_status = 0 " +
//                    "FROM " +
//                        "doc.main_data JOIN doc.approved " +
//                        "ON main_data.ref_no_year = approved.ref_no_year " +
//                        "AND main_data.ref_no = approved.ref_no " +
//                    "WHERE " +
//                        "(main_data.ref_no_year = " + anios + ") AND " +
//                        "(main_data.ref_no = " + refno + ") AND " +
//                        "(approved.up_status = 1) AND " +
//                        "(approved.section = 'carding')")) {
//                if (rst.next()) {
//                    rst.close();
//                    throw new Exception("Acctg Ref. # already used.");
//                }
//            }

            if (opened) {
                smt.executeUpdate("UPDATE doc.approved SET up_status = 1 WHERE (ref_no_year = " + anios + ") AND (ref_no = " + refno + ") AND (up_status = 0) AND (section = 'receiving')");
                gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("doc.approved");
                saver.FieldName("ref_no_year",  NUMERIC, gov.enums.Take.InsertOnly, anios);
                saver.FieldName("ref_no",       NUMERIC, gov.enums.Take.InsertOnly, refno);
                saver.FieldName("users",       !NUMERIC, gov.enums.Take.InsertOnly, "ADMIN");
                saver.FieldName("section",     !NUMERIC, gov.enums.Take.InsertOnly, NEXTSECTION);
                saver.FieldName("amount",       NUMERIC, gov.enums.Take.InsertOnly, 0);
                int success = smt.executeUpdate(saver.Perform(gov.enums.Fire.doInsert));
                if (success != 0) smt.executeUpdate("UPDATE doc.main_data SET returned = false WHERE (ref_no_year = " + anios + ") AND (ref_no = " + refno + ")");
            }


/******************************* CODING PARA SA PAG POST SA CARDING *******************************/
            try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                    java.sql.Statement jsmt = jdbc.createStatement()) {
                for (gov.pay.WageField wages : arFields) {
                    gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("cao.jobcarding");
                    saver.FieldName("keyid",      !NUMERIC, gov.enums.Take.InsertOnly, wages.getWorkID());
                    saver.FieldName("trndates",   !NUMERIC, gov.enums.Take.InsertOnly, DateRange(wages.getDateFr(), wages.getDateTo()));
                    saver.FieldName("reference",  !NUMERIC, gov.enums.Take.InsertOnly, Reference);
                    saver.FieldName("particular", !NUMERIC, gov.enums.Take.InsertOnly, details.replaceAll("'", "''") + (wages.getBunos() > 0 ? " P" + new java.text.DecimalFormat("#,##0.00").format(wages.getBunos()) : ""));
                    saver.FieldName("workdays",    NUMERIC, gov.enums.Take.InsertOnly, wages.getDays());
                    saver.FieldName("amount",      NUMERIC, gov.enums.Take.InsertOnly, wages.getGross() + wages.getBunos());
                    if (wages.getDeduction() != 0) {
                        saver.FieldName("text1",      !NUMERIC, gov.enums.Take.InsertOnly, "U. T.");
                        saver.FieldName("value1",      NUMERIC, gov.enums.Take.InsertOnly, wages.getDeduction());
                    }
                    saver.FieldName("inputby",    !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getUserOnline());
                    saver.FieldName("ctrlno",     !NUMERIC, gov.enums.Take.InsertOnly, CtrlNo);
                    saver.FieldName("offcid",     !NUMERIC, gov.enums.Take.InsertOnly, OfficeID);
                    if (wages.getPagIbig() != 0D) {
                        saver.FieldName("text2",      !NUMERIC, gov.enums.Take.InsertOnly, "PAG-IBIG Premium");
                        saver.FieldName("value2",      NUMERIC, gov.enums.Take.InsertOnly, wages.getPagIbig());
                    }
                    if (wages.getSSSPrem() != 0D) {
                        saver.FieldName("text3",      !NUMERIC, gov.enums.Take.InsertOnly, "SSS Premium");
                        saver.FieldName("value3",      NUMERIC, gov.enums.Take.InsertOnly, wages.getSSSPrem());
                    }
                    if (wages.getSSSPrem() != 0D) {
                        saver.FieldName("text4",      !NUMERIC, gov.enums.Take.InsertOnly, "W/TAX");
                        saver.FieldName("value4",      NUMERIC, gov.enums.Take.InsertOnly, wages.getTaxHeld());
                    }
                    saver.FieldName("ref_no2",     NUMERIC, gov.enums.Take.InsertOnly, (wages.getBunos() > 0 ? 1 : 0));
                    jsmt.executeUpdate(saver.Perform(gov.enums.Fire.doInsert));
                }
            }

            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Records posted to Carding and approved in Tracer.");
            SwitchOff = true;

        } catch (Exception ex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void returnRecord(javax.faces.event.ActionEvent ae) {
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn("accounting");
                java.sql.Statement smt = jdbc.createStatement()) {
            
            String anios = Reference.split("-")[0],
                   refno = Reference.split("-")[1];

            try (java.sql.ResultSet rst = smt.executeQuery("SELECT 0 FROM doc.main_data WHERE (ref_no_year = " + anios + ") AND (ref_no = " + refno + ")")) {
                if (!rst.next()) throw new Exception("Invalid Acctg Ref. #");
            }

            jdbc.setAutoCommit(false);
            gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("doc.returned");
            saver.FieldName("ref_no_year",  NUMERIC, gov.enums.Take.InsertOnly, anios);
            saver.FieldName("ref_no",       NUMERIC, gov.enums.Take.InsertOnly, refno);
            saver.FieldName("remarks",     !NUMERIC, gov.enums.Take.InsertOnly, Remark);
            saver.FieldName("users",       !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getUserOnline());
            saver.FieldName("section",     !NUMERIC, gov.enums.Take.InsertOnly, NEXTSECTION);
            int success = smt.executeUpdate(saver.Perform(gov.enums.Fire.doInsert));
            if (success != 0) smt.executeUpdate("UPDATE doc.main_data SET returned = true WHERE (ref_no_year = " + anios + ") AND (ref_no = " + refno + ")");
            jdbc.commit();
            
            SwitchOff = true;


        } catch (Exception ex) {
            javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage()));
        } finally {

        }
    }
}
