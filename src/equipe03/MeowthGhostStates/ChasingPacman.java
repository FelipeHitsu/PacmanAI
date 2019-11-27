package equipe03.MeowthGhostStates;

import equipe03.MeowthGhost;
import equipe03.Message.Message;
import equipe03.State;
import pacman.Game;
import pacman.Location;
import util.Utils;
import equipe03.Message.MessageDispatcher;
import equipe03.Message.EntityManager;

public class ChasingPacman implements State<MeowthGhost> {

    public static ChasingPacman instance = null;

    public ChasingPacman(){}

    public static ChasingPacman getInstance(){
        if(instance == null)
            instance = new ChasingPacman();
        return instance;
    }

    @Override
    public void enter(MeowthGhost g) {
        g.count = 0;
    }

    @Override
    public void execute(MeowthGhost g) {
        g.stateG = g.gameG.getCurrentState();
        g.moveG = g.gameG.getLegalGhostMoves(g.ghostIndexG);
        double[] distances = new double[g.moveG.size()];
        Location pacManLoc = g.stateG.getPacManLocation();
        for (int i = 0; i < distances.length; i++) {
            Location newLoc = Game.getNextLocation(g.stateG.getGhostLocations().get(g.ghostIndexG), g.moveG.get(i));
            distances[i] = Location.euclideanDistance(pacManLoc, newLoc);
        }
        g.moveIndex = Utils.argmin(distances);

        g.count++;

        if(g.count >= 15) {
            g.getStateMachine().changeState(GuardingDot.getInstance());
            MessageDispatcher.getInstance().dispatchMessage(g, EntityManager.getInstance().getEntity("Zornik"), "Run", null);
        }
    }

    @Override
    public void exit(MeowthGhost g) {

    }

    @Override
    public boolean onMessage(MeowthGhost g, Message msg) {
        return false;
    }
}
