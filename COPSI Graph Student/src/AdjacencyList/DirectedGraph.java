package AdjacencyList;

import java.util.*;
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
            for(int j=0;j<nodes.size();j++){
                if(matrix[i][j] == 1){ //the ij element contains 1 if the i node contains the j nodes in his succs. this also means that the j node have the i node in his preds.
                    nodes.get(i).getSuccs().add(nodes.get(j));
                    nodes.get(j).getPreds().add(nodes.get(i));
                    this.m ++;
                }
            }
        }

        this.order = nodes.size();
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
        //return this.getNodes().get(x.getLabel()).getSuccs().stream().map(n -> (A)n).collect(Collectors.toList());
        return (List<A>) x.getSuccs();
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
        this.m -= 1;
    }

    @Override
    public void addArc(A from, A to) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(1) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        getNodeFromDirectNode(from).getSuccs().add(getNodeFromDirectNode(to));
        getNodeFromDirectNode(to).getPreds().add(getNodeFromDirectNode(from));
        this.m += 1;
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
        // O(n²) algorithm
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

    public List<A> depthFirstSearch(){
        List<A> nodesVisited = new ArrayList<>();

        boolean[] mark = new boolean[this.order];
        Arrays.fill(mark, false);

        Stack<A> toVisit = new Stack<>();
        toVisit.add(getNodes().get(0));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n+m) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        while(nodesVisited.size() != this.order) { //To get a covering forest
            while (!toVisit.isEmpty()) {
                A node =  toVisit.pop();
                nodesVisited.add(node);
                mark[node.getLabel()] = true;
                for (A neighbor : getSuccessors(node)) {
                    if(!mark[neighbor.getLabel()] && !toVisit.contains(neighbor)){
                        toVisit.push(neighbor);
                    }
                }
            }

            for(int j=0; j<mark.length; j++){ //we add the first unmarked node to the queue, not adding anything else
                if(!mark[j]){
                    toVisit.push(getNodes().get(j));
                    break;
                }
            }
        }
        return nodesVisited;
    }

    public List<A> breathFirstSearch(){
        List<A> nodesVisited = new ArrayList<>();

        boolean[] mark = new boolean[this.order];
        Arrays.fill(mark, false);

        Queue<A> toVisit = new LinkedList<>();
        toVisit.add(getNodes().get(0));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n+m) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        while(nodesVisited.size() != this.order) { //To get a covering forest
            while (!toVisit.isEmpty()) {
                A node =  ((LinkedList<A>) toVisit).pop();
                nodesVisited.add(node);
                mark[node.getLabel()] = true;
                for (A neighbor : getSuccessors(node)) {
                    if(!mark[neighbor.getLabel()] && !toVisit.contains(neighbor)){
                        toVisit.add(neighbor);
                    }
                }
            }
            for(int j=0; j<mark.length; j++){ //we add the first unmarked node to the queue, not adding anything else
                if(!mark[j]){
                    toVisit.add(getNodes().get(j));
                    break;
                }
            }
        }
        return nodesVisited;
    }

    public List<List<A>> strongConnexity(){
        List<A> startingConnexityNodes = new ArrayList<>();

        int[] startExplore = new int[this.order];
        int[] endExplore = new int[this.order];

        Arrays.fill(startExplore, -1);
        Arrays.fill(endExplore, -1);

        int[] time = new int[1];
        time[0] = 0;

        Deque<DirectedNode> result = new ArrayDeque<>();
        List<DirectedNode> visited = new ArrayList<>();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        while(!allExplore(endExplore)){
            A node = this.getNodes().get(getFirstNonExplored(endExplore));
            startingConnexityNodes.add(node);
            depthSearch(node,startExplore,endExplore, time, result, visited);
        }

        List<List<A>> connexElements = getConnexElements(startingConnexityNodes, visited);

        List<DirectedNode> elementsSortedByLastVisited = new ArrayList<>();
        getElementsByLastVisited(endExplore, elementsSortedByLastVisited);

        this.computeInverse();

        startingConnexityNodes = new ArrayList<>();

        Arrays.fill(startExplore, -1);
        Arrays.fill(endExplore, -1);

        time[0] = 0;
        result = new ArrayDeque<>();
        visited = new ArrayList<>();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        while(! allExplore(endExplore)){
            A node = this.getNodes().get(getLastVisitedNonExplored(endExplore,elementsSortedByLastVisited));
            startingConnexityNodes.add(node);
            depthSearch(node,startExplore,endExplore, time, result, visited);
        }

        List<List<A>> connexElementsInverse = getConnexElements(startingConnexityNodes, visited);

        this.computeInverse();
        return getStrongConnexityElements(connexElements, connexElementsInverse, visited.size());

    }

    private void getElementsByLastVisited(int[] endExplore, List<DirectedNode> inverseVisited) {
        SortedMap<Integer, Integer> sortedMap = new TreeMap<>();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for(int i =0; i< this.order; i++){
            sortedMap.put(endExplore[i], getNodes().get(i).getLabel());
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for(int i =0; i< this.order; i++){
            inverseVisited.add(getNodes().get(((TreeMap<Integer, Integer>) sortedMap).pollLastEntry().getValue()));
        }
    }

    private List<List<A>> getStrongConnexityElements(List<List<A>> connexElements, List<List<A>> connexElementsInverse, int numberOfNodes) {
        List<List<A>> result = new ArrayList<>();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n²) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        while(!resultIsFull(result, numberOfNodes)){
            for(List<A> nodesList : connexElements){
                for(List<A> nodesListInverse : connexElementsInverse){
                    List<A> tmp = new ArrayList<>();
                    for(A node : nodesList){
                        if(nodesListInverse.contains(node)){
                            tmp.add(node);
                        }
                    }
                    if(!tmp.isEmpty()){
                        result.add(tmp);
                    }
                }
            }

            for(List<A> nodesList : connexElements){
                if(nodesList.isEmpty()){
                    connexElements.remove(nodesList);
                }
                 }

            for(List<A> nodesList : connexElementsInverse){
                    if(nodesList.isEmpty()){
                        connexElementsInverse.remove(nodesList);
                    }
            }
        }
        return result;
    }

    private boolean resultIsFull(List<List<A>> result, int numberOfNodes) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int count = 0;
        for(List<A> tmp : result){
            count += tmp.size();
        }
        return numberOfNodes==count;
    }

    private int getLastVisitedNonExplored(int[] endExplore, List<DirectedNode> inverseVisited) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for(DirectedNode node : inverseVisited){
            if(endExplore[node.getLabel()] == -1){
                return node.getLabel();
            }
        }
        return 0;
    }

    private List<List<A>> getConnexElements(List<A> startingConnexityNodes, List<DirectedNode> visited) {
        List<List<A>> result = new ArrayList<>();
        List<A> tmp = new ArrayList<>();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for(DirectedNode node : visited){
            if(startingConnexityNodes.contains(node)){
                if(tmp.size() != 0){
                    result.add(tmp);
                }
                tmp = new ArrayList<>();
            }
            tmp.add((A)node);
        }
        result.add(tmp);
        return result;
    }

    private boolean allExplore(int[] endExplore) {
        try{
            getFirstNonExplored(endExplore);
            return false;
        }catch(Exception e){
            return true;
        }
    }

    private int getFirstNonExplored(int[] endExplore) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for(int i=0; i<endExplore.length; i++){
            if(endExplore[i] == -1){
                return i;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private Deque<DirectedNode> depthSearch(A start, int[] startExplore, int[] endExplore, int[] time, Deque<DirectedNode> result, List<DirectedNode> visited) {
        visited.add(start);
        startExplore[start.getLabel()] = time[0];
        time[0] ++;
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // O(n+m) algorithm
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        start.getSuccs().forEach(directedNode -> {
            if(!visited.contains(directedNode)){
                depthSearch((A) directedNode, startExplore, endExplore, time,  result, visited);
            }
        });
        endExplore[start.getLabel()] = time[0] ;
        time[0] ++;
        result.add(start);
        return result;
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


        //Profondeur à droite et largeur a gauche
        //GRAPH HAVE BEEN INVERSED
        int[][] am = al.toAdjacencyMatrix();
        GraphTools.AfficherMatrix(am);

        System.out.println(al);
        System.out.println("Depth explore should give : \n" + "[node-0, node-4 , node-1, node-8, node-3, node-7, node-2, node-9, node-5, node-6]");
        System.out.println("Depth explore gave : \n" + al.depthFirstSearch());
        System.out.println("Breath explore should give : \n" + "[node-0, node-4 , node-1, node-3, node-8, node-2, node-7, node-9, node-5, node-6]");
        System.out.println("Breath explore gave : \n" + al.breathFirstSearch());

        al.computeInverse();
        System.out.println(al);
        System.out.println("Depth explore should give : \n" + "[node-0, node-7 , node-5, node-6, node-3, node-9, node-2, node-1, node-4, node-8]");
        System.out.println("Depth explore gave : \n" + al.depthFirstSearch());
        System.out.println("Breath explore should give : \n" + "[node-0, node-3 , node-7, node-1, node-2, node-9, node-5, node-6, node-4, node-8]");
        System.out.println("Breath explore gave : \n" + al.breathFirstSearch());

        System.out.println("\n Strong connexity should give : \n" + "[[node 8] , [node-4], (node-0], [node-5 , node-6] , [node-3, node-1, node-2, node-7, node-9]");
        System.out.println("Strong connexity gave : \n" + al.strongConnexity());

        //Exemple du cours

        int[][] matrix = new int[][] {
                {0,0,0,0,0,1,0,0},
                {0,0,1,0,1,0,0,0},
                {0,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,1,0},
                {1,0,1,1,0,0,0,0},
                {1,1,0,1,0,0,0,0},
        };

        GraphTools.AfficherMatrix(matrix);
        DirectedGraph dgEx = new DirectedGraph(matrix);
        System.out.println(dgEx);

        System.out.println("Strong connexity should give : \n" + "[[node-1, node-7, node-4], [node-0, node-6, node-5], [node-2], [node-3]]");
        System.out.println("Strong connexity gave : \n" + dgEx.strongConnexity());

        int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 197385);
        GraphTools.AfficherMatrix(matrix2);
        DirectedGraph otherEx = new DirectedGraph(matrix2);
        System.out.println(otherEx);

        System.out.println("\n Strong connexity should give : \n" + "[[node 9] , [node-8], (node-0], [node-3] , [node-5] , [node-7, node-4, node-1, node-2, node-6]");
        System.out.println("Strong connexity gave : \n" + otherEx.strongConnexity());

    }
}
