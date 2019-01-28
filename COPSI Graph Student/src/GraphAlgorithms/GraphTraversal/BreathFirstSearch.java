package GraphAlgorithms.GraphTraversal;

import Abstraction.AbstractNode;
import Abstraction.IGraph;
import AdjacencyList.DirectedGraph;
import AdjacencyList.UndirectedGraph;
import AdjacencyMatrix.AdjacencyMatrixDirectedGraph;
import AdjacencyMatrix.AdjacencyMatrixUndirectedGraph;
import GraphAlgorithms.GraphTools;

import java.util.*;

public class BreathFirstSearch {

    private IGraph graph;


    public BreathFirstSearch(IGraph graph){
        this.graph = graph;
    }


    public List<AbstractNode> exploreGraph(){

        List<AbstractNode> nodesVisited = new ArrayList<>();

        boolean[] mark = new boolean[graph.getNbNodes()];
        for(boolean b : mark){
            b = false;
        }

        Queue<AbstractNode> toVisit = new ArrayDeque<>();
        toVisit.add((AbstractNode) graph.getNodes().get(0));


        while(mark.length != nodesVisited.size()) { //To get a covering forest
            while (!toVisit.isEmpty()) {
                AbstractNode node = ((ArrayDeque<AbstractNode>) toVisit).pop();
                nodesVisited.add(node);
                mark[node.getLabel()] = true;
                for (AbstractNode neighbor : (List<AbstractNode>) graph.getNeighbors(node)) {
                    if (mark[neighbor.getLabel()] == false) {
                        mark[neighbor.getLabel()] = true;
                        ((ArrayDeque<AbstractNode>) toVisit).push(neighbor);
                    }
                }
            }

            for (int j = 0; j < mark.length; j++) { //we add the first unmarked node to the queue, not adding anything else
                if (!mark[j]) {
                    toVisit.add((AbstractNode) graph.getNodes().get(j));
                    break;
                }
            }
        }
        return nodesVisited;
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.AfficherMatrix(Matrix);


        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);

        System.out.println("Breath explore should give :" + "[node-0, node-3 , node-7, node-1, node-2, node-9, node-5, node-6, node-4, node-8]");

        /////////////////////////////////////////////////////////////DFS///////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////DFS - DIRECTED GRAPH///////////////////////////////////////////////////////////////////////


        BreathFirstSearch dfsDirectedGraph = new BreathFirstSearch(al);

        List<AbstractNode> nodesVisited = dfsDirectedGraph.exploreGraph();

        System.out.println(nodesVisited.toString());

        /*nodesVisited = dfsDirectedGraph.exploreGraph_rec();

        System.out.println(nodesVisited.toString());*/

        /////////////////////////////////////////////////////////////DFS - UNDIRECTED GRAPH///////////////////////////////////////////////////////////////////////

        UndirectedGraph undirectedGraph = new UndirectedGraph(Matrix);
        System.out.println(undirectedGraph);

        BreathFirstSearch dfsUndirectedGraph = new BreathFirstSearch(undirectedGraph);

        nodesVisited = dfsUndirectedGraph.exploreGraph();

        System.out.println(nodesVisited.toString());

        /*nodesVisited = dfsUndirectedGraph.exploreGraph_rec();

        System.out.println(nodesVisited.toString());*/

        /////////////////////////////////////////////////////////////DFS - DIRECTED MATRIX GRAPH///////////////////////////////////////////////////////////////////////

        AdjacencyMatrixDirectedGraph directedMatrixGraph = new AdjacencyMatrixDirectedGraph(Matrix);
        System.out.println(directedMatrixGraph);

        BreathFirstSearch dfsDirectedMatrixGraph = new BreathFirstSearch(directedMatrixGraph);

        nodesVisited = dfsDirectedMatrixGraph.exploreGraph();

        System.out.println(nodesVisited.toString());

        /*nodesVisited = dfsDirectedMatrixGraph.exploreGraph_rec();

        System.out.println(nodesVisited.toString());*/

        /////////////////////////////////////////////////////////////DFS - UNDIRECTED MATRIX GRAPH///////////////////////////////////////////////////////////////////////

        AdjacencyMatrixUndirectedGraph undirectedMatrixGraph = new AdjacencyMatrixUndirectedGraph(Matrix);
        System.out.println(undirectedMatrixGraph);

        BreathFirstSearch dfsUndirectedMatrixGraph = new BreathFirstSearch(undirectedMatrixGraph);

        nodesVisited = dfsUndirectedMatrixGraph.exploreGraph();

        System.out.println(nodesVisited.toString());

        /*nodesVisited = dfsUndirectedMatrixGraph.exploreGraph_rec();

        System.out.println(nodesVisited.toString());*/

    }
}
