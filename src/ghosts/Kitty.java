package ghosts;

import pacman.*;
import util.Utils;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Kitty extends GhostPlayer {

    @Override
    public void setColor(Color color) {
        super.setColor(color.BLACK);
    }

    @Override
    public Move chooseMove(Game game, int ghostIndex) {

        Random random = new Random();
        Location NewPacLoc = null;
        State state = game.getCurrentState();
        List<Move> moves = game.getLegalGhostMoves(ghostIndex);
        List<Move> pmoves = game.getLegalPacManMoves();

        for (Move m: pmoves){
            List<State> stateList = Game.getProjectedStates(state,m);
            State s = stateList.get(stateList.size()-1);
            NewPacLoc = s.getPacManLocation();
        }


        if (moves.isEmpty()) return null;
        double[] distances = new double[moves.size()];
        Location pacManLoc = state.getPacManLocation();
        for (int i=0; i<distances.length; i++) {
            Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghostIndex), moves.get(i));
            distances[i] = Location.euclideanDistance(NewPacLoc, newLoc);
        }
        int moveIndex = Utils.argmin(distances); // the move that minimizes the distance to PacMan
        return moves.get(moveIndex);

    }
}
