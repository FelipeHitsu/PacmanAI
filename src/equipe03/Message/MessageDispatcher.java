package equipe03.Message;

public class MessageDispatcher {

    private static MessageDispatcher instance = null;

    private MessageDispatcher(){}

    public static MessageDispatcher getInstance(){
        if(instance == null)
            instance = new MessageDispatcher();
        return instance;
    }

    public void dispatchMessage(Entity msgSender, Entity msgReceiver, String message, Object extraInfo){
        Message msg = new Message(msgSender, msgReceiver, message, extraInfo);

        deliverMessage(msgReceiver, msg);
    }

    public void deliverMessage(Entity receiver, Message msg){
        if(!receiver.handleMessage(msg)){
            System.out.println("Cannot handle, error!\n");
        }
    }

}
