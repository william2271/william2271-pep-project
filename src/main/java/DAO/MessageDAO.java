package DAO;
import java.sql.*;
import Model.*;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;
public class MessageDAO {
   
    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "Insert into Message (posted_by,message_text,time_posted_epoch) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_account_id,message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }



        return null;
    }
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            
            String sql = "select * from Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "SELECT * from Message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                 Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                        
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getAllMessageById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            
            String sql = "Select * from Message where posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                        messages.add(message);
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public void updateMessageById(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "update Message set message_text = ?  where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);
          

            preparedStatement.executeUpdate();
           
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    public void deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "delete from Message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            

            preparedStatement.executeUpdate();
           
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

}
