package DAO;
import java.sql.*;
import Model.Account;
import Util.ConnectionUtil;
public class UserDAO {

    public Account registerAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "Insert into Account (username,password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id,account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }




        return null;
    }

    public Account loginAccount(String username, String password){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int account_id = resultSet.getInt("account_id");
                String returnedUsername = resultSet.getString("username");
                String returnedPassword = resultSet.getString("password");

                return new Account(account_id,returnedUsername, returnedPassword);
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }




        return null;
    }
    
}
