package player_2019_1.Equipe06;

import java.util.ArrayList;
import java.util.List;

import pacman.Game;
import pacman.Location;
import pacman.Move;
import pacman.State;

public class BreadthFirstSearchAlgorithm {

	static int Depth = 13; //Variavel de definição da profundidade da busca

	// Pega o movimento com a Depth definida
	public static Move getMove(Game g) {
		return getMove(g, Depth);
	}

	// Retorna o melhor movimento encontrado
	public static Move getMove(Game g, int depth) {
		State s = g.getCurrentState();

		Move returnMove = Move.NONE;
		List<Node<AuxNode>> NotVisited = new ArrayList<>();
		List<Node<AuxNode>> Final = new ArrayList<>();

		List<Move> legalMoves = g.getLegalPacManMoves();
		for (Move m : legalMoves) {
			Node<AuxNode> nNode = new Node<AuxNode>();
			nNode.nData = new AuxNode();
			nNode.nData.state = Game.getNextState(s, m);
			nNode.nData.move = m;
			NotVisited.add(nNode);
		}

		for (int i = 0; i < NotVisited.size(); i++) {
			legalMoves = Game.getLegalPacManMoves(NotVisited.get(i).nData.state);
			if (!Game.isFinal(NotVisited.get(i).nData.state) && NotVisited.get(i).depth < depth) {
				for (Move m : legalMoves) {
					Node<AuxNode> nNode = new Node<AuxNode>();
					nNode.nData = new AuxNode();
					nNode.nData.state = Game.getNextState(NotVisited.get(i).nData.state, m);
					nNode.nData.move = m;
					if (!isComingBack(nNode)) {
						NotVisited.get(i).AddChild(nNode);// Adiciona mais um no na arvore
						NotVisited.add(nNode);
					}
				}
			} else {
				NotVisited.get(i).nData.value = HeuristicEvaluation(NotVisited.get(i).nData.state);
				Final.add(NotVisited.get(i));
			}
		}
		// Função de escolha do melhor  movimento
		double bestScore = Double.NEGATIVE_INFINITY;
		for (Node<AuxNode> aX : Final) {
			if (aX.nData.value > bestScore) {
				Node<AuxNode> n = aX;
				while (n.parent != null) {
					n = n.parent;
				}
				returnMove = n.nData.move;
				bestScore = aX.nData.value;
			}
		}
		return returnMove;
	}

	static double HeuristicEvaluation(State state) {
		if (Game.isFinal(state)) {
			if (Game.isLosing(state))
				return Double.NEGATIVE_INFINITY;
			if (Game.isWinning(state))
				return Double.POSITIVE_INFINITY;
		}
		// Menos dots
		double evaluationResult = -state.getDotLocations().size() * 8f;
		// Proximidade ponto mais proximo
		evaluationResult -= closestDotDistance(state, state.getPacManLocation()) *2;
		// Mais pontos perto
		evaluationResult -= averageDotDistance(state, state.getPacManLocation()) * 2f;
		// Longe de todos os fantasmas
		evaluationResult += averageGhostDistance(state, state.getPacManLocation()) * 6f;
		// Distancia do fantasma mais proximo
		evaluationResult += closestGhostDistance(state, state.getPacManLocation()) * 8f;
		return evaluationResult;
	}

	static boolean isComingBack(Node<AuxNode> node) {
		Location actualPacmanLocation = node.nData.state.getPacManLocation();

		if (node.depth <= 1)
			return false;
		if (node.nData.state.getParent() == null)
			return false;
		if (node.nData.state.getParent().getParent() == null)
			return false;
		Location oldPacmanLocation = node.nData.state.getParent().getParent().getPacManLocation();
		return actualPacmanLocation.getX() == oldPacmanLocation.getX() && actualPacmanLocation.getY() == oldPacmanLocation.getY();
	}

	static double closestDotDistance(State state, Location location) {
		double closest = Double.POSITIVE_INFINITY;

		for (Location l : state.getDotLocations()) {
			double distance = Location.euclideanDistance(location, l);
			if (distance < closest)
				closest = distance;
		}

		return closest;
	}

	static double averageDotDistance(State state, Location location) {
		double average = 0f;

		for (Location l : state.getDotLocations()) {
			average += Location.euclideanDistance(location, l);
		}

		return average / state.getDotLocations().size();
	}

	static double closestGhostDistance(State state, Location location) {
		double closestGhost = Double.POSITIVE_INFINITY;
		for (Location l : state.getGhostLocations()) {
			double s = Location.euclideanDistance(location, l);
			if (s < closestGhost)
				closestGhost = s;
		}
		return closestGhost;
	}

	static double farGhostDistance(State state, Location location){
		double farGhost = Double.NEGATIVE_INFINITY;
		for (Location l : state.getGhostLocations()){
			double s = Location.euclideanDistance(location,l);
			if(s > farGhost)
				farGhost = s;
		}
		return farGhost;
	}

	static double averageGhostDistance(State state, Location location) {
		double average = 0f;

		for (Location l : state.getGhostLocations()) {
			average += Location.euclideanDistance(location, l);
		}

		return average / state.getGhostLocations().size();
	}
}
