/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import Model.Admin;
import Model.Server;
import java.sql.Date;
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
                    + "      ,Admins.username as created_by\n"
                    + "  FROM [dbo].[Servers] INNER JOIN Admins \n"
                    + "  ON Servers.created_by = Admins.admin_id";
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
                
                Admin a = new Admin();
                a.setUsername(rs.getString("created_by"));
                s.setAdmin(a);
                
                servers.add(s);
            }
            return servers;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
