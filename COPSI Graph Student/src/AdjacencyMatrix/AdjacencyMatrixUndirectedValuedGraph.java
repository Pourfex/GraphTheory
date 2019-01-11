package AdjacencyMatrix;

import java.util.ArrayList;
import java.util.List;

import Abstraction.AbstractNode;
import Abstraction.IUndirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class AdjacencyMatrixUndirectedValuedGraph extends AdjacencyMatrixUndirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	private  int[][] matrixCosts;	// The graph with Costs


	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixUndirectedValuedGraph(int[][] mat, int[][] matrixVal) {
		super();
		this.order = mat.length;
		this.matrix = new int[this.order][this.order];
		this.matrixCosts = new int[this.order][this.order];
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// O(n²) algorithm
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		for(int i =0;i<this.order;i++){
			for(int j=i;j<this.order;j++){
				int val = mat[i][j];
				int cost = matrixVal[i][j]; 
				this.matrix[i][j] = val;
				this.matrix[j][i] = val;
				this.matrixCosts[i][j] = cost;
				this.matrixCosts[j][i] = cost; 
				this.m += val;					
			}
		}
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
     * removes the edge (x,y) if there exists at least one between these nodes in the graph. And if there remains no arc, removes the cost.
     */
	@Override
	public void removeEdge(AbstractNode x, AbstractNode y) {
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// O(1) algorithm
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		this.matrix[x.getLabel()][y.getLabel()] = 0;
		this.matrix[x.getLabel()][y.getLabel()] = 0;
		this.matrixCosts[x.getLabel()][y.getLabel()] = 0;
		this.matrixCosts[x.getLabel()][y.getLabel()] = 0;
	}

	/**
     * adds the edge (x,y,cost), we allow the multi-graph. If there is already one initial cost, we keep it.
     */
	public void addEdge(AbstractNode x, AbstractNode y, int cost ) {
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// O(1) algorithm
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		this.matrix[x.getLabel()][y.getLabel()] = 1;
		this.matrix[x.getLabel()][y.getLabel()] = 1;
		this.matrixCosts[x.getLabel()][y.getLabel()] = cost;
		this.matrixCosts[x.getLabel()][y.getLabel()] = cost;
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
		int[][] matrix = GraphTools.generateGraphData(10, 20, true, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		GraphTools.AfficherMatrix(matrix);
		AdjacencyMatrixUndirectedValuedGraph am = new AdjacencyMatrixUndirectedValuedGraph(matrix, matrixValued);
		System.out.println(am);
		am.addEdge(new UndirectedNode(2), new UndirectedNode(5), 13);
		am.addEdge(new UndirectedNode(2), new UndirectedNode(7), 4);
		System.out.println(am);
        System.out.println(am.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        am.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(am);
	}

}
