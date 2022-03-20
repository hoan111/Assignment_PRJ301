/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Dashboard;

import Controller.Login.BaseAuthController;
import Model.Account;
import Model.MatchHistory;
import Model.Server;
import dal.AccountDBContext;
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
public class MainDashboardController extends BaseAuthController {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        
        AccountDBContext accountDB = new AccountDBContext();
        MatchDBContext matchDB = new MatchDBContext();
        ServerDBContext serverDB = new ServerDBContext();
        
        int total_server = serverDB.countServers();
        int total_match_orders = matchDB.getTotalMatchOrder();
        int playing_match = matchDB.getCurrentPlayingMatch();
        int total_admin = accountDB.getAdmins();
        ArrayList<Server> servers = serverDB.getAllServers();
        ArrayList<MatchHistory> mh = matchDB.getMatchHistory();
        
        request.setAttribute("TotalServer", total_server);
        request.setAttribute("TotalOrder", total_match_orders);
        request.setAttribute("PlayingMatch", playing_match);
        request.setAttribute("TotalAdmin", total_admin);
        request.setAttribute("servers", servers);
        request.setAttribute("matchHistory", mh);
        
        request.getRequestDispatcher("Dashboard/Dashboard.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        response.sendRedirect("404.html");
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
