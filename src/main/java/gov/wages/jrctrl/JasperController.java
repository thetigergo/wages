package gov.wages.jrctrl;

/**
 *
 * @author felix
 */
public class JasperController implements java.io.Serializable {

    private static final long serialVersionUID = -4292420913022900494L;

//    @javax.annotation.PostConstruct
//    protected void init() throws java.io.IOException {
//        java.util.Map<String, String> params = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//	/*CtrlNo = params.get("action");
//        pwidi  = Boolean.valueOf(params.get("pwidi"));
//        retrieveJOs(null);*/
//    }

    public void PDF(String value) throws java.io.IOException, net.sf.jasperreports.engine.JRException, java.sql.SQLException {
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

        net.sf.jasperreports.engine.JasperPrint jasperPrint = net.sf.jasperreports.engine.JasperFillManager.fillReport(reportPath, params, new gov.dbase.PgSQLConn());
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
        byte[] bytes = net.sf.jasperreports.engine.JasperRunManager.runReportToPdf(jasper.getPath(), params, new gov.dbase.PgSQLConn());
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
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        // REPORT PARAMETERS
        params.put("CtrlNo", value);
//        params.put("HasATM", hasATM);
        
        previewNow(params, folder + (test ? "wageAdd" : "wages"));
    }
    
//    public void endoParam(String value, Boolean test) throws Exception {
//        java.util.Map<String, Object> params = new java.util.HashMap();
//        // REPORT PARAMETERS
//        params.put("CtrlNo", value);
//        
//        previewNow(params, (test ? "endo/wageAdd" : "endo/wages"));
//    }

}
