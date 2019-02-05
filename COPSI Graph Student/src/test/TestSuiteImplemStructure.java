package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestUndirectedGraph.class, TestUndirectedValuedGraph.class, TestDirectedGraph.class,
		TestAdjacencyMatrixUndirectedValuedGraph.class, TestAdjacencyMatrixDirectedValuedGraph.class })
public class TestSuiteImplemStructure {

}
