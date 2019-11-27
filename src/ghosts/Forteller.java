package ghosts;

import pacman.*;
import util.Utils;

import java.awt.*;
import java.util.List;

public class Forteller extends GhostPlayer{

    @Override
    public void setColor(Color color) {
        super.setColor(color.BLACK);
    }

    public Move chooseMove(Game game, int ghostIndex) {
        State state = game.getCurrentState();
        List<Move> moves = game.getLegalGhostMoves(ghostIndex);
        if (moves.isEmpty())
            return null;
        Move mov = null;

        double[] distances = new double[moves.size()];
        Location dotLocation = state.getDotLocations().iterator().next();
        for (int i=0; i< distances.length; i++) {
            Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghostIndex), moves.get(i));
            distances[i] = Location.euclideanDistance(dotLocation, newLoc);
        }
        int moveIndex = Utils.argmin(distances);
        return moves.get(moveIndex);
    }
}
