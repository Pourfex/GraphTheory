package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class DirectedValuedGraph extends DirectedGraph<DirectedNode> {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public DirectedValuedGraph(int[][] matrixVal) {
		// A completer            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    
	/**
     * Removes the arc (from,to) with cost if it is present in the graph
     */
    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
    	// A completer
    }

    /**
     * Adds the arc (from,to) with cost  if it is not already present in the graph
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
    	// A completer      
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.AfficherMatrix(matrix);
        GraphTools.AfficherMatrix(matrixValued);
        DirectedValuedGraph al = new DirectedValuedGraph(matrixValued);
        System.out.println(al);
        //DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        //System.out.println(alInv);
        System.out.println(al.getNodes().get(2).getCosts());
        al.addArc(new DirectedNode(2), new DirectedNode(5), 13);
        al.addArc(new DirectedNode(2), new DirectedNode(7), 4);
        System.out.println(al);
        System.out.println(al.getNodes().get(2).getCosts());
        System.out.println(al.isArc(new DirectedNode(2), new DirectedNode(5)));
        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(al.getNodes().get(2).getCosts());
    }
	
}
