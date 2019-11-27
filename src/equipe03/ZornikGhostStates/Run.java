package equipe03.ZornikGhostStates;

import equipe03.ZornikGhost;
import equipe03.Message.Message;
import equipe03.State;
import pacman.Game;
import pacman.Location;
import util.Utils;

import java.util.List;

public class Run implements State<ZornikGhost> {
    public static Run instance = null;


    public Run() {
    }

    public static Run getInstance() {
        if (instance == null)
            instance = new Run();
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

        List<Location> dotLocations = g.stateG.getDotLocations().list();

        Location DotPac = dotLocations.get(dotLocations.size()-1);

        double[] distances = new double[g.moveG.size()];
        for (int i = 0; i < distances.length; i++) {
            Location newLoc = Game.getNextLocation(g.stateG.getGhostLocations().get(g.ghostIndexG), g.moveG.get(i));
            distances[i] = Location.euclideanDistance(pacManLoc, DotPac);
        }
        g.moveIndex = Utils.argmin(distances);

        g.count++;
        if (g.count >= 35)
            g.getStateMachine().changeState(Walking.getInstance());
    }



    @Override
    public void exit(ZornikGhost g) {

    }

    @Override
    public boolean onMessage(ZornikGhost g, Message msg) {
        return false;
    }
}
