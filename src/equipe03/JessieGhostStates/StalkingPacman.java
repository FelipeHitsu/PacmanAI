package equipe03.JessieGhostStates;

import equipe03.Message.EntityManager;
import equipe03.Message.Message;
import equipe03.JessieGhost;
import equipe03.Message.MessageDispatcher;
import equipe03.State;
import pacman.Game;
import pacman.Location;
import util.Utils;

public class StalkingPacman implements State<JessieGhost> {

    private static StalkingPacman instance = null;

    private StalkingPacman(){}

    public static StalkingPacman getInstance(){
        if(instance == null)
            instance = new StalkingPacman();
        return instance;
    }

    public void enter(JessieGhost g){
        g.count = 0;
    }

    public void execute(JessieGhost g){
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
        if(g.count >= 100) {
            MessageDispatcher.getInstance().dispatchMessage(g, EntityManager.getInstance().getEntity("James"), "Cansei", null);
            g.getStateMachine().changeState(Walking.getInstance());
        }

    }

    public void exit(JessieGhost g){

    }

    public boolean onMessage(JessieGhost g, Message msg){
        return false;
    }

}
