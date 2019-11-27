package equipe03;

import equipe03.Message.Message;

public class StateMachine<Ghost> {

    private Ghost myOwner;

    private State<Ghost> estadoAtual;

    private State<Ghost> estadoAnterior;

    private State<Ghost> estadoGlobal;

    public StateMachine(Ghost owner){
        myOwner = owner;
        estadoAtual = null;
        estadoAnterior = null;
        estadoGlobal = null;
    }

    public void setCurrentState(State<Ghost> g){estadoAtual = g;}

    public void setGlobalState(State<Ghost> g){estadoGlobal = g;}

    public void setPreviousState(State<Ghost> g){estadoAnterior = g;}

    public void update(){
        if(estadoGlobal != null)
            estadoGlobal.execute(myOwner);
        if(estadoAtual != null)
            estadoAtual.execute(myOwner);
    }

    public void changeState(State<Ghost> novoEstado){
        estadoAnterior = estadoAtual;
        estadoAtual.exit(myOwner);
        estadoAtual = novoEstado;
        estadoAtual.enter(myOwner);
    }

    public void revertToPreviousState(){changeState(estadoAnterior);}

    public State<Ghost> getCurrentState(){return estadoAtual;}

    public State<Ghost> getGlobalState(){return estadoGlobal;}

    public State<Ghost> getPreviousState(){return estadoAnterior;}

    public boolean handleMessage(Message msg){
        if(estadoAtual.onMessage(myOwner, msg))
            return true;
        if(estadoGlobal != null && estadoGlobal.onMessage(myOwner, msg))
            return true;
        return false;
    }

}
