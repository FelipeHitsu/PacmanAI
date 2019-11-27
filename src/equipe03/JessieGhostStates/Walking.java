package equipe03.JessieGhostStates;

import equipe03.Message.Message;
import equipe03.JessieGhost;
import equipe03.State;

import java.util.Random;


public class Walking implements State<JessieGhost> {

    public static Walking instance = null;

    public Walking(){}

    public static Walking getInstance(){
        if(instance == null)
            instance = new Walking();
        return instance;
    }

    @Override
    public void enter(JessieGhost g) {
        g.count = 0;
    }

    @Override
    public void execute(JessieGhost g) {
        g.stateG = g.gameG.getCurrentState();
        g.moveG = g.gameG.getLegalGhostMoves(g.ghostIndexG);

        Random random = new Random();

        g.moveIndex = random.nextInt(g.moveG.size());


        g.count++;
    }

    @Override
    public void exit(JessieGhost g) {
        System.out.println("Vo a�asinar o paquim�\n");
    }

    @Override
    public boolean onMessage(JessieGhost g, Message msg) {
        if(msg.getMessage().compareToIgnoreCase("Cansei") == 0) {
            g.getStateMachine().changeState(StalkingPacman.getInstance());
            return true;
        }
        else
            return false;
    }
}
