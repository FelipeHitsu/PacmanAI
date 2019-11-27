package equipe03.Message;

public class Message {

    private Entity sender;

    private Entity receiver;

    private String msg;

    private Object extraInfo;

    public Message(Entity msgSender, Entity msgReceiver, String message, Object extraInfo){
        this.sender = msgSender;
        this.receiver = msgReceiver;
        this.msg = message;
        this.extraInfo = extraInfo;
    }

    public String getMessage(){
        return msg;
    }

}
