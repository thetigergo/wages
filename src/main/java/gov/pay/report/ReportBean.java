package gov.pay.report;

/**
 *
 * @author felix
 */
public class ReportBean implements java.io.Serializable {

    private static final long serialVersionUID = -3602742434296243728L;
    
    private final java.util.List<gov.pay.WageField> arFields = new java.util.ArrayList<>();
    
    private String  ProjectID, Project, Opesina, Certify1, Certify2, Certify3, Rank1, Rank2, Rank3, CtrlNo, PayDate;
    private Double  TotalWage = 0D, TotalGross = 0D, TotalDeduct = 0D, TotalHdmf = 0D;
    private Short   Counter = 0;
    private Boolean pwidi = false;

    
    
    public String getProjectID() {return ProjectID;}
    public String getProject() {return Project;}
    public String getOpesina() {return Opesina;}
    public String getCertify1() {return Certify1;}
    public String getCertify2() {return Certify2;}
    public String getCertify3() {return Certify3;}
    public String getRank1() {return Rank1;}
    public String getRank2() {return Rank2;}
    public String getRank3() {return Rank3;}
    public String getPayDate() {return PayDate;}

    public String getCtrlNo() {return CtrlNo;}
    public void setCtrlNo(String value) {CtrlNo = value;}
    

    public Double getTotalWage() {return TotalWage;}
    public Double getTotalGross() {return TotalGross;}
    public Double getTotalDeduct() {return TotalDeduct;}
    public Double getTotalHdmf() {return TotalHdmf;}
    


    public java.util.List<gov.pay.WageField> getWages() {return arFields;}
    
    

    @javax.annotation.PostConstruct
    protected void init() {
        java.util.Map<String, String> params = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	CtrlNo = params.get("action");
        pwidi  = Boolean.valueOf(params.get("pwidi"));
        retrieveJOs(null);
    }

    public void retrieveJOs(javax.faces.event.ActionEvent event) {
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.Statement _smt = jdbc.createStatement();
            java.sql.ResultSet rst = _smt.executeQuery(
                    "SELECT " +
                    /* 1*/"wages.worker, " +
                    /* 2*/"humane(workers.lastname, workers.firstname, workers.midname), " +
                    /* 3*/"workers.jobdesc, " +
                    /* 4*/"wages.datefr, " +
                    /* 5*/"wages.dateto, " +
                    /* 6*/"wages.days, " +
                    /* 7*/"wages.rate, " +
                    /* 8*/"(wages.days * wages.rate), " +
                    /* 9*/"wages.utime, " +
                    /*10*/"(wages.rate / 480.0 * wages.utime)::NUMERIC(18, 2), " +
                    /*11*/"wages.proyekto, " +
                    /*12*/"projects.projtitle, " +
                    /*13*/"wages.jobordno, " +
                    /*14*/"offices.office, " +
                    /*15*/"wages.payfr, " +
                    /*16*/"wages.payto, " +
                    /*17*/"wages.officer1, " +
                    /*18*/"wages.officer2, " +
                    /*19*/"wages.officer3, " +
                    /*20*/"wages.designa1, " +
                    /*21*/"wages.designa2, " +
                    /*22*/"wages.designa3, " +
                    /*23*/"wages.bunos, " +
                    /*24*/"wages.pag_ibig, " +
                    /*25*/"wages.sssprem " +
                    "FROM " +
                        "jos.workers JOIN pay.wages " +
                        "ON workers.uniqkey = wages.worker " +
                        "JOIN cbo.projects " +
                        "ON wages.proyekto = projects.projid " +
                        "JOIN jos.offices " +
                        "ON wages.opesina = offices.offcid " +
                    "WHERE " +
                        "(wages.ctrlno = '" + CtrlNo + "') " +
                        (pwidi ? "" : "AND (wages.paid_up = FALSE) ") +
                    "ORDER BY " +
                        "workers.lastname, workers.firstname, workers.midname")) {
            TotalWage = 0D; arFields.clear(); Counter = 0;
            while (rst.next()) {
                Counter++;
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
                        rst.getDouble(24),
                        rst.getDouble(25),
                        Counter,
                        rst.getDouble(23),
                        0D));                
                gov.pay.WageField payroll = arFields.get(arFields.size() - 1);
                TotalWage   += payroll.getNetAmount();
                TotalGross  += payroll.getGross();
                TotalDeduct += payroll.getDeduction();
                TotalHdmf   += payroll.getPagIbig();
                ProjectID    = rst.getString(11);
                Project      = rst.getString(12);
                Opesina      = rst.getString(14);
                PayDate      = DateRange(rst.getDate(15), rst.getDate(16));
                Certify1     = rst.getString(17);
                Certify2     = rst.getString(18);
                Certify3     = rst.getString(19);
                Rank1        = rst.getString(20);
                Rank2        = rst.getString(21);
                Rank3        = rst.getString(22);
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
