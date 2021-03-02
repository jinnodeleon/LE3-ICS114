/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myController;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author Jinno
 */

class urlAppender{
    public StringBuffer urlApp(String url, String host, String port, String databaseName){
        StringBuffer tempurl = new StringBuffer(url).append("://").append(host).append(":").append(port).append("/").append(databaseName);
    return tempurl;

    }
}
public class LE3Controller extends HttpServlet{
    Connection conn;
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        
        try
        {
            /*Driver Class START*/
            Class.forName(config.getInitParameter("driverClass"));
            /*Driver Class END*/
            
            /*Username and Password START*/
            String user, pass;
            user = config.getInitParameter("user");
            pass = config.getInitParameter("pass");
            /*Username and Password END*/
            
            /*URL START*/
            urlAppender draftUrl = new urlAppender();
            String finalUrl = draftUrl.urlApp(config.getInitParameter("url"), config.getInitParameter("host"), config.getInitParameter("port"), config.getInitParameter("databaseName")).toString();
            /*URL END*/
            
            /*Connection START*/
            conn = DriverManager.getConnection(finalUrl,user,pass);
            /*Connection END*/
        }
        catch (SQLException sqle) 
        {
            System.out.println(sqle.getMessage());
        } 
        catch (ClassNotFoundException cnfe) 
        {
            System.out.println(cnfe.getMessage());
        }
    }
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {	

                if (conn != null) {
                        Statement stmt = conn.createStatement();
                        ResultSet records = stmt.executeQuery("SELECT * FROM PERSON_INFO ORDER BY NAME");
                        request.setAttribute("results", records);
                        request.getRequestDispatcher("displayresult.jsp").forward(request,response);
                } else {
                        response.sendRedirect("error.jsp");
                }
        } catch (SQLException sqle){
                response.sendRedirect("error.jsp");
        } 
    }


  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
