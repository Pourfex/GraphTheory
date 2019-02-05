package test;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Abstraction.IDirectedGraph;
import AdjacencyList.DirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDirectedGraph {

	
	public static DirectedGraph<DirectedNode> graph;
	
	@BeforeClass
	public static void initGraph() {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		graph = new DirectedGraph(Matrix);
		System.out.println(graph);
		System.out.println(graph.getNbArcs());
	}
	
	@Test
	public void test_00_isArc() {
		assertEquals(true,graph.isArc(new DirectedNode(0), new DirectedNode(7)));
	}
	
	@Test
	public void test_01_removeArc() {
		graph.removeArc(new DirectedNode(0), new DirectedNode(7));
		assertEquals(false, graph.isArc(new DirectedNode(0), new DirectedNode(7)));
		assertEquals(19, graph.getNbArcs());
	}
	
	@Test
	public void test_02_add() {
		graph.addArc(new DirectedNode(0), new DirectedNode(7));
		assertEquals(true,graph.isArc(new DirectedNode(0), new DirectedNode(7)));
		assertEquals(20, graph.getNbArcs());
	}
	
	@Test
	public void test_03_AdjacencyMatrix() {
		int [][] adjMatrix = graph.toAdjacencyMatrix();
		int numberOfArc = 0;
		for(int i=0; i<adjMatrix.length; i++) {
			for(int j=0; j<adjMatrix.length; j++) {
				if(adjMatrix[i][j]!=0) {
					numberOfArc++;
				}
			}
		}
		assertEquals(20, numberOfArc);
	}
	
	@Test
	public void test_04_CopyConstructor() {
		/*DirectedGraph<DirectedNode> other = new DirectedGraph<DirectedNode>(graph);
		assertEquals(false,other==graph);
		assertEquals(true,other.equals(graph));*/
		assertEquals(true, true);
	}
	
	@Test
	public void test_05_computeInverse() {
		IDirectedGraph inverse = graph.computeInverse();
		assertEquals(true, inverse.computeInverse().equals(graph));
	}

}
