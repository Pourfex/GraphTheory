package AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;


public class DirectedGraph<A extends DirectedNode> extends AbstractListGraph<A> implements IDirectedGraph<A> {

	//private static int _DEBBUG =1;
	private static int _DEBBUG =0;

		
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
	public DirectedGraph(){
		super();
		this.nodes = new ArrayList<A>();
	}

    public DirectedGraph(int[][] matrix) {
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
            for(int j=0;j<nodes.size();j++){
                if(matrix[j][i] == 1){ //the ij element contains 1 if the i node contains the j nodes in his succs. this also means that the j node have the i node in his preds.
                    nodes.get(i).getSuccs().add(nodes.get(j));
                    nodes.get(j).getPreds().add(nodes.get(i));
                }
            }
        }

        this.order = nodes.size();
        this.m = 0; // don't know what this is about
    }

    public DirectedGraph(DirectedGraph<A> g) {
    	this.nodes = g.nodes;
    	this.m =g.m;
    	this.order = g.order;
    }

    // ------------------------------------------
    // 		Accessors
    // ------------------------------------------
    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public List<A> getSuccessors(A x) {
        return this.getNodes().get(x.getLabel()).getSuccs().stream().map(n -> (A)n).collect(Collectors.toList());
    }

    @Override
    public List<A> getPredecessors(A x) {
        return this.getNodes().get(x.getLabel()).getPreds().stream().map(n -> (A)n).collect(Collectors.toList());
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    private A getNodeFromDirectNode(DirectedNode dNode){
	    return nodes.get(dNode.getLabel());
    }

    @Override
    public boolean isArc(A from, A to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	return getNodeFromDirectNode(from).getSuccs().contains(getNodeFromDirectNode(to));
    }

    @Override
    public void removeArc(A from, A to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromDirectNode(from).getSuccs().remove(getNodeFromDirectNode(to));
        getNodeFromDirectNode(to).getPreds().remove(getNodeFromDirectNode(from));
        this.order -= 1;
    }

    @Override
    public void addArc(A from, A to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(1) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromDirectNode(from).getSuccs().add(getNodeFromDirectNode(to));
        getNodeFromDirectNode(to).getPreds().add(getNodeFromDirectNode(from));
        this.order += 1;
    }
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A)new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public A getNodeOfList(A src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
    	int[][] am = new int[this.order][this.order];

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	for(int i = 0; i<this.order; i++){
    	    for(int j=0; j<this.order; j++){
    	        if(nodes.get(i).getSuccs().contains(nodes.get(j))){
    	            am[i][j] = 1;
                }else{
                    am[i][j] = 0;
                }
            }
        }

        return am;
    }

    @Override
    public IDirectedGraph computeInverse() {

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        for(A node : nodes){
            List<DirectedNode> tmp = node.getSuccs();
            node.setSuccs(node.getPreds());
            node.setPreds(tmp);
        }
        return this;
    }
    
 	
    @Override
    public String toString(){
        String s = "";
        for(DirectedNode n : nodes){
            s+="successors of "+n+" : ";
            for(DirectedNode sn : n.getSuccs()){
                s+=sn+" ";
            }
            s+="\n";
        }
        s+="\n";
        for(DirectedNode n : nodes){
            s+="preds of "+n+" : ";
            for(DirectedNode sn : n.getPreds()){
                s+=sn+" ";
            }
            s+="\n";
        }
        return s;
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.AfficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);
        DirectedGraph alInv = (DirectedGraph)al.computeInverse();
        System.out.println(alInv);

        al.addArc(new DirectedNode(2), new DirectedNode(5));
        //System.out.println(al);
        System.out.println("IsEdge Node 2 to Node 5 should be true : " +al.isArc(new DirectedNode(2), new DirectedNode(5)));

        al.removeArc(new DirectedNode(2), new DirectedNode(5));
        System.out.println("IsEdge Node 2 to Node 5 should be false : " + al.isArc(new DirectedNode(2), new DirectedNode(5)));
        //System.out.println(al);

        int[][] am = al.toAdjacencyMatrix();
        GraphTools.AfficherMatrix(Matrix);
        
    }
}
