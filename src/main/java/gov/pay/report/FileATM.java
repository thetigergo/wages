package gov.pay.report;

/**
 *
 * @author felix
 */
@javax.servlet.annotation.WebServlet(name = "fileatm", urlPatterns = {"/acctg/fileatm.html"}) //urlPatterns = {"/admin/exporter.html", "/exporter.html", "/trans/exporter.html"})
public class FileATM extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = -1119658447196428884L;

    private static final int BYTES_DOWNLOAD = 1024;
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
        gov.wages.OnlineUser onlineUser = (gov.wages.OnlineUser) request.getSession().getAttribute("onlined");
        String dated = new java.text.SimpleDateFormat("MMdd").format(onlineUser.getMgaPetsa()),
               refNo = onlineUser.getTemporary(),
               Amount, AcctNo;
        Integer parsed = Integer.valueOf(refNo.split("-")[1], 10);
        
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=PAY" + dated + "-" + parsed + ".SC");
        StringBuilder content = new StringBuilder(/*"Test\n\nText file contects!!"*/);
        String sqlCmd1 =
                    "SELECT " +
                        "lpad(replace(bank_acct, '-', ''), 12, '0'), " +
                        "rpad(workers, 50, ' '), " +
                        "lpad(to_char(netpay::NUMERIC(18,2)), 11, ' ') " +
                    "FROM " +
                        "pay.foradvice(?)",
               sqlCmd2 =
                    "SELECT " +
                        "totamt, " +
                        "acctno " +
                    "FROM " +
                        "pay.advicepay " +
                    "WHERE " +
                        "(refkey = ?)";
        try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
            java.sql.PreparedStatement psmt1 = jdbc.prepareStatement(sqlCmd1);
            java.sql.PreparedStatement psmt2 = jdbc.prepareStatement(sqlCmd2)) {

            psmt2.setString(1, refNo);
            try (java.sql.ResultSet rst = psmt2.executeQuery()) {
                if (rst.next()) {
                    long test = Math.round(rst.getDouble(1) * 100D);
                    Amount = new java.text.DecimalFormat("#0.00").format(test * 0.01D);
                    AcctNo = rst.getString(2).substring(5).replaceAll("-", "") + "0";

                    StringBuilder concat = new StringBuilder("LGU SURIGAO CITY");
                    for (int abc = concat.length(); abc < 50; abc++) concat.append(' ');
                    concat.append(Amount);
                    
                    sqlCmd2 = AcctNo + concat.toString() + "\r\n";
                    content.append(sqlCmd2);
                }
            }
                        
            psmt1.setString(1, refNo);
            try (java.sql.ResultSet rst = psmt1.executeQuery()) {
                while (rst.next()) {
                    StringBuilder concat = new StringBuilder(rst.getString(1));
                    concat.append(rst.getString(2));
                    concat.append(rst.getString(3));

                    sqlCmd1 = concat.toString() + "\r\n";
                    content.append(sqlCmd1);
                }
            }


            String contain = content.toString();
            java.io.InputStream input = new java.io.ByteArrayInputStream(contain.getBytes("UTF8"));

            int read;
            byte[] bytes = new byte[BYTES_DOWNLOAD];
            try (java.io.OutputStream os = response.getOutputStream()) {
                while ((read = input.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
                os.flush();
            }


        } catch (Exception ex) {
            // display stack trace in the browser
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            try (java.io.PrintWriter printWriter = new java.io.PrintWriter(stringWriter)) {
                ex.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
            }
        } finally {
            
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
