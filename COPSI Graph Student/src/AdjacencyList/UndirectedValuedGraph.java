package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class UndirectedValuedGraph extends UndirectedGraph<UndirectedNode> {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
    public UndirectedValuedGraph(int[][] matrixVal) {
        this.nodes = new ArrayList<>();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Getting the length of the column (which is equal to lines..)
        //Adding the nodes to the nodes list;
        for(int i=0; i<matrixVal[0].length;i++){
            nodes.add(makeNode(i));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n²) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //When he have all nodes, you can put the preds and succs of it;
        for(int i=0; i<nodes.size();i++){
            for(int j=i;j<nodes.size();j++){
                if(matrixVal[i][j] != 0){ //the ij element contains 1 if the i node contains the j nodes in his succs. this also means that the j node have the i node in his preds.
                    nodes.get(i).getNeighbors().add(nodes.get(j));
                    nodes.get(i).addCosts(matrixVal[i][j]);
                    nodes.get(j).getNeighbors().add(nodes.get(i));
                    nodes.get(j).addCosts(matrixVal[i][j]);
                    this.m ++;
                }
            }
        }

        this.order = nodes.size();

    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    private UndirectedNode getNodeFromDirectNode(UndirectedNode dNode){
        return nodes.get(dNode.getLabel());
    }

    /**
     * Removes the edge (from,to) and its cost if the edge is present in the graph
     */
    @Override
    public void removeEdge(UndirectedNode from, UndirectedNode to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n²) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromDirectNode(from).getCosts().remove(getNodeFromDirectNode(from).getNeighbors().indexOf(to));
        getNodeFromDirectNode(from).getNeighbors().remove(getNodeFromDirectNode(to));
        getNodeFromDirectNode(to).getCosts().remove(getNodeFromDirectNode(to).getNeighbors().indexOf(from));
        getNodeFromDirectNode(to).getNeighbors().remove(getNodeFromDirectNode(from));

        this.order -= 1;
    }

    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph
     */
    public void addEdge(UndirectedNode from, UndirectedNode to, int cost) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(1) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromDirectNode(from).getNeighbors().add(getNodeFromDirectNode(to));
        getNodeFromDirectNode(from).addCosts(cost);
        getNodeFromDirectNode(to).getNeighbors().add(getNodeFromDirectNode(from));
        getNodeFromDirectNode(to).addCosts(cost);
        this.order += 1;
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 15, false, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.AfficherMatrix(matrix);
        GraphTools.AfficherMatrix(matrixValued);
        UndirectedValuedGraph al = new UndirectedValuedGraph(matrixValued);
        System.out.println(al);
        System.out.println(al.getNodes().get(2).getCosts());
        al.addEdge(new UndirectedNode(2), new UndirectedNode(5), 13);
        al.addEdge(new UndirectedNode(2), new UndirectedNode(7), 4);
        System.out.println(al);
        System.out.println(al.getNodes().get(2).getCosts());
        System.out.println(al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        al.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(al.getNodes().get(2).getCosts());
        System.out.println(al);
    }
	
}
