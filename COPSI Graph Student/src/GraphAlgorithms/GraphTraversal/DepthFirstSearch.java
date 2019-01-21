package GraphAlgorithms.GraphTraversal;

import Abstraction.AbstractNode;
import Abstraction.IGraph;
import AdjacencyList.DirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

import java.util.*;

public class DepthFirstSearch {

    private IGraph graph;


    public DepthFirstSearch(IGraph graph){
        this.graph = graph;
    }

    public Set<AbstractNode> exploreGraph_rec(IGraph graph){
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

    public Set<AbstractNode> exploreGraph(IGraph graph){

        Set<AbstractNode> nodesVisited = new HashSet<>();

        boolean[] mark = new boolean[graph.getNbNodes()];
        for(boolean b : mark){
            b = false;
        }

        Queue<AbstractNode> toVisit = new ArrayDeque<>();
        toVisit.add((AbstractNode) graph.getNodes().get(0));

        while(!toVisit.isEmpty()) {
            AbstractNode node = ((ArrayDeque<AbstractNode>) toVisit).pop();
            nodesVisited.add(node);
            if(mark[node.getLabel()] == false){
                mark[node.getLabel()] = true;
                for(AbstractNode neighbor : (List<AbstractNode>) graph.getNeighbors(node)){
                    ((ArrayDeque<AbstractNode>) toVisit).push(neighbor);
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

        DepthFirstSearch dfsDirectedGraph = new DepthFirstSearch(al);
        Set<AbstractNode> nodesVisited = dfsDirectedGraph.exploreGraph(al);

        System.out.println(nodesVisited.toString());
        //TODO DON'T WORK

        nodesVisited = dfsDirectedGraph.exploreGraph_rec(al);

        System.out.println(nodesVisited.toString());


    }
}
