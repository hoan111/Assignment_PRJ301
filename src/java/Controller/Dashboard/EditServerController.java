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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoan
 */
public class EditServerController extends BaseAuthController {

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
        try {
            rawid = Integer.parseInt(request.getParameter("id"));
            ServerDBContext serverDB = new ServerDBContext();
            Server s = serverDB.getServerbyID(rawid);
            if (s != null) {
                request.setAttribute("server", s);
                request.getRequestDispatcher("../Dashboard/EditServer.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/server/list");
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("serverID"));
        String serverName = (String) request.getParameter("serverName");
        String serverIP = (String) request.getParameter("serverIP");
        String serverPort = (String) request.getParameter("serverPort");
        String serverPassword = (String) request.getParameter("serverPassword");
        String rcon = (String) request.getParameter("rconPassword");
//
//        if (id != rawid) {
//            request.setAttribute("isSuccess", false);
//            request.setAttribute("msg", "Edit server failed because an error occured!");
//            request.getRequestDispatcher("../Dashboard/EditServer.jsp").forward(request, response);
//        } else {
        try {
            ServerDBContext serverDB = new ServerDBContext();
            serverDB.updateServer(rawid, serverName, serverIP, serverPort, serverPassword, rcon);
            request.setAttribute("isSuccess", true);
            request.setAttribute("msg", "Server edited successfully!");
            request.getRequestDispatcher("../Dashboard/EditServer.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("isSuccess", false);
            request.setAttribute("msg", "Edit server failed because an error occured!");
            request.getRequestDispatcher("../Dashboard/EditServer.jsp").forward(request, response);
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
