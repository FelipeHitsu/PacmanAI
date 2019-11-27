package equipe03;

import equipe03.Message.Message;

public interface State<Ghost>{

    public void enter(Ghost g);

    public void execute(Ghost g);

    public void exit(Ghost g);

    public boolean onMessage(Ghost g, Message msg);

}
