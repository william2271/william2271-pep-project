package Service;
import java.util.List;
import Model.Message;
import DAO.MessageDAO;
public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDao){
     this.messageDAO = messageDao;
    }
    public Message addMessage(Message message){
        if(message.message_text == null || message.message_text.trim().isEmpty() || message.message_text.length() > 255){
            return null;
        }
        return messageDAO.createMessage(message);
    }
    public List<Message> getAllMessage(){
        return messageDAO.getAllMessages();
    }
    public List<Message> getAllMessageById(int message_id){
        return messageDAO.getAllMessageById(message_id);
    }
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }
    public Message updateMessageById(int message_id, Message message){
        if (message.message_text == null || message.message_text.trim().isEmpty() || message.message_text.length() > 255){
            return null;
        }
        messageDAO.updateMessageById(message_id, message);
       return messageDAO.getMessageById(message_id);
       
    }
    public Message deleteMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }
}
