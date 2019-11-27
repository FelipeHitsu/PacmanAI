package equipe03.MeowthGhostStates;

import equipe03.MeowthGhost;
import equipe03.Message.Message;
import equipe03.State;
import pacman.Game;
import pacman.Location;
import util.Utils;

import java.util.List;
import java.util.Random;

public class GuardingDot implements State<MeowthGhost> {

    public static GuardingDot instance = null;

    public GuardingDot(){}

    public static GuardingDot getInstance(){
        if(instance == null)
            instance = new GuardingDot();
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
        List<Location> dotLocations = g.stateG.getDotLocations().list();

        Random rand = new Random();
        Location choosenDot = dotLocations.get(rand.nextInt(dotLocations.size()));

        for (int i = 0; i < distances.length; i++) {
            Location newLoc = Game.getNextLocation(g.stateG.getGhostLocations().get(g.ghostIndexG), g.moveG.get(i));
            distances[i] = Location.euclideanDistance(choosenDot, newLoc);
        }
        g.moveIndex = Utils.argmin(distances);

        g.count++;

        if(g.count >= 50)
            g.getStateMachine().changeState(ChasingPacman.getInstance());
    }

    @Override
    public void exit(MeowthGhost g) {

    }

    @Override
    public boolean onMessage(MeowthGhost g, Message msg) {
        return false;
    }
}
