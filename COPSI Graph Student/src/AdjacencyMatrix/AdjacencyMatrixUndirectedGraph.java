package AdjacencyMatrix;
import Abstraction.AbstractMatrixGraph;
import Abstraction.AbstractNode;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * This class represents the undirected graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixUndirectedGraph extends AbstractMatrixGraph<UndirectedNode> implements IUndirectedGraph {
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 
	
	public AdjacencyMatrixUndirectedGraph() {
		super();
	}
	
	public AdjacencyMatrixUndirectedGraph(int[][] mat) {
		this.order=mat.length;
		this.matrix = new int[this.order][this.order];
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// O(n²) algorithm
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		for(int i =0;i<this.order;i++){
			for(int j=i;j<this.order;j++){
				int val = mat[i][j];
				this.matrix[i][j] = val;
				this.matrix[j][i] = val;
				this.m += val;	 
			}
		}	
	}
	
	public AdjacencyMatrixUndirectedGraph(IUndirectedGraph g) {
		this.order = g.getNbNodes(); 				
		this.m = g.getNbEdges(); 				
		this.matrix = g.toAdjacencyMatrix(); 
	}
	

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------
	

	@Override
	public int getNbEdges() {
		return this.m;
	}

	@Override
	public List<AbstractNode> getNeighbors(AbstractNode x) {
		List<AbstractNode> l = new ArrayList<AbstractNode>();
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// O(n) algorithm
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		for(int i =0;i<matrix[x.getLabel()].length;i++){
			if(matrix[x.getLabel()][i]>0){
				l.add((AbstractNode) getNodes().get(i));
			}
		}
		return l;		
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	@Override
	public boolean isEdge(AbstractNode x, AbstractNode y) {
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// O(1) algorithm
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		return this.matrix[x.getLabel()][y.getLabel()] > 0;
	}
	
	/**
     * removes the edge (x,y) if there exists at least one between these nodes in the graph.
     */
	@Override
	public void removeEdge(AbstractNode x, AbstractNode y) {
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// O(1) algorithm
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		this.matrix[x.getLabel()][y.getLabel()] = 0;
		this.matrix[y.getLabel()][x.getLabel()] = 0;
	}

	/**
     * adds the edge (x,y), we allow the multi-graph.
     */
	@Override
	public void addEdge(AbstractNode x, AbstractNode y) {
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// O(1) algorithm
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		this.matrix[x.getLabel()][y.getLabel()] = 1;
		this.matrix[y.getLabel()][x.getLabel()] = 1;
	}


	/**
     * @return the adjacency matrix representation int[][] of the graph
     */
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}

	public UndirectedNode makeNode(int label) {
		return new UndirectedNode(label);
	}

	@Override
	public List<?> getNodes() {
		if(nodes == null){
			nodes =new ArrayList<>();
			for(int i=0; i<matrix[0].length; i++){
				nodes.add(makeNode(i));
			}
		}
		return nodes;
	}

	@Override
	public String toString() {
		String s = "Adjacency Matrix: \n";
		for(int i =0;i<this.matrix.length;i++){
			for(int j =0;j<this.matrix[i].length;j++){
				s+=this.matrix[i][j]+" ";
			}
			s+="\n";
		}
		s+="\n";
		return s;
	}
	
	
	public static void main(String[] args) {
		int[][] mat2 = GraphTools.generateGraphData(10, 35, false, true, false, 100001);
		GraphTools.AfficherMatrix(mat2);
		AdjacencyMatrixUndirectedGraph am = new AdjacencyMatrixUndirectedGraph(mat2);
		System.out.println(am);
		System.out.println("N = "+am.getNbNodes()+ "\n M = "+am.getNbEdges());
		List<AbstractNode> t2 = am.getNeighbors(new UndirectedNode(2));
		for(int i =0;i<t2.size();i++)
			System.out.print(t2.get(i).getLabel()+", ");
		System.out.println("\n");
		am.addEdge(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println(am);
	    System.out.println("IsEdge Node 2 to Node 5 should be true : " + am.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
	    am.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println("IsEdge Node 2 to Node 5 should be false : " + am.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
		System.out.println(am);

	}

	

}
