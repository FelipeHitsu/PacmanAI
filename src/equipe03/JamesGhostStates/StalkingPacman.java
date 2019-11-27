package equipe03.JamesGhostStates;

import equipe03.JamesGhost;
import equipe03.Message.EntityManager;
import equipe03.Message.Message;
import equipe03.Message.MessageDispatcher;
import equipe03.State;
import pacman.Game;
import pacman.Location;
import util.Utils;

public class StalkingPacman implements State<JamesGhost> {

    public static StalkingPacman instance = null;

    public StalkingPacman(){}

    public static StalkingPacman getInstance(){
        if(instance == null)
            instance = new StalkingPacman();
        return instance;
    }

    @Override
    public void enter(JamesGhost g) {
        g.count = 0;
    }

    @Override
    public void execute(JamesGhost g) {
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
        if(g.count >= 50) {
            MessageDispatcher.getInstance().dispatchMessage(g, EntityManager.getInstance().getEntity("Jessie"), "Cansei", null);
            g.getStateMachine().changeState(Walking.getInstance());
        }

    }

    @Override
    public void exit(JamesGhost g) {

    }

    @Override
    public boolean onMessage(JamesGhost g, Message msg) {
        return false;
    }
}
