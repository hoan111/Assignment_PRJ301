/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.API.Match;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
public class UpdateMatchScore extends HttpServlet {

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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject json = new JsonObject();
        int matchID = Integer.parseInt(request.getParameter("matchID"));
        int ct_score = Integer.parseInt(request.getParameter("ct_score"));
        int t_score = Integer.parseInt(request.getParameter("t_score"));
        String ct_name = request.getParameter("ct_name");
        String t_name = request.getParameter("t_name");
        MatchDBContext matchDB = new MatchDBContext();
        if (matchDB.updateMatchScore(matchID, ct_score, t_score, ct_name, t_name)) {
            json.addProperty("Code", 200);
            json.addProperty("Message", "Match score updated successfully!");
            response.setStatus(200);
            response.getWriter().println(gson.toJson(json).toString());
        } else {
            json.addProperty("Code", 500);
            json.addProperty("Message", "An error occurred when updating match score!");
            response.setStatus(500);
            response.getWriter().println(gson.toJson(json).toString());
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
