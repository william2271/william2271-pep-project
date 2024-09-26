package Controller;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.UserService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    UserService userService;
    MessageService messageService;

    public SocialMediaController(){
        this.userService = new UserService();
        this.messageService= new MessageService();
    }
    
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login",this::postLoginHandler);
        app.post("/messages",this::postMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByIdHandler);
        

        return app;
    }


    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = userService.addAccount(account);
       
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }
    private void postLoginHandler (Context ctx) throws JsonProcessingException{
        Account loginRequest = ctx.bodyAsClass(Account.class);
         Account verifyLogin = userService.verifylogin(loginRequest.getUsername(), loginRequest.getPassword());
         
       if(verifyLogin!= null){
        ctx.json(verifyLogin).status(200);

       }else{
        ctx.status(401);

       }

       
    }
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message) ;
       
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }
    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageById(message_id, message);
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(updatedMessage);
        }

    }
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(message_id);
        System.out.println(deletedMessage);
        if(deletedMessage == null){
            ctx.status(200);
        }else{
            ctx.json(deletedMessage);
        }

    }
    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessage());
    }
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.getMessageById(messageId);
            
            if (message != null) {
                ctx.json(message); 
            }
        
        
    }

    private void getAllMessagesByIdHandler(Context ctx){
        
            
            List<Message> messages = messageService.getAllMessageById(Integer.parseInt(ctx.pathParam("account_id")));
          ctx.json(messages);
       
    }

}