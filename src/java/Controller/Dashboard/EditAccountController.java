/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Dashboard;

import Controller.Login.BaseAuthController;
import Model.Account;
import dal.AccountDBContext;
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
public class EditAccountController extends BaseAuthController {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        AccountDBContext accountDB = new AccountDBContext();
        rawid = Integer.parseInt(request.getParameter("id"));
        Account a = accountDB.getAccountbyID(rawid);

        if (a != null) {
            request.setAttribute("account", a);
            request.getRequestDispatcher("../Dashboard/EditAccount.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getServletContext() + "/account/view");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("Username");
        String password = request.getParameter("PasswordEdit");
        int role = Integer.parseInt(request.getParameter("role"));
        String apikey = request.getParameter("apikey");

        AccountDBContext accountDB = new AccountDBContext();
        if (accountDB.updateAccount(rawid, username, password, role, apikey)) {
            request.setAttribute("isSuccess", true);
            request.setAttribute("msg", "Account edited successfully!");
            request.getRequestDispatcher("../Dashboard/EditAccount.jsp").forward(request, response);
        } else {
            request.setAttribute("isSuccess", false);
            request.setAttribute("msg", "Edit account failed because an error occured!");
            request.getRequestDispatcher("../Dashboard/EditAccount.jsp").forward(request, response);
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
