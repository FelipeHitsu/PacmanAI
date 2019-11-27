package equipe03.JamesGhostStates;

import equipe03.JamesGhost;
import equipe03.Message.Message;
import equipe03.State;

import java.util.Random;

public class Walking implements State<JamesGhost>{

    public static Walking instance = null;

    public Walking(){}

    public static Walking getInstance(){
        if(instance == null)
            instance = new Walking();
        return instance;
    }

    @Override
    public void enter(JamesGhost g) {

    }

    @Override
    public void execute(JamesGhost g) {
        g.stateG = g.gameG.getCurrentState();
        g.moveG = g.gameG.getLegalGhostMoves(g.ghostIndexG);

        Random random = new Random();

        g.moveIndex = random.nextInt(g.moveG.size());

        g.count++;
    }

    @Override
    public void exit(JamesGhost g) {

    }

    @Override
    public boolean onMessage(JamesGhost g, Message msg) {
        if(msg.getMessage().compareToIgnoreCase("Cansei") == 0) {
            g.getStateMachine().changeState(StalkingPacman.getInstance());
            return true;
        }
        else
            return false;
    }
}
