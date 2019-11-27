package player_2019_1.Equipe06;

import pacman.*;
import util.Counter;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GreedyPacManPlayer implements PacManPlayer {

    //DEBUG DO MOVIMENTO ESCOLHIDO JUNTO AO RETURN MOVE NA FUNÇÃO CHOOSE MOVE

    static Random random = new Random();

    private Move lastMove = null;

    /**
     * This is the only public method, required by the PacManPlayer interface.
     */
    public Move chooseMove(Game game) {

        State s = game.getCurrentState();
        List<Move> legalMoves = game.getLegalPacManMoves();
        Counter<Move> scores = new Counter<Move>();

        for (Move m : legalMoves) {

            List<State> stateList = Game.getProjectedStates(s, m);
            State last = stateList.get(stateList.size() - 1);

            double stateScore = evaluateState(s, last);
            double turnaroundPenalty = (lastMove == m.getOpposite() ? -10.0 : 0.0);


            scores.setCount(m, stateScore + turnaroundPenalty);
        }

        scores = scores.exp().normalize();
        Move move = scores.sampleFromDistribution(random);

        //System.out.println(move);
        lastMove = move;
        return move;
    }

    public double evaluateState(State s, State next) {

        if (Game.isLosing(next))
            return -1000.0;
        if (Game.isWinning(next))
            return 0.0;

        double score = 0.0;
        Location pacManLocation = next.getPacManLocation();

        // Pontos restantes no tabuleiro
        score -= s.getDotLocations().size();

        // Distancia do ponto mais proximo
        score -= getMinDistance(pacManLocation, s.getDotLocations());

        // Distancia do fantasma mais proximo
        score += getMinDistance(pacManLocation, s.getGhostLocations());

        return score;
    }

    /**
     * Returns the distance from the source location to the closest of the
     * target locations.
     *
     * @param sourceLocation
     * @param targetLocations
     * @return
     */

    private double getMinDistance(Location sourceLocation, Collection<Location> targetLocations) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (Location dotLocation : targetLocations) {

            double distance = Location.manhattanDistance(sourceLocation, dotLocation);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        if (Double.isInfinite(minDistance))
            throw new RuntimeException();
        return minDistance;
    }

}
