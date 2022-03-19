/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Dashboard;

import Controller.Login.BaseAuthController;
import Model.MatchHistory;
import dal.MatchDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoan
 */
public class EditMatchHistoryController extends BaseAuthController {

    private static int rawid = 0;

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
        rawid = Integer.parseInt(request.getParameter("id"));
        MatchDBContext matchDB = new MatchDBContext();

        MatchHistory mh = matchDB.getMatchHistoryByID(rawid);

        request.setAttribute("matchinfo", mh);
        request.getRequestDispatcher("../../Dashboard/EditMatch.jsp").forward(request, response);
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
        int matchid = Integer.parseInt(request.getParameter("matchID"));
        int state = Integer.parseInt(request.getParameter("matchState"));
        MatchDBContext matchDB = new MatchDBContext();
        if(matchDB.updateMatchDetail(matchid, state))
        {
            matchDB.updateMatchDetail(matchid, state);
            request.setAttribute("isSuccess", true);
            request.setAttribute("msg", "Server edited successfully!");
            request.getRequestDispatcher("../../Dashboard/EditMatchOrder.jsp").forward(request, response);
        }
        else
        {
            request.setAttribute("isSuccess", false);
            request.setAttribute("msg", "Edit server failed because an error occured!");
            request.getRequestDispatcher("../../Dashboard/EditMatchOrder.jsp").forward(request, response);
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
