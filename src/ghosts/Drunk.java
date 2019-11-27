package ghosts;

import pacman.*;
import util.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Drunk extends GhostPlayer {

    public void setColor(Color color) {
        super.setColor(color.BLACK);
    }

    public Move chooseMove(Game game, int ghostIndex) {

        Random random = new Random();
        State state = game.getCurrentState();
        List<Move> moves = game.getLegalGhostMoves(ghostIndex);
        if (moves.isEmpty())
            return null;

        double[] distances = new double[moves.size()];
        Location dotLocation = state.getDotLocations().iterator().next();
        Location pacmanLocation = state.getPacManLocation();
        for (int i=0; i< distances.length; i++) {
            Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghostIndex), moves.get(i));
            distances[i] = Location.euclideanDistance(pacmanLocation, newLoc) + Location.euclideanDistance(dotLocation, newLoc);
        }
        int moveIndex = Utils.argmin(distances);
        return moves.get(moveIndex);
    }

}

