package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import AdjacencyList.UndirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUndirectedGraph {

	public static UndirectedGraph<UndirectedNode> graph;

	@Before
	public void initGraph() {
		int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
		GraphTools.AfficherMatrix(mat);
		graph = new UndirectedGraph<UndirectedNode>(mat);
	}

	@Test
	public void test_01_CreationGraph() {
		assertEquals(20, graph.getNbEdges());
		assertEquals(10, graph.getNbNodes());
		System.out.println(graph);
	}

	@Test
	public void test_02_isEdge() {
		assertEquals(true, graph.isEdge(new UndirectedNode(0), new UndirectedNode(3)));
	}

	@Test
	public void test_03_removeEdge() {
		graph.removeEdge(new UndirectedNode(0), new UndirectedNode(3));
		// System.out.println(graph);
		assertEquals(19, graph.getNbEdges());
		assertEquals(false, graph.isEdge(new UndirectedNode(0), new UndirectedNode(3)));
	}

	@Test
	public void test_04_addEdge() {
		graph.addEdge(new UndirectedNode(0), new UndirectedNode(2));
		System.out.println(graph);
		assertEquals(21, graph.getNbEdges());
		assertEquals(true, graph.isEdge(new UndirectedNode(0), new UndirectedNode(2)));
	}

	@Test
	public void test_05_adjMatrix() {
		int nbEdges = 0;
		int[][] matrix = graph.toAdjacencyMatrix();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] != 0) {
					nbEdges++;
				}
			}
		}
		assertEquals(graph.getNbEdges() * 2, nbEdges);
	}

}
