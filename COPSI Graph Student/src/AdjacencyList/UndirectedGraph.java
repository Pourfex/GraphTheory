package AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;


public class UndirectedGraph<A extends UndirectedNode> extends AbstractListGraph<A> implements IUndirectedGraph<A> {


    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public UndirectedGraph() {
		 this.nodes = new ArrayList<>();
	}
	
	public UndirectedGraph(List<A> nodes) {
        super(nodes);
        for (UndirectedNode i : nodes) {
            this.m += i.getNbNeigh();
        }
        this.order = nodes.size();
    }

    public UndirectedGraph(int[][] matrix) {
        this.nodes = new ArrayList<A>();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Getting the length of the column (which is equal to lines..)
        //Adding the nodes to the nodes list;
        for(int i=0; i<matrix[0].length;i++){
            nodes.add(makeNode(i));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n²) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //When he have all nodes, you can put the preds and succs of it;
        for(int i=0; i<nodes.size();i++){
            List<A> tmp = new ArrayList<>();
            for(int j=i;j<nodes.size();j++){
                if(matrix[j][i] == 1){ //the ij element contains 1 if the i node contains the j nodes in his succs. this also means that the j node have the i node in his preds.
                    nodes.get(i).getNeighbors().add(nodes.get(j));
                    nodes.get(j).getNeighbors().add(nodes.get(i));
                    this.m ++;
                }
            }
        }

        this.order = nodes.size();

    }

    public UndirectedGraph(UndirectedGraph<A> g) {
        this.nodes = g.nodes;
        this.m =g.m;
        this.order = g.order;
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    @Override
    public int getNbEdges() {
        return this.m;
    }    

    @Override
    public List<A> getNeighbors(A x) {
        return this.getNodes().get(x.getLabel()).getNeighbors().stream().map(n -> (A)n).collect(Collectors.toList());
    }
    
    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------

    private A getNodeFromUndirectNode(UndirectedNode uDNode){
        return nodes.get(uDNode.getLabel());
    }

    @Override
    public boolean isEdge(A x, A y) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return getNodeFromUndirectNode(x).getNeighbors().contains(getNodeFromUndirectNode(y));
    }

    @Override
    public void removeEdge(A x, A y) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromUndirectNode(x).getNeighbors().remove(getNodeFromUndirectNode(y));
        getNodeFromUndirectNode(y).getNeighbors().remove(getNodeFromUndirectNode(x));
        this.m -= 1;
    }

    @Override
    public void addEdge(A x, A y) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(1) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromUndirectNode(x).getNeighbors().add(getNodeFromUndirectNode(y));
        getNodeFromUndirectNode(y).getNeighbors().add(getNodeFromUndirectNode(x));
        this.m += 1;
    }
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends UndirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A) new UndirectedNode(label);
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] am = new int[this.order][this.order];

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n²) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for(int i = 0; i<this.order; i++){
            for(int j=i; j<this.order; j++){
                if(nodes.get(i).getNeighbors().contains(nodes.get(j))){
                    am[i][j] = 1;
                    am[j][i] = 1;
                }else{
                    am[i][j] = 0;
                    am[j][i] = 0;
                }
            }
        }
        return am;
    }

    
    @Override
    public String toString() {
        String s = "";
        for (UndirectedNode n : nodes) {
            s += "neighbors of " + n + " : ";
            for (UndirectedNode sn : n.getNeighbors()) {
                s += sn + " ";
            }
            s += "\n";
        }
        s += "\n";
        return s;
    }


    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.AfficherMatrix(mat);
        UndirectedGraph al = new UndirectedGraph(mat);
        System.out.println(al);
        al.addEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println("IsEdge Node 2 to Node 5 should be true : " +al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        al.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println("IsEdge Node 2 to Node 5 should be false : " +al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        System.out.println(al);

        int[][] am = al.toAdjacencyMatrix();
        GraphTools.AfficherMatrix(am);
    }

}
