package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class DirectedValuedGraph extends DirectedGraph<DirectedNode> {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public DirectedValuedGraph(int[][] matrixVal) {
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
            for(int j=0;j<nodes.size();j++){
                if(matrixVal[i][j] != 0){ //the ij element contains 1 if the i node contains the j nodes in his succs. this also means that the j node have the i node in his preds.
                    nodes.get(i).getSuccs().add(nodes.get(j));
                    nodes.get(i).addCosts(matrixVal[i][j]);
                    nodes.get(j).getPreds().add(nodes.get(i));
                    nodes.get(j).addCostsInv(matrixVal[i][j]);
                }
            }
        }

        this.order = nodes.size();
        this.m = 0; // don't know what this is about
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    private DirectedNode getNodeFromDirectNode(DirectedNode dNode){
        return nodes.get(dNode.getLabel());
    }

	/**
     * Removes the arc (from,to) with cost if it is present in the graph
     */
    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n²) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromDirectNode(from).getCosts().remove(getNodeFromDirectNode(from).getSuccs().indexOf(to));
        getNodeFromDirectNode(from).getSuccs().remove(getNodeFromDirectNode(to));
        getNodeFromDirectNode(to).getCostsInv().remove(getNodeFromDirectNode(to).getPreds().indexOf(from));
        getNodeFromDirectNode(to).getPreds().remove(getNodeFromDirectNode(from));

        this.order -= 1;
}

    /**
     * Adds the arc (from,to) with cost  if it is not already present in the graph
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(1) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromDirectNode(from).getSuccs().add(getNodeFromDirectNode(to));
        getNodeFromDirectNode(from).getCosts().add(cost);
        getNodeFromDirectNode(to).getPreds().add(getNodeFromDirectNode(from));
        getNodeFromDirectNode(to).getCostsInv().add(cost);
        this.order += 1;
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.AfficherMatrix(matrix);
        GraphTools.AfficherMatrix(matrixValued);
        DirectedValuedGraph al = new DirectedValuedGraph(matrixValued);
        System.out.println(al);
        //DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        //System.out.println(alInv);
        System.out.println(al.getNodes().get(2).getCosts());
        al.addArc(new DirectedNode(2), new DirectedNode(5), 13);
        al.addArc(new DirectedNode(2), new DirectedNode(7), 4);
        System.out.println(al);
        System.out.println(al.getNodes().get(2).getCosts());
        System.out.println(al.isArc(new DirectedNode(2), new DirectedNode(5)));
        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println(al.getNodes().get(2).getCosts());
    }
	
}
