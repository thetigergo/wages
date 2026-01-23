package gov.audit;

/**
 *
 * @author felix
 */
public class AuditEndoBean implements java.io.Serializable {

    private static final long serialVersionUID = 5425380438129076935L;
    
    private final java.util.List<gov.pay.WageField> arFields = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arCtrls = new java.util.ArrayList<>();
    
    private String Opesina, Certify1, Certify2, Certify3, Rank1, Rank2, Rank3, CtrlNo, PayDate, Remark, Reference, OfficeID;
    private Double TotalWage = 0D, TotalGross = 0D, TotalDeduct = 0D, TotalBunos = 0D, TotalHdmf = 0D, TotalSSS = 0D, TotalTAX = 0D;
    private Short Counter = 0;
    private Boolean SwitchOff = false;
    //private java.util.Date DateFr, DateTo, PayFr, PayTo;


    private final boolean NUMERIC = true; //, CONDITION = true;
    private final String /*SECTIONED = "receiving", */NEXTSECTION = "carding";


    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}
    
    
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
    public Double getTotalHdmf() {return TotalHdmf;}
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
                if (rst.next()) 
                    alobsNo = rst.getString(1);
                else {
                    int years = Integer.parseInt(acctref[0], 10) - 1;
                    try (java.sql.PreparedStatement psmt = adbc.prepareStatement("SELECT alobs FROM doc.get_alobs(" + years + "::smallint, '" + cboNo + "');");
                            java.sql.ResultSet tbl = psmt.executeQuery()) {
                        if (tbl.next()) alobsNo = tbl.getString(1);
                    }
                }
            }
            try (java.sql.ResultSet rst = _zmt.executeQuery("SELECT DISTINCT ctrlno FROM pay.laborpaid WHERE (alobs = '" + alobsNo + "') AND (acctg_ref IS NULL);")) {
                arCtrls.clear();
                while (rst.next()) arCtrls.add(new javax.faces.model.SelectItem(rst.getString(1)));
            }


        } catch (java.sql.SQLException ex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void accessContract() {//javax.faces.event.ActionEvent event
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
                    /*19*/"(laborpaid.acctg_ref IS NULL), " +
                    /*20*/"laborpaid.bunos, " +
                    /*21*/"laborpaid.pag_ibig, " +
                    /*22*/"laborpaid.sssprem, " +
                    /*23*/"offices.offcid, " +
                    /*24*/"offices.office, " +
                    /*25*/"laborpaid.withtax " +
                    "FROM " +
                        "psnl.jobworker INNER JOIN pay.laborpaid " +
                        "ON jobworker.uniqkey = laborpaid.worker " +
                        "INNER JOIN psnl.offices " +
                        "ON laborpaid.opesina = offices.offcid " +
                    "WHERE " +
                        "(laborpaid.ctrlno = '" + CtrlNo + "') " +
                    "ORDER BY " +
                        "jobworker.lastname, jobworker.firstname, jobworker.midname")) {
            
            TotalWage = 0D; arFields.clear(); TotalGross = 0D; TotalDeduct = 0D; Counter = 0; TotalHdmf = 0D; TotalBunos = 0D; TotalSSS = 0D; TotalTAX = 0D;
            while (rst.next()) {
                /*if (!rst.getBoolean(19)) {
                    Counter = -1;
                    continue;
                }*/
                Counter++;
                arFields.add(new gov.pay.WageField(
                        rst.getString(1),       //workerid
                        rst.getString(2),       //humane
                        rst.getString(3),       //jobdesc
                        rst.getDate  (4),       //datefr
                        rst.getDate  (5),       //dateto
                        rst.getFloat (6),       //udays
                        rst.getFloat (7),       //rate
                        rst.getDouble(8),       //basic
                        rst.getShort (9),       //utime
                        rst.getDouble(10),      //deduction
                        rst.getDouble(21),      //pag-ibig
                        rst.getDouble(22),      //SSS Prem
                        Counter,
                        rst.getDouble(20),      //bunos
                        rst.getDouble(25)));    //withtax
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction() + payroll.getPagIbig() + payroll.getSSSPrem() + payroll.getTaxHeld();
                TotalBunos  += payroll.getBunos();
                TotalHdmf   += payroll.getPagIbig();
                TotalSSS    += payroll.getSSSPrem();
                TotalTAX    += payroll.getTaxHeld();

                //PayFr        = rst.getDate(11);
                //PayTo        = rst.getDate(12);
                Certify1     = rst.getString(13);
                Certify2     = rst.getString(14);
                Certify3     = rst.getString(15);
                Rank1        = rst.getString(16);
                Rank2        = rst.getString(17);
                Rank3        = rst.getString(18);
                OfficeID     = rst.getString(23);
                Opesina      = rst.getString(24);
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
                    break;
            }

            SwitchOff = false;


        } catch (java.sql.SQLException ex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

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
        boolean already = false;
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
                    already = rst.getBoolean(2);
                } else
                    throw new Exception("Invalid Acctg Ref. #");
            }
            
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
                        "(approved.up_status = 1) AND " +
                        "(approved.section = 'carding')")) {
                if (rst.next()) {
                    throw new Exception("Acctg Ref. # already used.");
                }
            }

            if (already) {
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
            
/*
    COL Sales Officer - Jenny
    Skype ID: colsales101
    Viber #: +63947-281-8919
    IMO #: +63995-149-5674
*/
/******************************* CODING PARA SA PAG POST SA CARDING *******************************/
            try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                    java.sql.Statement jsmt = jdbc.createStatement()) {
                for (gov.pay.WageField laborpaid : arFields) {
                    gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("cao.jobcarding");
                    saver.FieldName("keyid",      !NUMERIC, gov.enums.Take.InsertOnly, laborpaid.getWorkID());
                    saver.FieldName("trndates",   !NUMERIC, gov.enums.Take.InsertOnly, DateRange(laborpaid.getDateFr(), laborpaid.getDateTo()));
                    saver.FieldName("reference",  !NUMERIC, gov.enums.Take.InsertOnly, Reference);
                    saver.FieldName("particular", !NUMERIC, gov.enums.Take.InsertOnly, details.replaceAll("'", "''") + (laborpaid.getBunos() > 0 ? " P" + new java.text.DecimalFormat("#,##0.00").format(laborpaid.getBunos()) : ""));
                    saver.FieldName("workdays",    NUMERIC, gov.enums.Take.InsertOnly, laborpaid.getDays());
                    saver.FieldName("amount",      NUMERIC, gov.enums.Take.InsertOnly, laborpaid.getGross() + laborpaid.getBunos());
                    if (laborpaid.getDeduction() != 0) {
                        saver.FieldName("text1",      !NUMERIC, gov.enums.Take.InsertOnly, "U. T.");
                        saver.FieldName("value1",      NUMERIC, gov.enums.Take.InsertOnly, laborpaid.getDeduction());
                    }
                    saver.FieldName("inputby",    !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getUserOnline());
                    saver.FieldName("ctrlno",     !NUMERIC, gov.enums.Take.InsertOnly, CtrlNo);
                    saver.FieldName("offcid",     !NUMERIC, gov.enums.Take.InsertOnly, OfficeID);
                    if (laborpaid.getPagIbig() != 0D) {
                        saver.FieldName("text2",      !NUMERIC, gov.enums.Take.InsertOnly, "PAG-IBIG Premium");
                        saver.FieldName("value2",      NUMERIC, gov.enums.Take.InsertOnly, laborpaid.getPagIbig());
                    }
                    if (laborpaid.getSSSPrem() != 0D) {
                        saver.FieldName("text3",      !NUMERIC, gov.enums.Take.InsertOnly, "SSS Premium");
                        saver.FieldName("value3",      NUMERIC, gov.enums.Take.InsertOnly, laborpaid.getSSSPrem());
                    }
                    if (laborpaid.getTaxHeld()!= 0D) {
                        saver.FieldName("text4",      !NUMERIC, gov.enums.Take.InsertOnly, "W/TAX");
                        saver.FieldName("value4",      NUMERIC, gov.enums.Take.InsertOnly, laborpaid.getTaxHeld());
                    }
                    saver.FieldName("ref_no2",     NUMERIC, gov.enums.Take.InsertOnly, (laborpaid.getBunos() > 0 ? 1 : 0));
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