package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import AdjacencyMatrix.AdjacencyMatrixUndirectedValuedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;

public class TestAdjacencyMatrixUndirectedValuedGraph {

	AdjacencyMatrixUndirectedValuedGraph graph;

	@Before
	public void init() {
		int[][] matrix = GraphTools.generateGraphData(10, 20, true, true, false, 100001);
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);

		graph = new AdjacencyMatrixUndirectedValuedGraph(matrix, matrixValued);

	}

	@Test
	public void test01_builder() {
		assertEquals(10, graph.getNbNodes());
		assertEquals(9, graph.getNbEdges());

		System.out.println(graph);
	}

	@Test
	public void test02_isArc() {
		assertTrue(graph.isEdge(new UndirectedNode(2), new UndirectedNode(3)));
	}

	@Test
	public void test03_removeEdge() {
		graph.removeEdge(new UndirectedNode(2), new UndirectedNode(3));
		assertFalse(graph.isEdge(new UndirectedNode(2), new UndirectedNode(3)));
		assertEquals(0, graph.getMatrixCosts()[2][3]);
		assertEquals(8, graph.getNbEdges());
	}

	@Test
	public void test04_addEdge() {
		graph.addEdge(new UndirectedNode(0), new UndirectedNode(1), 5);
		assertTrue(graph.isEdge(new UndirectedNode(0), new UndirectedNode(1)));
		assertEquals(10, graph.getNbEdges());
		assertEquals(5, graph.getMatrixCosts()[0][1]);

		graph.addEdge(new UndirectedNode(1), new UndirectedNode(3), 5);
		assertEquals(5, graph.getMatrixCosts()[1][3]);
	}

}
