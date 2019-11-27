package equipe03;

import equipe03.JamesGhostStates.StalkingPacman;
import equipe03.Message.Entity;
import pacman.Game;
import pacman.Move;

public class JamesGhost extends Entity {

    public StateMachine<JamesGhost> getStateMachine(){return stateMachine;}

    public JamesGhost(){
        super("James");

        stateMachine = new StateMachine<JamesGhost>(this);
        stateMachine.setCurrentState(StalkingPacman.getInstance());
    }

    @Override
    public Move chooseMove(Game game, int ghostIndex) {

        gameG = game;
        ghostIndexG = ghostIndex;

        stateMachine.getCurrentState().execute(this);

        return moveG.get(moveIndex);
    }
}
