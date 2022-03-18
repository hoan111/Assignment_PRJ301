/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.API.Match;

import Model.MatchOrder;
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
public class GetMatchConfig extends HttpServlet {

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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject json = new JsonObject();
        String ip = request.getParameter("ip");
        String port = request.getParameter("port");

        MatchDBContext matchDB = new MatchDBContext();
        MatchOrder mo = matchDB.getMatchInfo(ip, port);
        if (mo != null) {
            json.addProperty("code", 200);
            json.addProperty("OrderID", mo.getOrderID());
            json.addProperty("ServerIP", mo.getServer().getIp());
            json.addProperty("Port", mo.getServer().getPort());
            json.addProperty("Type", mo.getType());

            response.setStatus(200);
            response.getWriter().println(gson.toJson(json).toString());
        } else {
            json.addProperty("code", 404);
            json.addProperty("Message", "Cannot find any match order on this game server!");
            response.setStatus(404);
            response.getWriter().println(gson.toJson(json).toString());
        }
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
        json.addProperty("code", 500);
        json.addProperty("Message", "POST method does not allowed on this API endpoint!");
        response.setStatus(500);
        response.getWriter().println(gson.toJson(json).toString());
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
