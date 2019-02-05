package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import AdjacencyMatrix.AdjacencyMatrixDirectedValuedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAdjacencyMatrixDirectedValuedGraph {
	AdjacencyMatrixDirectedValuedGraph graph;

	@Before
	public void init() {
		int[][] matrix = GraphTools.generateGraphData(10, 30, false, false, false, 100001);
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
		graph = new AdjacencyMatrixDirectedValuedGraph(matrix, matrixValued);
	}

	@Test
	public void test01_testConstructeur() {
		assertEquals(30, graph.getNbArcs());
		assertEquals(10, graph.getNbNodes());
		System.out.println(graph);

	}

	@Test
	public void test02_isArc() {
		assertTrue(graph.isArc(new DirectedNode(2), new DirectedNode(0)));
	}

	@Test
	public void test03_removeArc() {
		graph.removeArc(new DirectedNode(2), new DirectedNode(0));
		assertFalse(graph.isArc(new DirectedNode(2), new DirectedNode(0)));
	}

	@Test
	public void test04_addArc_removeArc() {
		graph.addArc(new DirectedNode(0), new DirectedNode(1), 5);
		assertTrue(graph.isArc(new DirectedNode(0), new DirectedNode(1)));
		assertEquals(5, graph.getMatrixCosts()[0][1]);
		graph.addArc(new DirectedNode(0), new DirectedNode(1), 10);
		assertEquals(10, graph.getMatrixCosts()[0][1]);

		graph.removeArc(new DirectedNode(0), new DirectedNode(1));
		assertFalse(graph.isArc(new DirectedNode(0), new DirectedNode(1)));
		assertEquals(0, graph.getMatrixCosts()[0][1]);
		graph.removeArc(new DirectedNode(0), new DirectedNode(1));
		assertFalse(graph.isArc(new DirectedNode(0), new DirectedNode(1)));
		assertEquals(0, graph.getMatrixCosts()[0][1]);
		graph.removeArc(new DirectedNode(0), new DirectedNode(1));

	}

}
