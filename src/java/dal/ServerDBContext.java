/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import Model.Server;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoan
 */
public class ServerDBContext extends DBContext {

    public ArrayList<Server> getAllServers() {
        ArrayList<Server> servers = new ArrayList<>();
        try {
            String sql = "SELECT [server_id]\n"
                    + "      ,[server_ip]\n"
                    + "      ,[server_port]\n"
                    + "	  ,[server_password]\n"
                    + "      ,[rcon_password]\n"
                    + "      ,[date_created]\n"
                    + "      ,[is_active]\n"
                    + "      ,[server_name]\n"
                    + "  FROM [dbo].[Servers]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Server s = new Server();
                s.setId(rs.getInt("server_id"));
                s.setIp(rs.getString("server_ip"));
                s.setPort(rs.getString("server_port"));
                s.setServerPassword(rs.getString("server_password"));
                s.setRconPassword(rs.getString("rcon_password"));
                s.setCreatedDate(rs.getDate("date_created"));
                s.setIsActive(rs.getBoolean("is_active"));
                s.setserverName(rs.getString("server_name"));
                servers.add(s);
            }
            return servers;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Server> getActiveServers() {
        ArrayList<Server> servers = new ArrayList<>();
        try {
            String sql = "SELECT [server_id]\n"
                    + "      ,[server_ip]\n"
                    + "      ,[server_port]\n"
                    + "      ,[server_name]\n"
                    + "  FROM [dbo].[Servers]\n"
                    + "  WHERE\n"
                    + "  is_active = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Server s = new Server();
                s.setId(rs.getInt("server_id"));
                s.setIp(rs.getString("server_ip"));
                s.setPort(rs.getString("server_port"));
                s.setserverName(rs.getString("server_name"));
                servers.add(s);
            }
            return servers;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void addServers(String serverName, String ip, String port, String password, String rcon) {
        try {
            String sql = "INSERT INTO [dbo].[Servers]\n"
                    + "           ([server_name]\n"
                    + "           ,[server_ip]\n"
                    + "           ,[server_port]\n"
                    + "           ,[server_password]\n"
                    + "           ,[rcon_password])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setString(1, serverName);
            stm.setString(2, ip);
            stm.setString(3, port);
            stm.setString(4, password);
            stm.setString(5, rcon);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Server getServerbyID(int id) {
        try {
            String sql = "SELECT [server_id]\n"
                    + "      ,[server_ip]\n"
                    + "      ,[server_port]\n"
                    + "      ,[rcon_password]\n"
                    + "      ,[server_password]\n"
                    + "      ,[server_name]\n"
                    + "  FROM [dbo].[Servers]\n"
                    + "  WHERE [server_id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Server s = new Server();
                s.setId(rs.getInt("server_id"));
                s.setserverName(rs.getString("server_name"));
                s.setIp(rs.getString("server_ip"));
                s.setPort(rs.getString("server_port"));
                s.setServerPassword(rs.getString("server_password"));
                s.setRconPassword(rs.getString("rcon_password"));
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateServer(int id, String server_name, String IP, String port, String serverPassword, String rcon) {
        try {
            String sql = "UPDATE [dbo].[Servers]\n"
                    + "   SET [server_name] = ?\n"
                    + "      ,[server_ip] = ?\n"
                    + "      ,[server_port] = ?\n"
                    + "      ,[server_password] = ?\n"
                    + "      ,[rcon_password] = ?\n"
                    + " WHERE server_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, server_name);
            stm.setString(2, IP);
            stm.setString(3, port);
            stm.setString(4, serverPassword);
            stm.setString(5, rcon);
            stm.setInt(6, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteServer(int id) {
        String sql = "DELETE Servers"
                + " WHERE [server_id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public int countServers() {
        try {
            String sql = "SELECT COUNT(*) as TotalServers\n"
                    + "  FROM [dbo].[Servers]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                int count = rs.getInt("TotalServers");
                return count;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
