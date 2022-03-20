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

    public Account getAccount(String username, String password) {
        try {
            String sql = "SELECT [username]\n"
                    + "      ,[password]\n"
                    + "      ,[admin_id]\n"
                    + "      ,[role]\n"
                    + "      ,[key]\n"
                    + "  FROM [Admins] WHERE username = ? AND password = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setId(rs.getInt("admin_id"));
                account.setRole(rs.getInt("role"));
                account.setApikey(rs.getString("key"));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getNumberOfRoles(String username, String url) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM \n"
                    + "Account a INNER JOIN [Account_Group] ag ON a.username = ag.username\n"
                    + "		  INNER JOIN [Group] g ON g.gid = ag.gid\n"
                    + "		  INNER JOIN [Group_Feature] gf ON gf.gid = g.gid\n"
                    + "		  INNER JOIN [Feature] f ON gf.fid = f.fid\n"
                    + "WHERE a.username = ? AND f.url = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, url);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int checkAPIkey(String key) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM \n"
                    + "Admins a\n"
                    + "WHERE a.[key] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, key);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean createAccount(String username, String password, int role, String apikey) {
        try {
            String sql = "INSERT INTO [dbo].[Admins]\n"
                    + "           ([username]\n"
                    + "           ,[password]\n"
                    + "           ,[role]\n"
                    + "           ,[key])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setInt(3, role);
            stm.setString(4, apikey);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<Account> getAccount() {
        try {
            String sql = "SELECT admin_id, username, [role], [key] \n"
                    + "FROM Admins";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList<Account> admins = new ArrayList<>();

            while (rs.next()) {
                Account a = new Account();
                a.setId(rs.getInt("admin_id"));
                a.setUsername(rs.getString("username"));
                a.setRole(rs.getInt("role"));
                a.setApikey(rs.getString("key"));

                admins.add(a);
            }
            return admins;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Account getAccountbyID(int accountID) {
        try {
            String sql = "SELECT admin_id, username, [role], [key] \n"
                    + "FROM Admins\n"
                    + "WHERE admin_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Account a = new Account();
                a.setId(rs.getInt("admin_id"));
                a.setUsername(rs.getString("username"));
                a.setRole(rs.getInt("role"));
                a.setApikey(rs.getString("key"));
                return a;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateAccount(int adminID, String username, String password, int role, String apikey) {
        try {
            String sql = "UPDATE [dbo].[Admins]\n"
                    + "   SET [username] = ISNULL(?, [username])\n"
                    + "      ,[password] = ISNULL(?, [password])\n"
                    + "      ,[role] = ISNULL(?, [role])\n"
                    + "      ,[key] = ISNULL(?, [key])\n"
                    + " WHERE admin_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setInt(3, role);
            stm.setString(4, apikey);
            stm.setInt(5, adminID);
            stm.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteAccount(int adminID) {
        try {
            String sql = "DELETE FROM [dbo].[Admins]\n"
                    + "      WHERE admin_id = ? and admin_id != 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, adminID);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getAdmins() {
        try {
            String sql = "SELECT COUNT(*) as Admins\n"
                    + "  FROM [dbo].[Admins]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int totalAdmin = rs.getInt("Admins");
                return totalAdmin;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
