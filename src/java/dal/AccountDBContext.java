/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Account;

/**
 *
 * @author SAP-LAP-FPT
 */
public class AccountDBContext extends DBContext {
    public Account getAccount(String username,String password)
    {
        try {
            String sql = "SELECT [username]\n" +
                        "      ,[password]\n" +
                        "  FROM [Admins] WHERE username = ? AND [password] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                Account account = new Account();
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int getNumberOfRoles(String username,String url)
    {
        try {
            String sql = "SELECT COUNT(*) as Total FROM \n" +
            "Account a INNER JOIN [Account_Group] ag ON a.username = ag.username\n" +
            "		  INNER JOIN [Group] g ON g.gid = ag.gid\n" +
            "		  INNER JOIN [Group_Feature] gf ON gf.gid = g.gid\n" +
            "		  INNER JOIN [Feature] f ON gf.fid = f.fid\n" +
            "WHERE a.username = ? AND f.url = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, url);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
