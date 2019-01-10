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
        this.nodes = new ArrayList<A>();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Getting the length of the column (which is equal to lines..)
        //Adding the nodes to the nodes list;
	    for(int i=0; i<matrix[0].length;i++){
    	    nodes.add(makeNode(i));
	    }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n²) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    //When he have all nodes, you can put the preds and succs of it;
        for(int i=0; i<nodes.size();i++){
            List<A> tmp = new ArrayList<>();
            for(int j=0;j<nodes.size();j++){
                if(matrix[i][j] == 1){ //the ij element contains 1 if the i node contains the j nodes in his succs. this also means that the j node have the i node in his preds.
                    nodes.get(i).getSuccs().add(nodes.get(j));
                    nodes.get(j).getPreds().add(nodes.get(i));
                }
            }
        }

        this.order = nodes.size();
        this.m = 0; // don't know what this is about
    }

    public DirectedGraph(DirectedGraph<A> g) {
    	this.nodes = g.nodes;
    	this.m =g.m;
    	this.order = g.order;
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
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	return from.getSuccs().contains(to);
    }

    @Override
    public void removeArc(A from, A to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(from.getSuccs().contains(to)){
            from.getSuccs().remove(to);
            to.getPreds().remove(from);
        }
        this.order -= 1;
    }

    @Override
    public void addArc(A from, A to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(1) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        from.getSuccs().add(to);
        to.getPreds().add(from);
        this.order += 1;
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
