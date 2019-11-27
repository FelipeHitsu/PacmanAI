package player_2019_1.Equipe06;

import java.util.ArrayList;
import java.util.List;

public class Node <T>{

	public T nData;//Dados armazenados

	public Node<T> parent = null;//Pai

	public int depth = 0;//Quantidade de pais / profundidade do no

	public double gCost = 0.0f;//O custo ate este no

	public double fCost = 0.0f;//O custo deste no

	public List<Node<T>> children = new ArrayList<>();//Lista de filhos

	//Adiciona filho
	public void AddChild(Node<T> node){
		node.depth = this.depth + 1;//Profundidade do filho 1 a mais que o pai deste

		node.parent = this;//Seta o no pai deste filho

		children.add(node);//Adiciona o filho na lista
	}
	
	//Percorre a arvore ate o primeiro no sem pai
	public Node<T> getFinalParent(){
		Node<T> n = this;
		while(n.parent != null)
			n = n.parent;
		return n;
	}
	
	//Desce ate o primeiro no que nao tem pai porem com limite de profundidade
	public Node<T> repathToDepth(int depth){
		Node<T> n = this;
		while(n.depth > depth)
			n = n.parent;
		return n;
	}
}
