package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import AdjacencyList.UndirectedValuedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;

public class TestUndirectedValuedGraph {

	UndirectedValuedGraph graph;
	int[][] matrixValued;

	@Before
	public void init() {
		matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		graph = new UndirectedValuedGraph(matrixValued);
	}

	public int compterEdgesFromMatrix() {
		int nbEdges = 0;
		for (int i = 0; i < matrixValued.length; i++) {
			for (int j = 0; j < matrixValued.length; j++) {
				if (matrixValued[i][j] != 0) {
					nbEdges++;
				}
			}
		}
		return nbEdges;
	}

	@Test
	public void test_build() {
		assertEquals(matrixValued.length, graph.getNbNodes());
		int nbEdges = compterEdgesFromMatrix();
		assertEquals(nbEdges/2, graph.getNbEdges());
	}

	@Test
	public void test_remove() {
		int oldNbEdges = graph.getNbEdges();
		assertTrue(graph.isEdge(new UndirectedNode(0), new UndirectedNode(2)));
		graph.removeEdge(new UndirectedNode(0), new UndirectedNode(2));
		assertFalse(graph.isEdge(new UndirectedNode(0), new UndirectedNode(2)));
		assertEquals(oldNbEdges -1 , graph.getNbEdges());
	}

	@Test
	public void test_add() {
		int oldNbEdges = graph.getNbEdges();
		assertFalse(graph.isEdge(new UndirectedNode(0), new UndirectedNode(3)));
		graph.addEdge(new UndirectedNode(0), new UndirectedNode(3));
		assertTrue(graph.isEdge(new UndirectedNode(0), new UndirectedNode(3)));
		assertEquals(oldNbEdges + 1, graph.getNbEdges());
	}
}
