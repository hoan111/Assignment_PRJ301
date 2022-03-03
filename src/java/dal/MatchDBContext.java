/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import Model.Admin;
import Model.AdminMatch;
import Model.Server;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoan
 */
public class MatchDBContext extends DBContext {

    public void registerMatch(String server_id, Time dateCreated, int type, double price, int admin_id) {
        try {
            String sql = "INSERT INTO [dbo].[MatchOrders]\n"
                    + "           ([server_id]\n"
                    + "           ,[created_time]\n"
                    + "           ,[type]\n"
                    + "           ,[price]\n"
                    + "           ,[admin_id]\n"
                    + "     VALUES\n"
                    + "           (<server_id, ?,>\n"
                    + "           ,<created_time, ?,>\n"
                    + "           ,<type, ?,>\n"
                    + "           ,<price, ?,>\n"
                    + "           ,<admin_id, ?,>";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
