package gov.wages;

/**
 *
 * @author felix
 */
/*@javax.servlet.annotation.WebServlet(name = "LogOut", 
        urlPatterns = {
            "/admin/logout.html",
            "/logout.html",
            "/acctg/logout.html",
            "/pays/logout.html",
            "/bhw/logout.html",
            "/endo/logout.html"
        })*/
public class LogOutlet extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = 3716359215404225342L;

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
        response.setContentType("text/html;charset=UTF-8");
        try (java.io.PrintWriter docout = response.getWriter()) {
            try (org.postgresql.core.BaseConnection jdbc = new gov.dbase.PgSQLConn();
                    java.sql.Statement _smt = jdbc.createStatement()) {
                gov.wages.OnlineUser onlineUser = (gov.wages.OnlineUser) request.getSession().getAttribute("onlined");
                /*
                 * TODO output your page here. You may use following sample code.
                 */
                String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                       //sessionid = request.getParameter("sessionid");

                docout.println("<html>");
                docout.println("<head>");
                docout.println("<meta http-equiv='refresh' content='1;url=" + url + "'/>");
                docout.println("<link rel='stylesheet' type='text/css' href='" + onlineUser.getCSS() + "'/>");
                docout.println("<title>Servlet LogOut</title>");            
                docout.println("</head>");
                docout.println("<body>");
                docout.println("<div align='center'><br/><br/><br/><br/><h1>Signing out . . . . .</h1></div>");
                docout.println("</body>");
                docout.println("</html>");

                request.getSession().invalidate();
    //            request.logout();


                _smt.executeUpdate("UPDATE userlogon SET lastout = NOW() WHERE (userid = '" + onlineUser.getUserOnline() + "')");

            } catch (java.sql.SQLException ex) {
                ex.printStackTrace(docout);
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
