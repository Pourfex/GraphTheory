package GraphAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import Abstraction.AbstractNode;
import Collection.Pair;
import Collection.Triple;
import Nodes.DirectedNode;


public class BinaryHeapEdge<A extends Object> {

	private  List<Triple<A,A,Integer>> nodes;

    public BinaryHeapEdge() {
        this.nodes = new ArrayList<Triple<A,A,Integer>>();
    }

    public boolean isEmpty() {
       return nodes.isEmpty();
    }

    public void insert(A from, A to, int val) {
    	this.nodes.add(new Triple<>(from,to,val));
    	int index = this.nodes.size() -1 ;
    	int fatherIndex = Math.round((index-1)/2);
    	while(val < this.nodes.get(fatherIndex).getThird() && index != 0){
            Collections.swap(nodes, index, fatherIndex);
            index = fatherIndex;
            fatherIndex = Math.round((index-1)/2);
        }
    }

    public Triple<A,A,Integer> remove() {
        Collections.swap(nodes, 0, nodes.size() -1);

        int val = nodes.get(0).getThird();
        int index =0;
        int childrenIndex = minChildrenIndex(index);

        while(val > this.nodes.get(childrenIndex).getThird() && !isLeaf(index)){
            Collections.swap(nodes, index, childrenIndex);
            index = childrenIndex;
            if(!isLeaf(index)){
                childrenIndex = minChildrenIndex(index);
            }
        }

        return nodes.remove(nodes.size() -1);
    }

    private int minChildrenIndex(int index) {
        if(isLeaf(index)){
            throw new UnsupportedOperationException();
        }
        if(2*index+2 > nodes.size() -1){
            return 2*index+1;
        }
        return nodes.get(2*index+1).getThird() >= nodes.get(2*index+2).getThird() ? 2*index+1 : 2*index+2;
    }


    private boolean isLeaf(int src) {
    	int size = nodes.size() -1 ;
    	if(2*src+1 >= size){
    	    return true;
        }
        return false;
    }

   

    public String toString() {
        return nodes.toString();
    }

    // test recursif pour verifier que fils gauche et fils droit sont bien >= a la racine.
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
    	int lastIndex = nodes.size()-1; 
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= lastIndex) {
                return nodes.get(left).getThird() >= nodes.get(root).getThird() && testRec(left);
            } else {
                return nodes.get(left).getThird() >= nodes.get(root).getThird() && testRec(left) && 
                		nodes.get(right).getThird() >= nodes.get(root).getThird() && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeapEdge<DirectedNode> jarjarBin = new BinaryHeapEdge<DirectedNode>();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 10;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));            
            System.out.print("insert triple des noeuds "+k+" et "+(k+30)+" de poids " + rand);
            jarjarBin.insert(new DirectedNode(k), new DirectedNode(k+30), rand);
            System.out.println(" ok");
            k--;
        }
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.isEmpty());
        System.out.println(jarjarBin.test() + "\n");
        while (m > 0) {
            System.out.print("remove " + jarjarBin.remove());
            System.out.println("\n" + jarjarBin);
            System.out.println(" ok");
            m-=2;
        }
        System.out.println();
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.isEmpty());
        System.out.println(jarjarBin.test());
    }


}

