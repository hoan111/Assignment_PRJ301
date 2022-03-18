/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Dashboard;

import Controller.Login.BaseAuthController;
import Model.Account;
import Model.Match;
import Model.Server;
import dal.MatchDBContext;
import dal.ServerDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoan
 */
public class MatchRegisterController extends BaseAuthController {

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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServerDBContext serverDB = new ServerDBContext();

        ArrayList<Server> activeServers = serverDB.getActiveServers();
        request.setAttribute("activeServers", activeServers);
        request.getRequestDispatcher("../Dashboard/RegisterMatch.jsp").forward(request, response);
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MatchDBContext matchDB = new MatchDBContext();
        int serverID = Integer.parseInt(request.getParameter("selectServer"));
        int type = Integer.parseInt(request.getParameter("selectMatchType"));
        String facebook = request.getParameter("facebookLink");
        double price = Double.parseDouble(request.getParameter("price"));
        Account a = (Account) request.getSession().getAttribute("user");
        try {
            matchDB.registerMatch(serverID, type, price, facebook, a.getId());
            request.setAttribute("isSuccess", true);
            request.setAttribute("msg", "Match has been registered successfully!");
            request.getRequestDispatcher("../Dashboard/RegisterMatch.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("isSuccess", false);
            request.setAttribute("msg", "Register match failed");
            request.getRequestDispatcher("../Dashboard/RegisterMatch.jsp").forward(request, response);
        }
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
