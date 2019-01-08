package AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;


public class UndirectedGraph<A extends UndirectedNode> extends AbstractListGraph<A> implements IUndirectedGraph<A> {


    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public UndirectedGraph() {
		 this.nodes = new ArrayList<>();
	}
	
	public UndirectedGraph(List<A> nodes) {
        super(nodes);
        for (UndirectedNode i : nodes) {
            this.m += i.getNbNeigh();
        }
    }

    public UndirectedGraph(int[][] matrix) {
    	// A completer
    }

    public UndirectedGraph(UndirectedGraph<A> g) {
    	// A completer

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    @Override
    public int getNbEdges() {
        return this.m;
    }    

    @Override
    public List<A> getNeighbors(A x) {
        return this.getNodes().get(x.getLabel()).getNeighbors().stream().map(n -> (A)n).collect(Collectors.toList());
    }
    
    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    @Override
    public boolean isEdge(A x, A y) {
    	// A completer
    	return true;
    }

    @Override
    public void removeEdge(A x, A y) {
    	// A completer
    }

    @Override
    public void addEdge(A x, A y) {
    	// A completer
    }
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends UndirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A) new UndirectedNode(label);
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
    	// A completer
        return null;
    }

    
    @Override
    public String toString() {
        String s = "";
        for (UndirectedNode n : nodes) {
            s += "neighbors of " + n + " : ";
            for (UndirectedNode sn : n.getNeighbors()) {
                s += sn + " ";
            }
            s += "\n";
        }
        s += "\n";
        return s;
    }


    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.AfficherMatrix(mat);
        UndirectedGraph al = new UndirectedGraph(mat);
        System.out.println(al);
        al.addEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(al);
        System.out.println(al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        al.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(al);
    }

}
