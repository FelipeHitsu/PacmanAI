package player_2019_1.Equipe06;

import java.util.ArrayList;
import java.util.List;

import pacman.Game;
import pacman.Location;
import pacman.Move;
import pacman.State;

public class aStarAlgorithm {

	//DEBUG DAS ESCOLHAS COMENTADO DENTRO DO PRIMEIRO WHILE DO ALGORITMO

	static int Depth = 13; //Variavel que define a profundidade da busca
	public static Move getMove(Game g) {
		return getMove(g, Depth);
	} //Função de retorno do move escolhido
	
	
	public static Move getMove(Game g, int depth) {
		State s = g.getCurrentState();

		// Inicializa os primeiros nos a serem usados
		Node<AuxNode> actualNode = new Node<AuxNode>();
		actualNode.nData = new AuxNode();
		actualNode.nData.state = s;
		actualNode.nData.move = Move.NONE;
		actualNode.gCost = 0f;
		actualNode.fCost = 0f;

		Node<AuxNode> bestNode = choiceCost(actualNode, depth);

		return bestNode.repathToDepth(1).nData.move;
	}

	//Avaliação dos pesos das escolhas
	static Node<AuxNode> choiceCost(Node<AuxNode> parentNode, int depth) {
		List<Node<AuxNode>> aChecar = new ArrayList<>();

		// Adiciona o no pai ao checar 
		aChecar.add(parentNode);
		// Melhor no encontrado
		Node<AuxNode> bestNodeFound = new Node<AuxNode>();
		bestNodeFound.nData = new AuxNode();
		bestNodeFound.nData.state = parentNode.nData.state;
		bestNodeFound.nData.move = Move.NONE;
		bestNodeFound.gCost = 0f;
		bestNodeFound.fCost = Double.NEGATIVE_INFINITY;

		//Checagem dos nos enquanto lista nao nula
		while (aChecar.size() > 0) {
			Node<AuxNode> ActualNode = aChecar.get(getHighCostNode(aChecar));
			aChecar.remove(ActualNode);
			
			if(ActualNode.fCost < bestNodeFound.fCost) continue;
			System.out.println("Depth: " + ActualNode.depth + "   Node cost: " + ActualNode.fCost);
			if (ActualNode.depth == depth || Game.isFinal(ActualNode.nData.state)) {
				bestNodeFound = ActualNode.fCost > bestNodeFound.fCost ? ActualNode : bestNodeFound;
				continue;
			}

			List<Move> legalMoves = Game.getLegalPacManMoves(ActualNode.nData.state);
			for (Move m : legalMoves) {
				if (!Game.isFinal(ActualNode.nData.state)) {
					Node<AuxNode> nNode = new Node<AuxNode>();
					nNode.nData = new AuxNode();
					nNode.nData.state = Game.getNextState(ActualNode.nData.state, m);
					nNode.nData.move = m;
					nNode.gCost = ActualNode.gCost - 1.0f;
					if(isComingBack(nNode))
						nNode.gCost -= 15f;
					nNode.fCost = nNode.gCost + HeuristicEvaluation(nNode.nData.state);
					ActualNode.AddChild(nNode);
					// Se nao voltar adiciona novo node a checar
					if (nNode.depth <= depth){
						aChecar.add(nNode);
					}
				}
			}
		}
		return bestNodeFound;
	}

	static boolean isComingBack(Node<AuxNode> node) {
		Location actualPacmanLocation = node.nData.state.getPacManLocation();
		
		if (node.nData.state.getParent() == null)
			return false;
		if (node.nData.state.getParent().getParent() == null)
			return false;
		Location oldPacmanLocation = node.nData.state.getParent().getParent().getPacManLocation();
		return actualPacmanLocation.getX() == oldPacmanLocation.getX() && actualPacmanLocation.getY() == oldPacmanLocation.getY();
	}

	static int getHighCostNode(List<Node<AuxNode>> list) {
		double high = Double.NEGATIVE_INFINITY;
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).fCost > high) {
				high = list.get(i).fCost;
				index = i;
			}
		}
		return index;
	}

	static double HeuristicEvaluation(State state) {
		if (Game.isFinal(state)) {
			if (Game.isLosing(state))
				return -1000000f;
			if (Game.isWinning(state))
				return 10000000f; // really good to win
		}
		//Menos dots
		double evaluationResult = -state.getDotLocations().size() * 12f;
		//Proximidade ponto mais proximo
		evaluationResult -= closestDotDistance(state, state.getPacManLocation());
		//Proximidade media dos pontos
		evaluationResult -= averageDotsDistance(state, state.getPacManLocation()) * 2f;
		//Distancia media dos fantasmas
		evaluationResult += averageGhostDistance(state, state.getPacManLocation()) * 4f;
		//Distancia do fantasma mais proximo
		evaluationResult += closestGhostDistance(state, state.getPacManLocation()) * 2f;
		return evaluationResult;
	}

	static double closestDotDistance(State state, Location location) {
		double minimumDistance = Double.POSITIVE_INFINITY;

		for (Location l : state.getDotLocations()) {
			double dist = Location.euclideanDistance(location, l);
			if (dist < minimumDistance)
				minimumDistance = dist;
		}

		return minimumDistance;
	}
	
	static double averageDotsDistance(State state, Location location) {
		double averageDistance = 0f;

		for (Location l : state.getDotLocations()) {
			averageDistance += Location.euclideanDistance(location, l);
		}

		return averageDistance / state.getDotLocations().size();
	}

	static double closestGhostDistance(State state, Location location) {
		double closest = Double.POSITIVE_INFINITY;
		for (Location l : state.getGhostLocations()) {
			double s = Location.euclideanDistance(location, l);
			if (s < closest)
				closest = s;
		}
		return closest;
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

	static double lessRemainingDots(State state){
		double lessDots = state.getDotLocations().size();
		return lessDots;
	}
	
	static double averageGhostDistance(State state, Location location) {
		double average = 0f;

		for (Location l : state.getGhostLocations()) {
			average += Location.euclideanDistance(location, l);
		}

		return average / state.getGhostLocations().size();
	}
}
