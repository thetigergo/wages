package gov.wages.jrctrl;

/**
 *
 * @author felix
 */
public class JasperController implements java.io.Serializable {

    private static final long serialVersionUID = -4292420913022900494L;
    
    // Payara injects the managed connection pool here
    @javax.annotation.Resource(lookup = "jdbc/JosCosPool")
    private javax.sql.DataSource dsJosCos;

    
//    @javax.annotation.PostConstruct
//    protected void init() throws java.io.IOException {
//        java.util.Map<String, String> params = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//	/*CtrlNo = params.get("action");
//        pwidi  = Boolean.valueOf(params.get("pwidi"));
//        retrieveJOs(null);*/
//    }

    public void PDF(String value) throws Exception {
        javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //java.io.InputStream reportSource = getClass().getResourceAsStream("/jrfuels/dailytrans.jasper");
        String reportPath = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRealPath("wages.jasper");

        java.util.Map<String, Object> params = new java.util.HashMap<>();

//        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();
//        logger.setLevel((org.apache.log4j.Level)org.apache.log4j.Level.WARN);

        // REPORT PARAMETERS
        params.put("CtrlNo", value);

        response.addHeader("Content-disposition", "attachment; filename-report.pdf");
        javax.servlet.ServletOutputStream outputStream = response.getOutputStream();

        net.sf.jasperreports.engine.JasperPrint jasperPrint = net.sf.jasperreports.engine.JasperFillManager.fillReport(reportPath, params, dsJosCos.getConnection());
        net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        //net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint, false);
        javax.faces.context.FacesContext.getCurrentInstance().responseComplete();

    }
    
    private void previewNow(java.util.Map<String, Object> params, String report) throws Exception {
//        java.util.Map<String, Object> params = new java.util.HashMap<String, Object>();
        //java.io.File img=new java.io.File(javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/classes/images/sc.png"));
        //java.awt.Image icon=fixImage(new javax.swing.ImageIcon(img.getPath()),null).getImage();
    // REPORT PARAMETERS
        //params.put("LogoSC",icon);
//        params.put("CtrlNo", value);
        
        java.io.File jasper = new java.io.File(javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRealPath(report + ".jasper"));
        byte[] bytes = net.sf.jasperreports.engine.JasperRunManager.runReportToPdf(jasper.getPath(), params, dsJosCos.getConnection());
        javax.servlet.http.HttpServletResponse httpServletResponse=(javax.servlet.http.HttpServletResponse)javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setContentLength(bytes.length);
        try (javax.servlet.ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
            servletOutputStream.write(bytes, 0, bytes.length);
            servletOutputStream.flush();
        }
        javax.faces.context.FacesContext.getCurrentInstance().responseComplete();        
     }

    /*public void wageParam(String value) throws Exception {
        java.util.Map<String, Object> params = new java.util.HashMap<String, Object>();
        // REPORT PARAMETERS
        params.put("CtrlNo", value);
        
        previewNow(params, "wages");
    }*/

//    public void wageParam(String value, Boolean test) throws Exception {
//        java.util.Map<String, Object> params = new java.util.HashMap();
//        // REPORT PARAMETERS
//        params.put("CtrlNo", value);
//        
//        previewNow(params, (test ? "pays/wageAdd" : "pays/wages"));
//    }

    public void viewPayroll(String value, Boolean test, String folder, Boolean hasATM) throws Exception {
        String sqlCmd = 
                    "INSERT INTO pay.suholan(ctrlno, payfr, payto, userid, typed, kabook) " +
                    "SELECT timebook.ctrlno, timebook.payfr, timebook.payto, timebook.userid, ?, COUNT(timebook.*) " +
                    "FROM pay.timebook INNER JOIN psnl.jobworker ON timebook.worker = jobworker.uniqkey " +
                    "WHERE (timebook.ctrlno = ?) AND (jobworker.bank_acct IS " + (hasATM ? "NOT" : "") + " NULL) " +
                    "GROUP BY timebook.ctrlno, timebook.payfr, timebook.payto, timebook.userid " +
                    "ORDER BY timebook.ctrlno " +
                    "ON CONFLICT DO NOTHING;",
            cmdSql =
                    "INSERT INTO pay.suholan(ctrlno, payfr, payto, userid, typed, kabook) " +
                    "SELECT laborpaid.ctrlno, laborpaid.payfr, laborpaid.payto, laborpaid.userid, ?, COUNT(laborpaid.*) " +
                    "FROM pay.laborpaid INNER JOIN psnl.jobworker ON laborpaid.worker = jobworker.uniqkey " +
                    "WHERE (laborpaid.ctrlno = ?) AND (jobworker.bank_acct IS " + (hasATM ? "NOT" : "") + " NULL) " +
                    "GROUP BY laborpaid.ctrlno, laborpaid.payfr, laborpaid.payto, laborpaid.userid " +
                    "ORDER BY laborpaid.ctrlno " +
                    "ON CONFLICT DO NOTHING;";
        javax.faces.application.FacesMessage msg = null;
        try (java.sql.Connection jdbc = dsJosCos.getConnection();
                java.sql.PreparedStatement psmt = jdbc.prepareStatement(sqlCmd);
                java.sql.PreparedStatement pzmt = jdbc.prepareStatement(cmdSql)) {
            
            psmt.setString(1, hasATM ? "ATM" : "C/A");
            psmt.setString(2, value);
            psmt.executeUpdate();
            
            java.util.Map<String, Object> params = new java.util.HashMap<>();
            // REPORT PARAMETERS
            params.put("CtrlNo", value);
    //        params.put("HasATM", hasATM);

            previewNow(params, folder + (test ? "wageAdd" : "wages"));


        } catch (Exception ex) {
            msg = new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "ERROR", ex.getMessage());
        } finally {
            if (msg != null) javax.faces.context.FacesContext.getCurrentInstance().addMessage(null, msg);
        }        
    }
    
//    public void endoParam(String value, Boolean test) throws Exception {
//        java.util.Map<String, Object> params = new java.util.HashMap();
//        // REPORT PARAMETERS
//        params.put("CtrlNo", value);
//        
//        previewNow(params, (test ? "endo/wageAdd" : "endo/wages"));
//    }

}
