package equipe03.ZornikGhostStates;

import equipe03.ZornikGhost;
import equipe03.Message.Message;
import equipe03.State;
import pacman.Game;
import pacman.Location;
import util.Utils;

public class Walking implements State<ZornikGhost> {

    public static Walking instance = null;
    public Walking(){}

    public static Walking getInstance(){
        if(instance == null)
            instance = new Walking();
        return instance;
    }
    @Override
    public void enter(ZornikGhost g) {

    }
    @Override
    public void execute(ZornikGhost g) {
        g.stateG = g.gameG.getCurrentState();
        g.moveG = g.gameG.getLegalGhostMoves(g.ghostIndexG);
        Location pacManLoc = g.stateG.getPacManLocation();
        double[] distances = new double[g.moveG.size()];
        for (int i = 0; i < distances.length; i++) {
            Location newLoc = Game.getNextLocation(g.stateG.getGhostLocations().get(g.ghostIndexG), g.moveG.get(i));
            distances[i] = Location.euclideanDistance(pacManLoc, newLoc);
        }
        g.moveIndex = Utils.argmin(distances);
      //  if (g.count >= 25)
      //      g.getStateMachine().changeState(Run.getInstance());


    }

    @Override
    public void exit(ZornikGhost g) {

    }

    @Override
    public boolean onMessage(ZornikGhost g, Message msg){
 if(msg.getMessage().compareToIgnoreCase("Run") == 0) {
        g.getStateMachine().changeState(Run.getInstance());
        return true;
   }
    else
                return false;
    }
}



