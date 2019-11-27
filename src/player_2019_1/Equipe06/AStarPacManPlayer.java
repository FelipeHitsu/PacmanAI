package player_2019_1.Equipe06;

import pacman.Game;
import pacman.Move;
import pacman.State;
import player.DFSPacManPlayer;

public class AStarPacManPlayer extends DFSPacManPlayer {
	@Override
	public Move chooseMove(Game game) {
		return aStarAlgorithm.getMove(game);
	}

	@Override
	public double evaluateState(State state) {
		if (Game.isLosing(state))
			return Double.NEGATIVE_INFINITY;
		return -state.getDotLocations().size();
	}
}
