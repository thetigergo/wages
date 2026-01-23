package gov.audit;

/**
 *
 * @author felix
 */
public class ATMBean implements java.io.Serializable {

    private static final long serialVersionUID = -1325334139286629372L;
    
    private final boolean NUMERIC = true; //, CONDITION = true;
    
    private java.util.Date DateATM = new java.util.Date();
    private String RefNo, AccntNo, Fund;
    private Short FundID, WhichOf;
    
//    private ForATMList selectedPayroll;
    
    

    private gov.wages.OnlineUser onlineUser;
    public void setOnlineBean(gov.wages.OnlineUser activeUser) {onlineUser = activeUser;}

    
    
    private final java.util.List<ForATMList> arCtrls = new java.util.ArrayList<>();
    private final java.util.List<javax.faces.model.SelectItem> arFunds = new java.util.ArrayList<>();

    
    public java.util.List<ForATMList> getCtrlNos() {return arCtrls;}
    public java.util.List<javax.faces.model.SelectItem> getFunding() {return arFunds;}

    
    private java.util.List<ForATMList> SelectedPayrolls;

    public java.util.List<ForATMList> getSelectedPayrolls() {return SelectedPayrolls;}
    public void setSelectedPayrolls(java.util.List<ForATMList> value) {SelectedPayrolls = value;}

//    public ForATMList getSelectedPayroll() {return selectedPayroll;}
//    public void setSelectedPayroll(ForATMList value) {selectedPayroll = value;}
    
    
    
    
    public java.util.Date getDateATM() {return DateATM;}
    public void setDateATM(java.util.Date value) {DateATM = value;}

    public String getRefNo() {return (RefNo == null ? onlineUser.getTemporary() : RefNo);}
    public void setRefNo(String value) {RefNo = value;}

    public String getAccntNo() {return AccntNo;}
    public void setAccntNo(String value) {AccntNo = value;}

    public Short getWhichOf() {return WhichOf;}
    public void setWhichOf(Short value) {WhichOf = value;}

//    public String getFund() {return Fund;}
//    public void setFund(String value) {Fund = value;}

    public Short getFundID() {return FundID;}
    public void setFundID(Short value) {
        FundID = value;
        
        javax.faces.application.FacesMessage msg = null;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement("SELECT acctno, fundname FROM pay.fundings WHERE (numbers = ?);")) {
            
            psmt.setShort(1, FundID);
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                if (rst.next()) {
                    setAccntNo(rst.getString(1));
                    Fund = rst.getString(2);
                }
            }
            
        } catch (java.sql.SQLException sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
    
    @javax.annotation.PostConstruct
    protected void init() {
        javax.faces.application.FacesMessage msg = null;

        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.PreparedStatement asmt = jdbc.prepareStatement("SELECT 0 FROM pg_catalog.pg_class WHERE (relname = ?);")) {
            
            try (java.sql.PreparedStatement pzmt = jdbc.prepareStatement("SELECT numbers, fundname FROM pay.fundings ORDER BY fundname;");
                    java.sql.ResultSet rst = pzmt.executeQuery()) {
                arFunds.clear();
                while (rst.next())
                    arFunds.add(new javax.faces.model.SelectItem(rst.getString(1), rst.getString(2)));
            }
            
            java.util.Calendar cal = java.util.Calendar.getInstance();
            String atmseq = "foratm_seq";
            try (java.sql.PreparedStatement psmt = jdbc.prepareStatement("SELECT NOW()::DATE;");
                    java.sql.ResultSet rst = psmt.executeQuery()) {
                rst.next();
                cal.setTime(rst.getDate(1));
                atmseq += cal.get(java.util.Calendar.YEAR);
            }
            
            asmt.setString(1, atmseq);
            try (java.sql.ResultSet rst = asmt.executeQuery()) {
                if (rst.next()) try (java.sql.PreparedStatement psmt = jdbc.prepareStatement("SELECT last_value FROM pay." + atmseq);
                        java.sql.ResultSet rec = psmt.executeQuery()) {
                    rec.next();
                    RefNo = new java.text.DecimalFormat("0000").format(cal.get(java.util.Calendar.YEAR)) +
                            "-" +
                            new java.text.DecimalFormat("000000").format((rec.getInt(1) == 1 ? 0 : rec.getInt(1)) + 1);
                } else 
                    RefNo = new java.text.DecimalFormat("0000").format(cal.get(java.util.Calendar.YEAR)) +
                            "-" +
                            new java.text.DecimalFormat("000000").format(1);
                
            }
            

        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
//    public void onFundSelect() {
//        javax.faces.application.FacesMessage msg = null;
//        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
//                java.sql.PreparedStatement psmt = jdbc.prepareStatement("SELECT DATE_PART('YEAR', NOW()), (SUBSTR(refkey, 6, 6)::INTEGER) + 1 FROM pay.advicepay ORDER BY refkey DESC LIMIT 1;")) {
//            
//            try (java.sql.ResultSet rst = psmt.executeQuery()) {
//                if (rst.next()) RefNo = rst.getShort(1) + "-" + new java.text.DecimalFormat("000000").format(rst.getInt(2));
//            }
//            
//        } catch (Exception sex) {
//            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
//        } finally {
//            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }
    
    public void onWhichOf() {
        javax.faces.application.FacesMessage msg = null;
        String sqlComm = "SELECT ref_no, named, ctrlno, count, netpay FROM pay.foradvice WHERE (whichof = ?) ORDER BY ref_no, ctrlno;";
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(sqlComm)) {
            
            psmt.setShort(1, WhichOf);
            try (java.sql.ResultSet rst = psmt.executeQuery()) {
                arCtrls.clear();
                while (rst.next())
                    arCtrls.add(new ForATMList(rst.getString(1), rst.getString(2), rst.getString(3), rst.getShort(4), rst.getDouble(5)));
                }


        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String nextPage() {
        if (SelectedPayrolls.isEmpty()) return null;
        
        Long totalAmount = 0L;
        StringBuilder sqlFilter = new StringBuilder("IN(");
        for (ForATMList atms : SelectedPayrolls) {
            //if (atms.getNapili()) {
                totalAmount += Math.round(atms.getNetPay() * 100D);
                sqlFilter.append("'");
                sqlFilter.append(atms.getCtrlNo());
                sqlFilter.append("',");
            //}
        }
        sqlFilter = sqlFilter.deleteCharAt(sqlFilter.length() - 1);
        sqlFilter.append(")");
        

        javax.faces.application.FacesMessage msg = null;
        String sqlComm = "";
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                java.sql.Statement _smt = jdbc.createStatement();
                
                java.sql.PreparedStatement asmt = jdbc.prepareStatement("SELECT NOW(), next_ref FROM pay.next_ref(DATE_PART('YEAR', NOW)::INTEGER);");) {
            
            try (java.sql.ResultSet rst = asmt.executeQuery()) {
                if (rst.next()) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(rst.getDate(1));
                    RefNo = new java.text.DecimalFormat("0000").format(cal.get(java.util.Calendar.YEAR)) +
                            "-" +
                            new java.text.DecimalFormat("000000").format(rst.getInt(2));
                }
            }

            jdbc.setAutoCommit(false);
            try (java.sql.PreparedStatement psmt = jdbc.prepareStatement("INSERT INTO pay.advicepay(refkey, refdate, inputby, totamt, acctno, funds, fund_id)	VALUES (?, ?, ?, ?, ?, ?, ?);");
                    java.sql.PreparedStatement pzmt = jdbc.prepareStatement("UPDATE pay.timebook SET advicekey = ? WHERE (ctrlno " + sqlFilter + ") AND (advicekey IS NULL);");
                    java.sql.PreparedStatement pxmt = jdbc.prepareStatement("UPDATE pay.laborpaid SET advicekey = ? WHERE (ctrlno " + sqlFilter + ") AND (advicekey IS NULL);")) {
                psmt.setString(1, RefNo);
                psmt.setDate  (2, new java.sql.Date(DateATM.getTime()));
                psmt.setString(3, onlineUser.getUserOnline());
                psmt.setDouble(4, (totalAmount * 0.01D));
                psmt.setString(5, AccntNo);
                psmt.setString(6, Fund);
                psmt.setShort (7, FundID);
                psmt.executeUpdate();

                /*gov.dbase.SQLExecute saver = new gov.dbase.SQLExecute("pay.advicepay");
                saver.FieldName("refkey",  !NUMERIC, gov.enums.Take.InsertOnly, RefNo);
                saver.FieldName("refdate", !NUMERIC, gov.enums.Take.InsertOnly, new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateATM));
                saver.FieldName("inputby", !NUMERIC, gov.enums.Take.InsertOnly, onlineUser.getUserOnline());
                saver.FieldName("totamt",   NUMERIC, gov.enums.Take.InsertOnly, (totalAmount * 0.01D));
                saver.FieldName("acctno",  !NUMERIC, gov.enums.Take.InsertOnly, AccntNo);
                saver.FieldName("funds",   !NUMERIC, gov.enums.Take.InsertOnly, Fund);
                saver.FieldName("fund_id",  NUMERIC, gov.enums.Take.InsertOnly, FundID);
                _smt.executeUpdate(saver.Perform(gov.enums.Fire.doInsert));*/
                
                pzmt.setString(1, RefNo);
                pzmt.executeUpdate();
                /*sqlComm = 
                        "UPDATE " +
                            "pay.timebook " +
                        "SET " +
                            "advicekey = '" + RefNo + "' " +
                        "WHERE " +
                            "(ctrlno " + sqlFilter + ") AND " +
                            "(advicekey IS NULL)";
                _smt.executeUpdate(sqlComm);*/

                pxmt.setString(1, RefNo);
                pxmt.executeUpdate();
                /*sqlComm = 
                        "UPDATE " +
                            "pay.laborpaid " +
                        "SET " +
                            "advicekey = '" + RefNo + "' " +
                        "WHERE " +
                            "(ctrlno " + sqlFilter + ") AND " +
                            "(advicekey IS NULL)";
                _smt.executeUpdate(sqlComm);*/
                jdbc.commit();
                //msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_INFO, "INFO", "Updated successfully");
                sqlComm = "/acctg/acctg_5Report";

                onlineUser.setTemporary(RefNo);
                onlineUser.setMgaPetsa(DateATM);
                onlineUser.setWhichOf(WhichOf);
            } catch (Exception exp) {
                jdbc.rollback();
                msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", exp.getMessage());
            }
            
        } catch (Exception sex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", sex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }        
        return sqlComm;
    }
}
