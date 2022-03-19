/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Dashboard;

import Controller.Login.BaseAuthController;
import Model.Server;
import dal.ServerDBContext;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoan
 */
public class AddServerController extends BaseAuthController {

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
        ServerDBContext serverDB = new ServerDBContext();
        ArrayList<Server> servers = serverDB.getAllServers();
        request.setAttribute("servers", servers);
        request.getRequestDispatcher("../Dashboard/AddServer.jsp").forward(request, response);
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
        String serverName = (String) request.getParameter("serverName");
        String serverIP = (String) request.getParameter("serverIP");
        String serverPort = (String) request.getParameter("serverPort");
        String serverPassword = (String) request.getParameter("serverPassword");
        String rcon = (String) request.getParameter("rconPassword");

        try {
            ServerDBContext serverDB = new ServerDBContext();
            serverDB.addServers(serverName, serverIP, serverPort, serverPassword, rcon);
            request.setAttribute("isSuccess", true);
            request.setAttribute("msg", "Server added successfully!");
            request.getRequestDispatcher("../Dashboard/AddServer.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("isSuccess", false);
            request.setAttribute("msg", "Failed to add new server!");
            request.getRequestDispatcher("../Dashboard/AddServer.jsp").forward(request, response);
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
