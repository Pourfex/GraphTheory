package GraphAlgorithms.GraphTraversal;

import Abstraction.AbstractNode;
import Abstraction.IGraph;

import java.util.*;

public class DepthFirstSearch {

    private IGraph graph;


    public DepthFirstSearch(IGraph graph){
        this.graph = graph;
    }

    public void exploreGraph_rec(IGraph graph){
        Set<AbstractNode> nodesVisited = new HashSet<>();
        for(int i=0; i<graph.getNbNodes(); i++){
            if(!nodesVisited.contains(graph.getNodes().get(i))){
                exploreNode((AbstractNode) graph.getNodes().get(i), nodesVisited);
            }
        }
    }

    private void exploreNode(AbstractNode node, Set<AbstractNode> nodesVisited) {
        nodesVisited.add(node);
        for(AbstractNode neighbor : (List<AbstractNode>) graph.getNeighbors(node)){
            if(!nodesVisited.contains(neighbor)){
                exploreNode(neighbor, nodesVisited);
            }
        }
    }

    public void exploreGraph(IGraph graph){
        boolean[] mark = new boolean[graph.getNbNodes()];
        for(boolean b : mark){
            b = false;
        }

        Queue<AbstractNode> toVisit = new ArrayDeque<>();
        toVisit.add((AbstractNode) graph.getNodes().get(0));

        while(!toVisit.isEmpty()) {
            AbstractNode node = ((ArrayDeque<AbstractNode>) toVisit).pop();
            if(mark[node.getLabel()] == false){
                mark[node.getLabel()] = true;
                for(AbstractNode neighbor : (List<AbstractNode>) graph.getNeighbors(node)){
                    ((ArrayDeque<AbstractNode>) toVisit).push(neighbor);
                }
            }
        }
    }

    


}
