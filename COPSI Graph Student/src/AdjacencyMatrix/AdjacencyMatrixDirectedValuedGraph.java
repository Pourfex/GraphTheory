package AdjacencyMatrix;

import java.util.ArrayList;
import java.util.List;

import Abstraction.AbstractNode;
import Abstraction.IDirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	private  int[][] matrixCosts;	// The graph with Costs


	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedValuedGraph(int[][] mat, int[][] matrixVal) {
		super(mat);
		this.matrixCosts = matrixVal;
	}


	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------
	
	/**
	 * @return the matrix with costs of the graph
 	 */
	public int[][] getMatrixCosts() {
		return matrixCosts;
	}

	
	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------	
	
	/**
     * removes the arc (from,to) if there exists at least one between these nodes in the graph. And if there remains no arc, removes the cost.
     */
	@Override
	public void removeArc(AbstractNode from, AbstractNode to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(1) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		this.matrix[from.getLabel()][to.getLabel()] =0;
		this.matrixCosts[from.getLabel()][to.getLabel()] =0;
	}

	/**
     * adds the arc (from,to,cost), we allow the multi-graph. If there is already one initial cost, we keep it.
     */
	public void addArc(AbstractNode from, AbstractNode to, int cost ) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(1) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		this.matrix[from.getLabel()][to.getLabel()] = 1 ;
		this.matrixCosts[from.getLabel()][to.getLabel()] = cost ;
	}
	
	public String toString() {
		String s = super.toString()+"\n Matrix of Costs: \n";
		for(int i =0;i<this.matrixCosts.length;i++){
			for(int j =0;j<this.matrixCosts[i].length;j++){
				s+=this.matrixCosts[i][j]+" ";
			}
			s+="\n";
		}
		s+="\n";
		return s;
	}
	
	
	public static void main(String[] args) {
		int[][] matrix = GraphTools.generateGraphData(10, 30, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
		AdjacencyMatrixDirectedValuedGraph am = new AdjacencyMatrixDirectedValuedGraph(matrix, matrixValued);
		System.out.println(am);
		am.addArc(new DirectedNode(2), new DirectedNode(5), 13);
		am.addArc(new DirectedNode(2), new DirectedNode(7), 4); //we erase the existing arcs.
		System.out.println(am);
        System.out.println(am.isArc(new DirectedNode(2), new DirectedNode(5)));
        am.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(am);
	}

}
