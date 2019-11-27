package equipe03;

import equipe03.MeowthGhostStates.GuardingDot;
import equipe03.Message.Entity;
import pacman.Game;
import pacman.Move;

public class MeowthGhost extends Entity {

    public StateMachine<MeowthGhost> getStateMachine(){return stateMachine;}

    public MeowthGhost(){
        super("Meowth");

        stateMachine = new StateMachine<MeowthGhost>(this);
        stateMachine.setCurrentState(GuardingDot.getInstance());
    }

    @Override
    public Move chooseMove(Game game, int ghostIndex) {
        gameG = game;
        ghostIndexG = ghostIndex;

        stateMachine.getCurrentState().execute(this);

        return moveG.get(moveIndex);
    }
}
