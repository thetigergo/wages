package gov.pay.report;

/**
 *
 * @author felix
 */
@javax.servlet.annotation.WebServlet(name = "rptatm", urlPatterns = {"/acctg/rptatm.html"}) //urlPatterns = {"/admin/exporter.html", "/exporter.html", "/trans/exporter.html"})
public class RptATM extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = 2156790607309287444L;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void processRequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        //response.setContentType("text/html;charset=UTF-8");
        //java.io.PrintWriter out = response.getWriter();
        String refkey, realPath = request.getParameter("report");
        Short WhichOf;
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn()) {
            gov.wages.OnlineUser onlineUser = (gov.wages.OnlineUser) request.getSession().getAttribute("onlined");
            refkey = onlineUser.getTemporary();
            WhichOf = onlineUser.getWhichOf();
            
            java.util.Map<String, Object> params = new java.util.HashMap<>();
            params.put("RefKey" , refkey);
            params.put("whichof", (WhichOf != 0));
            
             /* TODO output your page here. You may use following sample code. */
            try (javax.servlet.ServletOutputStream servletOutputStream = response.getOutputStream()) {
                java.io.File reportFile = new java.io.File(getServletConfig().getServletContext().getRealPath(realPath/*"/wages.jasper"*/));
                byte[] bytes;
                
                bytes = net.sf.jasperreports.engine.JasperRunManager.runReportToPdf(reportFile.getPath(), params, jdbc);
                
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                
                servletOutputStream.write(bytes, 0, bytes.length);
                servletOutputStream.flush();
            }

            
        } catch (net.sf.jasperreports.engine.JRException | java.io.IOException | java.sql.SQLException ex) {
            //ex.printStackTrace(out);
            // display stack trace in the browser
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            java.io.PrintWriter printWriter = new java.io.PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            response.setContentType("text/plain");
            response.getOutputStream().print(stringWriter.toString());            
        } finally {            
            //out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
