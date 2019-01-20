package Abstraction;

import java.util.List;

public interface IGraph {
	/**
	 * @return the number of nodes in the graph (referred to as the order of the graph)
 	 */
	int getNbNodes();

	/**
	 * @return the adjacency matrix representation int[][] of the graph
 	 */
	int[][] toAdjacencyMatrix();

	/**
	 * @return the list of nodes in the graph
	 */
	List<?> getNodes();

	/**
	 * @return a List of nodes representing the neighbors of node x
	 */
	List<?> getNeighbors(Object x);

}
