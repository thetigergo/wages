package gov.audit;

/**
 *
 * @author felix
 */
public class AuditBHWBean implements java.io.Serializable {

    private static final long serialVersionUID = 7675576932677536556L;
    
    private final java.util.List<gov.pay.WageField> arFields = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arCtrls = new java.util.ArrayList<>();
    
    private String Certify1, Certify2, Rank1, Rank2, CtrlNo, PayDate, Remark, Reference, Distrito;
    private Double TotalWage = 0D, TotalGross = 0D, TotalDeduct = 0D, TotalBunos = 0D, TotalHdmf = 0D, TotalSSS = 0D;
    private final Double TotalTAX = 0D;
    private Short Counter = 0;
    private Boolean SwitchOff = false;
    private java.util.Date PayFr, PayTo;
    private Short Barangay, District;


    private final boolean NUMERIC = true; //, CONDITION = true;
    private final String /*SECTIONED = "receiving", */NEXTSECTION = "carding";


    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}
    
    
    public String getCertify1() {return Certify1;}
    public String getCertify2() {return Certify2;}
    public String getRank1() {return Rank1;}
    public String getRank2() {return Rank2;}
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

    public String getDistrito() {return Distrito;}

    
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
            try (java.sql.ResultSet rst = _zmt.executeQuery("SELECT DISTINCT ctrlno FROM pay.bhwwages WHERE (alobs = '" + alobsNo + "') AND (acctg_ref IS NULL);")) {
                arCtrls.clear();
                while (rst.next()) arCtrls.add(new javax.faces.model.SelectItem(rst.getString(1)));
            }


        } catch (java.sql.SQLException ex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }

    public void accessWorker() {//javax.faces.event.ActionEvent event
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
                        /*17*/"bhwwages.designa2, " +
                        /*18*/"bhwwages.opesina, " +
                        /*19*/"distrito.district, " +
                        /*20*/"bhwwages.acctg_ref IS NULL " +
                    "FROM " +
                        "bhw.workers JOIN pay.bhwwages " +
                        "ON workers.uniqkey = bhwwages.worker " +
                        "INNER JOIN bhw.distrito " +
                        "ON bhwwages.opesina = distrito.distid " +
                    "WHERE " +
                        "(bhwwages.ctrlno = '" + CtrlNo + "') AND " +
                        "(bhwwages.paid_up = FALSE) " +
                    "ORDER BY " +
                        "workers.lastname, workers.firstname, workers.midname")) {
            
            TotalWage = 0D; arFields.clear(); TotalGross = 0D; TotalDeduct = 0D; Counter = 1; TotalHdmf = 0D; TotalBunos = 0D; TotalSSS = 0D;
            while (rst.next()) {
                arFields.add(new gov.pay.WageField(
                        rst.getString(1),   //workerid
                        rst.getString(2),   //humane
                        rst.getString(3),   //jobdesc
                        rst.getDate  (4),   //datefr
                        rst.getDate  (5),   //dateto
                        rst.getFloat (6),   //days
                        rst.getFloat (7),   //rate
                        rst.getDouble(8),   //basic
                        rst.getShort (9),   //utime
                        rst.getDouble(10),  //deduction
                        rst.getDouble(12),  //pag-ibig
                        rst.getDouble(13),  //SSS Prem
                        Counter,            //counter
                        rst.getDouble(11),  //bunos
                        0D));               //withtax
                
                if (rst.getBoolean(20))
                    Counter++;
                else 
                    Counter = -2;
                
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();// + payroll.getPagIbig();
                TotalBunos  += payroll.getBunos();
//                TotalHDMF   += payroll.getPagIbig();
//                TotalSSS    += payroll.getSSSPrem();

                PayFr    = rst.getDate(4);
                PayTo    = rst.getDate(5);
                
                Certify1 = rst.getString(14);
                Certify2 = rst.getString(15);

                Rank1    = rst.getString(16);
                Rank2    = rst.getString(17);

                District = rst.getShort (18);
                Distrito = rst.getString(19);
            }
            if (Counter != null)
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
                if (rst.next())
                    throw new Exception("Acctg Ref. # already used.");
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
                for (gov.pay.WageField bhwwages : arFields) {
                    gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("bhw.carding");
                    saver.FieldName("keyid",      !NUMERIC, gov.enums.Take.InsertOnly, bhwwages.getWorkID());
                    saver.FieldName("trndates",   !NUMERIC, gov.enums.Take.InsertOnly, DateRange(bhwwages.getDateFr(), bhwwages.getDateTo()));
                    saver.FieldName("reference",  !NUMERIC, gov.enums.Take.InsertOnly, Reference);
                    saver.FieldName("particular", !NUMERIC, gov.enums.Take.InsertOnly, details.replaceAll("'", "''") + (bhwwages.getBunos() > 0 ? " P" + new java.text.DecimalFormat("#,##0.00").format(bhwwages.getBunos()) : ""));
                    saver.FieldName("workdays",    NUMERIC, gov.enums.Take.InsertOnly, bhwwages.getDays());
                    saver.FieldName("amount",      NUMERIC, gov.enums.Take.InsertOnly, bhwwages.getGross() + bhwwages.getBunos());
                    if (bhwwages.getDeduction() != 0) {
                        saver.FieldName("text1",      !NUMERIC, gov.enums.Take.InsertOnly, "U. T.");
                        saver.FieldName("value1",      NUMERIC, gov.enums.Take.InsertOnly, bhwwages.getDeduction());
                    }
                    saver.FieldName("inputby",    !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getUserOnline());
                    saver.FieldName("ctrlno",     !NUMERIC, gov.enums.Take.InsertOnly, CtrlNo);
                    saver.FieldName("district",    NUMERIC, gov.enums.Take.InsertOnly, District);
                    if (bhwwages.getPagIbig() != 0D) {
                        saver.FieldName("text2",      !NUMERIC, gov.enums.Take.InsertOnly, "PAG-IBIG Premium");
                        saver.FieldName("value2",      NUMERIC, gov.enums.Take.InsertOnly, bhwwages.getPagIbig());
                    }
                    if (bhwwages.getSSSPrem() != 0D) {
                        saver.FieldName("text3",      !NUMERIC, gov.enums.Take.InsertOnly, "SSS Premium");
                        saver.FieldName("value3",      NUMERIC, gov.enums.Take.InsertOnly, bhwwages.getSSSPrem());
                    }
                    if (bhwwages.getTaxHeld()!= 0D) {
                        saver.FieldName("text4",      !NUMERIC, gov.enums.Take.InsertOnly, "W/TAX");
                        saver.FieldName("value4",      NUMERIC, gov.enums.Take.InsertOnly, bhwwages.getTaxHeld());
                    }
                    saver.FieldName("ref_no2",     NUMERIC, gov.enums.Take.InsertOnly, (bhwwages.getBunos() > 0 ? 1 : 0));
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

