package AdjacencyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import Collection.Pair;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;


public class DirectedGraph<A extends DirectedNode> extends AbstractListGraph<A> implements IDirectedGraph<A> {

	//private static int _DEBBUG =1;
	private static int _DEBBUG =0;

		
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public DirectedGraph(){
		super();
		this.nodes = new ArrayList<A>();
	}

    public DirectedGraph(int[][] matrix) {
        /*this.nodes = new ArrayList<A>();

	    for(int i=0; i<nodes.size();i++){
    	    A node = makeNode(i);
	    }

        for(int i=0; i<nodes.size();i++){
            A node = makeNode(i);
        }*/


    }

    public DirectedGraph(DirectedGraph<A> g) {
    	// A completer

    }

    // ------------------------------------------
    // 		Accessors
    // ------------------------------------------
    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public List<A> getSuccessors(A x) {
        return this.getNodes().get(x.getLabel()).getSuccs().stream().map(n -> (A)n).collect(Collectors.toList());
    }

    @Override
    public List<A> getPredecessors(A x) {
        return this.getNodes().get(x.getLabel()).getPreds().stream().map(n -> (A)n).collect(Collectors.toList());
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    
    @Override
    public boolean isArc(A from, A to) {
    	return from.getSuccs().contains(to);
    }

    @Override
    public void removeArc(A from, A to) {
        if(from.getSuccs().contains(to)){
            from.getSuccs().remove(to);
            to.getPreds().remove(from);
        }
    }

    @Override
    public void addArc(A from, A to) {
        from.getSuccs().add(to);
        to.getPreds().add(from);
    }
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A)new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public A getNodeOfList(A src) {
        return this.getNodes().get(src.getLabel());
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
    public IDirectedGraph computeInverse() {
    	// A completer
        return null;
    }
    
 	
    @Override
    public String toString(){
        String s = "";
        for(DirectedNode n : nodes){
            s+="successors of "+n+" : ";
            for(DirectedNode sn : n.getSuccs()){
                s+=sn+" ";
            }
            s+="\n";
        }
        s+="\n";
        return s;
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.AfficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);
        DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        /*System.out.println(alInv);
        al.addArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(al);
        System.out.println(al.isArc(new DirectedNode(2), new DirectedNode(5)));
        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(al);*/
        
       
    }
}
