package Service;
import Model.Account;
import DAO.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public UserService(){
        userDAO = new UserDAO();
    }
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    public  Account addAccount(Account account){
        if(account.username == null || account.username.trim().isEmpty()){
            return null;
        }
        if(account.password == null || account.password.length() < 4){
            return null;
        }
        return userDAO.registerAccount(account);
    }
    public Account verifylogin(String username, String password){
        return userDAO.loginAccount(username, password);
    }
}
