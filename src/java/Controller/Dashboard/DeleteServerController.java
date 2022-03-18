/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Dashboard;

import Controller.Login.BaseAuthController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dal.ServerDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoan
 */
public class DeleteServerController extends BaseAuthController {

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
        int id = Integer.parseInt(request.getParameter("id"));

        ServerDBContext serverDB = new ServerDBContext();
        if (serverDB.deleteServer(id)) {
            JsonObject jsonobj = new JsonObject();
            jsonobj.addProperty("msg", "Server deleted successfully!");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            response.setStatus(200);
            response.getWriter().println(gson.toJson(jsonobj).toString());
        } else {
            JsonObject jsonobj = new JsonObject();
            jsonobj.addProperty("msg", "An error occured when delete a server!");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            response.setStatus(500);
            response.getWriter().println(gson.toJson(jsonobj).toString());
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
