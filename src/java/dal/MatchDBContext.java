/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import Model.MatchHistory;
import Model.MatchOrder;
import Model.Server;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoan
 */
public class MatchDBContext extends DBContext {

    public void registerMatch(int server_id, int type, double price, String facebook, int admin_id) {
        try {
            String sql = "INSERT INTO [dbo].[MatchOrders]\n"
                    + "           ([server_id]\n"
                    + "           ,[type]\n"
                    + "           ,[price]\n"
                    + "           ,[admin_id]\n"
                    + "           ,[facebook])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, server_id);
            stm.setInt(2, type);
            stm.setDouble(3, price);
            stm.setString(5, facebook);
            stm.setInt(4, admin_id);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<MatchOrder> GetAllOrders() {
        try {
            String sql = "SELECT \n"
                    + "MatchOrders.order_id, \n"
                    + "Servers.server_id, \n"
                    + "Servers.server_ip, \n"
                    + "Servers.server_port, \n"
                    + "Servers.server_password, \n"
                    + "Servers.server_name, \n"
                    + "MatchOrders.type, \n"
                    + "MatchOrders.price, \n"
                    + "MatchOrders.facebook, \n"
                    + "MatchOrders.status, \n"
                    + "MatchOrders.created_time, \n"
                    + "Admins.username as RegisterBy,\n"
                    + "MatchOrders.comment\n"
                    + "FROM MatchOrders inner join Servers \n"
                    + "on MatchOrders.server_id = Servers.server_id\n"
                    + "inner join Admins \n"
                    + "on MatchOrders.admin_id = Admins.admin_id";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList<MatchOrder> listorders = new ArrayList<>();
            while (rs.next()) {
                MatchOrder mo = new MatchOrder();
                Server s = new Server();
                mo.setOrderID(rs.getInt("order_id"));

                s.setId(rs.getInt("server_id"));
                s.setIp(rs.getString("server_ip"));
                s.setPort(rs.getString("server_port"));
                s.setServerPassword(rs.getString("server_password"));
                s.setserverName(rs.getString("server_name"));
                mo.setServer(s);

                mo.setType(rs.getInt("type"));
                mo.setPrice(rs.getDouble("price"));
                mo.setFacebook(rs.getString("facebook"));
                mo.setStatus(rs.getInt("status"));
                mo.setCreatedTime(rs.getDate("created_time"));
                mo.setRegisterAdminName(rs.getString("RegisterBy"));
                mo.setComment(rs.getString("comment"));

                listorders.add(mo);
            }
            return listorders;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public MatchOrder GetMatchOrderByID(int id) {
        try {
            String sql = "SELECT \n"
                    + "MatchOrders.order_id, \n"
                    + "Servers.server_id, \n"
                    + "Servers.server_name, \n"
                    + "MatchOrders.type, \n"
                    + "MatchOrders.price, \n"
                    + "MatchOrders.facebook, \n"
                    + "MatchOrders.status, \n"
                    + "MatchOrders.comment\n"
                    + "FROM MatchOrders inner join Servers \n"
                    + "on MatchOrders.server_id = Servers.server_id\n"
                    + "WHERE MatchOrders.order_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            MatchOrder mo = new MatchOrder();
            if (rs.next()) {
                Server s = new Server();
                mo.setOrderID(rs.getInt("order_id"));

                s.setId(rs.getInt("server_id"));
                s.setserverName(rs.getString("server_name"));
                mo.setServer(s);

                mo.setType(rs.getInt("type"));
                mo.setPrice(rs.getDouble("price"));
                mo.setFacebook(rs.getString("facebook"));
                mo.setStatus(rs.getInt("status"));;
                mo.setComment(rs.getString("comment"));
            }
            return mo;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void updateMatchOrder(int order_id, int server_id, int type, double price, int status, String facebook, String comment) {
        try {
            String sql = "UPDATE [dbo].[MatchOrders]\n"
                    + "   SET [server_id] = ?\n"
                    + "      ,[type] = ?\n"
                    + "      ,[price] = ?\n"
                    + "      ,[status] = ?\n"
                    + "      ,[facebook] = ?\n"
                    + "      ,[comment] = ?\n"
                    + " WHERE [order_id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, server_id);
            stm.setInt(2, type);
            stm.setDouble(3, price);
            stm.setInt(4, status);
            stm.setString(5, facebook);
            stm.setString(6, comment);
            stm.setInt(7, order_id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteMatchOrder(int order_id) {
        try {
            String sql = "DELETE FROM [dbo].[MatchOrders]\n"
                    + "      WHERE order_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, order_id);

            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public MatchOrder getMatchInfo(String ip, String port) {
        try {
            String sql = "SELECT \n"
                    + "order_id, \n"
                    + "MatchOrders.server_id, \n"
                    + "server_ip, server_port, \n"
                    + "server_name, \n"
                    + "MatchOrders.type AS MatchType \n"
                    + "FROM MatchOrders INNER JOIN Servers\n"
                    + "ON MatchOrders.server_id = Servers.server_id\n"
                    + "WHERE server_ip = ? AND server_port = ? AND status = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, ip);
            stm.setString(2, port);

            ResultSet rs = stm.executeQuery();
            MatchOrder mo = new MatchOrder();
            if (rs.next()) {
                mo.setOrderID(rs.getInt("order_id"));
                mo.setType(rs.getInt("MatchType"));

                Server s = new Server();
                s.setId(rs.getInt("server_id"));
                s.setIp(rs.getString("server_ip"));
                s.setPort(rs.getString("server_port"));
                s.setserverName(rs.getString("server_name"));
                mo.setServer(s);
                return mo;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int insertNewMatch(int orderID) {
        try {
            String sql = "INSERT INTO [dbo].[MatchHistory]\n"
                    + "           ([order_id])\n"
                    + "     VALUES\n"
                    + "           (?)";
            PreparedStatement stm = connection.prepareStatement(sql, new String[]{"match_id"});
            stm.setInt(1, orderID);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();

            if (rs.next()) {
                int match_id = rs.getInt(1);
                return match_id;
            }
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public boolean updateMatch(int match_id, int state) {
        try {
            String sql = "UPDATE [dbo].[MatchHistory]\n"
                    + "   SET [state] = ?\n"
                    + " WHERE match_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, state);
            stm.setInt(2, match_id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<MatchHistory> getMatchHistory() {
        try {
            String sql = "SELECT [match_id]\n"
                    + "      ,[start_time]\n"
                    + "      ,[end_time]\n"
                    + "      ,[order_id]\n"
                    + "      ,[state]\n"
                    + "  FROM [dbo].[MatchHistory]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList<MatchHistory> matchHistory = new ArrayList<>();
            while (rs.next()) {
                MatchHistory mh = new MatchHistory();
                mh.setMatchid(rs.getInt("match_id"));
                mh.setStartTime(rs.getTimestamp("start_time"));
                mh.setEndTime(rs.getTimestamp("end_time"));
                mh.setOrderid(rs.getInt("order_id"));
                mh.setState(rs.getInt("state"));

                matchHistory.add(mh);
            }
            return matchHistory;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public MatchHistory getMatchHistoryByID(int matchID) {
        try {
            String sql = "SELECT [match_id]\n"
                    + "      ,[state]\n"
                    + "  FROM [dbo].[MatchHistory]\n"
                    + "  WHERE match_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, matchID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                MatchHistory mh = new MatchHistory();
                mh.setMatchid(rs.getInt("match_id"));
                mh.setState(rs.getInt("state"));
                return mh;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateMatchDetail(int matchID, int state) {
        try {
            String sql = "UPDATE [dbo].[MatchHistory]\n"
                    + "   SET [state] = ?\n"
                    + " WHERE match_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, state);
            stm.setInt(2, matchID);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteMatchDetail(int matchID) {
        try {
            String sql = "DELETE FROM [dbo].[MatchHistory]\n"
                    + " WHERE match_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, matchID);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateMatchOrder(int order_id, int status, String comment) {
        try {
            String sql = "UPDATE [dbo].[MatchOrders]\n"
                    + "   SET\n"
                    + "   [status] = ?,\n"
                    + "   [comment] = ?\n"
                    + " WHERE [order_id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setString(2, comment);
            stm.setInt(3, order_id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getTotalMatchOrder() {
        try {
            String sql = "SELECT COUNT(*) as TotalMatchOrders\n"
                    + "  FROM [dbo].[MatchOrders]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int totalServer = rs.getInt("TotalMatchOrders");
                return totalServer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getCurrentPlayingMatch() {
        try {
            String sql = "SELECT COUNT(*) as PlayingMatch\n"
                    + "  FROM [dbo].[MatchHistory]\n"
                    + "  WHERE state = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int totalPlayingMatch = rs.getInt("PlayingMatch");
                return totalPlayingMatch;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
