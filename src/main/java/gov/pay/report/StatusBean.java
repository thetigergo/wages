package gov.pay.report;

/**
 *
 * @author felix
 */
public class StatusBean implements java.io.Serializable {

    private static final long serialVersionUID = -4815335398431386123L;
    
    private final java.util.List<gov.pay.WageField> arFields = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arCtrls = new java.util.ArrayList<>();
    
    
    
    private String ProjectID, ProjectTitle, Opesina, /*Certify1, Certify2, Certify3, Rank1, Rank2, Rank3, */CtrlNo, PayDate, Remark, Reference;
    private Double TotalWage = 0D, TotalGross = 0D, TotalDeduct = 0D, TotalBunos = 0D, TotalHdmf = 0D, TotalSSS = 0D;
    
    private java.util.Date BayadDate,  BudgetDate, DawatDate, CardDate, AuditDate, AccntDate,  RelesDate;
    private String         PayrollOut, BudgetOut,  Receiving, Carding,  Auditing,  Accountant, Releasing;


    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}

    public String getProjectID() {return ProjectID;}
    public String getProject() {return ProjectTitle;}
    public String getOpesina() {return Opesina;}

    /**/

    /**
     *
     * @return
     */
    public String getPayrollOut() {return PayrollOut;}
    public String getBudgetOut() {return BudgetOut;}
    public String getReceiving() {return Receiving;}
    public String getCarding() {return Carding;}
    public String getAuditing() {return Auditing;}
    public String getAccountant() {return Accountant;}
    public String getReleasing() {return Releasing;}
    public String getPayDate() {return PayDate;}
    public java.util.Date getBayadDate() {return BayadDate;}
    public java.util.Date getBudgetDate() {return BudgetDate;}
    public java.util.Date getDawatDate() {return DawatDate;}
    public java.util.Date getCardDate() {return CardDate;}
    public java.util.Date getAuditDate() {return AuditDate;}
    public java.util.Date getAccntDate() {return AccntDate;}
    public java.util.Date getRelesDate() {return RelesDate;}
    public Double getTotalWage() {return TotalWage;}
    public Double getTotalGross() {return TotalGross;}
    public Double getTotalDeduct() {return TotalDeduct;}
    public Double getTotalBunos() {return TotalBunos;}
    public Double getTotalHdmf() {return TotalHdmf;}
    public Double getTotalSSS() {return TotalSSS;}

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
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement();
            java.sql.ResultSet rst = smt.executeQuery(
                    "SELECT DISTINCT " +
                        "ctrlno " +
                    "FROM " +
                        "pay.timebook " +
                    "WHERE " +
                        "(opesina = '" + onlineUser.getOpesina() + "') AND " +
                        "( " +
                            "(paid_up = FALSE) OR " +
                            "(entered::DATE >= (NOW()::DATE - (INTERVAL '2 MONTHS'))::DATE)" +
                        ") " +
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

    public void onCtrlChange() {
    //public void retrieveJOs(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement smt = jdbc.createStatement();
            java.sql.ResultSet rst = smt.executeQuery(
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
                    /*17*/"timebook.acctg_ref, " +
                    /*18*/"timebook.bunos, " +
                    /*19*/"timebook.pag_ibig, " +
                    /*20*/"timebook.sssprem " +
//                    /*20*/"timebook.officer2, " +
//                    /*21*/"timebook.officer3, " +
//                    /*22*/"timebook.designa1, " +
//                    /*23*/"timebook.designa2, " +
//                    /*24*/"timebook.designa3 " +
                    "FROM " +
                        "psnl.jobworker JOIN pay.timebook " +
                        "ON jobworker.uniqkey = timebook.worker " +
                        "JOIN cbo.projects " +
                        "ON timebook.proyekto = projects.projid " +
                        "JOIN psnl.offices " +
                        "ON timebook.opesina = offices.offcid " +
                    "WHERE " +
                        "(timebook.ctrlno = '" + CtrlNo + "') " +
                    "ORDER BY " +
                        "jobworker.lastname, " +
                        "jobworker.firstname, " +
                        "jobworker.midname")) {
                Receiving = "";
                DawatDate = null;
                Carding  = "";
                CardDate = null;
                Auditing  = "";
                AuditDate = null;
                Accountant = "";
                AccntDate  = null;
                Releasing = "";
                RelesDate  = null;
            String acctg_ref = "";
            TotalWage = 0D; arFields.clear(); TotalGross = 0D; TotalDeduct = 0D; TotalBunos = 0D; TotalHdmf = 0D; TotalSSS = 0D;
            short ihapa = 0;
            while (rst.next()) {
                ihapa++;
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
                        rst.getDouble(19),
                        rst.getDouble(20),
                        ihapa,
                        rst.getDouble(18),
                        0D));                
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();
                TotalBunos  += payroll.getBunos();
                TotalHdmf   += payroll.getPagIbig();
                TotalSSS    += payroll.getSSSPrem();

                ProjectID    = rst.getString(11);
                ProjectTitle = rst.getString(12);
                Opesina      = rst.getString(14);
                PayDate      = DateRange(rst.getDate(15), rst.getDate(16));
//                Certify1   = rst.getString(15);
//                Certify2   = rst.getString(16);
//                Certify3   = rst.getString(17);
//                Rank1      = rst.getString(18);
//                Rank2      = rst.getString(19);
//                Rank3      = rst.getString(20);
                acctg_ref  = rst.getString(17);
            }

            try (java.sql.ResultSet tbl = smt.executeQuery("SELECT MAX(entered), MAX(pay_time) FROM pay.timebook WHERE (ctrlno = '" + CtrlNo + "')")) {
                if(tbl.next()) {
                    BayadDate  = tbl.getTimestamp(1); PayrollOut = (BayadDate  != null ? "Release" : "");
                    BudgetDate = tbl.getTimestamp(2); BudgetOut  = (BudgetDate != null ? "Release" : "");
                }
            }

            if (acctg_ref != null && !acctg_ref.isEmpty()) {
                String[] acctgNos = acctg_ref.split("-"); short pilian = 0;
                try (org.postgresql.core.BaseConnection zdbc = new gov.dbase.PgSQLConn("accounting");
                        java.sql.Statement _smt = zdbc.createStatement();
                        java.sql.ResultSet tbl = _smt.executeQuery(
                        "SELECT aprob, petsa, amount, geuli, tempo FROM doc.statuses(" + acctgNos[0] + "::SMALLINT, " + acctgNos[1] + ", 'receiving') " +
                        "UNION ALL " +
                        "SELECT aprob, petsa, amount, geuli, tempo FROM doc.statuses(" + acctgNos[0] + "::smallint, " + acctgNos[1] + ", 'carding') " +
                        "UNION ALL " +
                        "SELECT aprob, petsa, amount, geuli, tempo FROM doc.statuses(" + acctgNos[0] + "::smallint, " + acctgNos[1] + ", 'audit_acc') " +
                        "UNION ALL " +
                        "SELECT aprob, petsa, amount, geuli, tempo FROM doc.statuses(" + acctgNos[0] + "::smallint, " + acctgNos[1] + ", 'accountant') " +
                        "UNION ALL " +
                        "SELECT aprob, petsa, amount, geuli, tempo FROM doc.statuses(" + acctgNos[0] + "::smallint, " + acctgNos[1] + ", 'release')")) {
                    while (tbl.next()) {
                        pilian++;
                        switch (pilian) {
                            case 1:
                                if (tbl.getObject(1) != null) {
                                    Receiving = tbl.getString(1);
                                    DawatDate = tbl.getTimestamp(2);
                                } else {
                                    Receiving = tbl.getString(4);
                                    DawatDate = tbl.getTimestamp(5);
                                }
                                break;
                            case 2:
                                if (tbl.getObject(1) != null) {
                                    Carding  = tbl.getString(1);
                                    CardDate = tbl.getTimestamp(2);
                                } else {
                                    Carding  = tbl.getString(4);
                                    CardDate = tbl.getTimestamp(5);
                                }
                                break;
                            case 3:
                                if (tbl.getObject(1) != null) {
                                    Auditing  = tbl.getString(1);
                                    AuditDate = tbl.getTimestamp(2);
                                } else {
                                    Auditing  = tbl.getString(4);
                                    AuditDate = tbl.getTimestamp(5);
                                }
                                break;
                            case 4:
                                if (tbl.getObject(1) != null) {
                                    Accountant = tbl.getString(1);
                                    AccntDate  = tbl.getTimestamp(2);
                                } else {
                                    Accountant = tbl.getString(4);
                                    AccntDate  = tbl.getTimestamp(5);
                                }
                                break;
                            default:
                                if (tbl.getObject(1) != null) {
                                    Releasing = tbl.getString(1);
                                    RelesDate  = tbl.getTimestamp(2);
                                } else {
                                    Releasing = tbl.getString(4);
                                    RelesDate  = tbl.getTimestamp(5);
                                }
                        }
                    }
                }
            }

        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
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

}

