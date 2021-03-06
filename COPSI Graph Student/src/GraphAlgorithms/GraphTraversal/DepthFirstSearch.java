package GraphAlgorithms.GraphTraversal;

import Abstraction.AbstractNode;
import Abstraction.IGraph;
import AdjacencyList.DirectedGraph;
import AdjacencyList.UndirectedGraph;
import AdjacencyMatrix.AdjacencyMatrixDirectedGraph;
import AdjacencyMatrix.AdjacencyMatrixUndirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

import java.util.*;

public class DepthFirstSearch {

    private IGraph graph;


    public DepthFirstSearch(IGraph graph){
        this.graph = graph;
    }

    public Set<AbstractNode> exploreGraph_rec(){
        Set<AbstractNode> nodesVisited = new HashSet<>();
        for(int i=0; i<graph.getNbNodes(); i++){
            if(!nodesVisited.contains(graph.getNodes().get(i))){
                exploreNode((AbstractNode) graph.getNodes().get(i), nodesVisited);
            }
        }
        return nodesVisited;
    }

    private void exploreNode(AbstractNode node, Set<AbstractNode> nodesVisited) {
        nodesVisited.add(node);
        for(AbstractNode neighbor : (List<AbstractNode>) graph.getNeighbors(node)){
            if(!nodesVisited.contains(neighbor)){
                exploreNode(neighbor, nodesVisited);
            }
        }
    }

    public Set<AbstractNode> exploreGraph(){

        Set<AbstractNode> nodesVisited = new HashSet<>();

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
                if (mark[node.getLabel()] == false) {
                    mark[node.getLabel()] = true;
                    for (AbstractNode neighbor : (List<AbstractNode>) graph.getNeighbors(node)) {
                        ((ArrayDeque<AbstractNode>) toVisit).push(neighbor);
                    }
                }
            }
            for(int j=0; j<mark.length; j++){ //we add the first unmarked node to the queue, not adding anything else
                if(!mark[j]){
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

        System.out.println("Depth explore shoudl give :" + "[node-0, node-3 , node-1, node-2, node-9, node-6, node-5, node-7, node-4, node-8]");



        /////////////////////////////////////////////////////////////DFS///////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////DFS - DIRECTED GRAPH///////////////////////////////////////////////////////////////////////


        DepthFirstSearch dfsDirectedGraph = new DepthFirstSearch(al);

        Set<AbstractNode> nodesVisited = dfsDirectedGraph.exploreGraph();

        System.out.println(nodesVisited.toString());

        nodesVisited = dfsDirectedGraph.exploreGraph_rec();

        System.out.println(nodesVisited.toString());

        /////////////////////////////////////////////////////////////DFS - UNDIRECTED GRAPH///////////////////////////////////////////////////////////////////////

        UndirectedGraph undirectedGraph = new UndirectedGraph(Matrix);
        System.out.println(undirectedGraph);

        DepthFirstSearch dfsUndirectedGraph = new DepthFirstSearch(undirectedGraph);

        nodesVisited = dfsUndirectedGraph.exploreGraph();

        System.out.println(nodesVisited.toString());

        nodesVisited = dfsUndirectedGraph.exploreGraph_rec();

        System.out.println(nodesVisited.toString());

        /////////////////////////////////////////////////////////////DFS - DIRECTED MATRIX GRAPH///////////////////////////////////////////////////////////////////////

        AdjacencyMatrixDirectedGraph directedMatrixGraph = new AdjacencyMatrixDirectedGraph(Matrix);
        System.out.println(directedMatrixGraph);

        DepthFirstSearch dfsDirectedMatrixGraph = new DepthFirstSearch(directedMatrixGraph);

        nodesVisited = dfsDirectedMatrixGraph.exploreGraph();

        System.out.println(nodesVisited.toString());

        nodesVisited = dfsDirectedMatrixGraph.exploreGraph_rec();

        System.out.println(nodesVisited.toString());

        /////////////////////////////////////////////////////////////DFS - UNDIRECTED MATRIX GRAPH///////////////////////////////////////////////////////////////////////

        AdjacencyMatrixUndirectedGraph undirectedMatrixGraph = new AdjacencyMatrixUndirectedGraph(Matrix);
        System.out.println(undirectedMatrixGraph);

        DepthFirstSearch dfsUndirectedMatrixGraph = new DepthFirstSearch(undirectedMatrixGraph);

        nodesVisited = dfsUndirectedMatrixGraph.exploreGraph();

        System.out.println(nodesVisited.toString());

        nodesVisited = dfsUndirectedMatrixGraph.exploreGraph_rec();

        System.out.println(nodesVisited.toString());

    }
}
