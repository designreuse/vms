/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypackage;
import db.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;


/**
 *
 * @author MPK05
 */
public class AddUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
                        
                // APPROACH 3 : USE UTILITIES Class
            final String serverName= "localhost";
            final String databaseName= "vms";
            String username = "root"; // Username/password required
            String password = "izzati"; // for MYSQL SERVER.
            DriverUtilities.loadDrivers();   
            String driver = DriverUtilities.getDriver(DriverUtilities.MYSQL);
            String url = DriverUtilities.makeURL(serverName,databaseName,DriverUtilities.MYSQL);
            
            String user = null;
            String pwd = null;
            String name;
            String level;
            
            user = request.getParameter("username");
            pwd = request.getParameter("password");
            name = request.getParameter("name");
            level = request.getParameter("level");
               
            try {
        // Load database driver if it's not already loaded.
        Class.forName(driver);
          // Establish network connection to database.
        Connection connection =
          DriverManager.getConnection(url, username, password);
        // Look up info about the database as a whole.

        //USE STATEMENT
  /*      String query = "SELECT * FROM user WHERE username='" + userid +"'";
        query = query + "AND password='" + userpass +"'";
        // Send query to database and store results.
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
  */ 
        //USE PREPAREDSTATEMENT
        String query2 = "INSERT INTO Users (staffID,password,fullName,userLevel) VALUES (?,?,?,?)";
        PreparedStatement insertUser = connection.prepareStatement(query2);
        insertUser.setString(1,user);
        insertUser.setString(2,pwd);
        insertUser.setString(3,name);
        insertUser.setString(4,level);
        insertUser.executeUpdate();

        System.out.println("New User has been added in database");

        response.sendRedirect("signup.jsp");
  // Print results.
        connection.close();
      } catch(ClassNotFoundException cnfe) {
        System.err.println("Error loading driver: " + cnfe);
      } catch(SQLException sqle) {
        System.err.println("Error connecting: " + sqle);
      } catch(Exception ex) {
        System.err.println("Error with input: " + ex);
      }
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
