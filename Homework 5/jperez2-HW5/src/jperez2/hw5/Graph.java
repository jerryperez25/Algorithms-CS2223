package jperez2.hw5;

import algs.days.day18.AVL;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

/** Standard undirected Grah implementation, as starting point for Q2 and Q3 on HW5. */
public class Graph {
    
    final int V;
    int E;
    Bag<Integer>[] adj;
    
    /**
     * Initializes an empty graph with <tt>V</tt> vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if <tt>V</tt> < 0
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /** Added this method for day20 to load graph from file. */
    public Graph (In in) {
    	this (in.readInt());
    	int E = in.readInt();
    	for (int i = 0; i < E; i++) {
    		int v = in.readInt();
    		int w = in.readInt();
    		addEdge (v,w);
    	}
    }

    public int V() { return V; }
    public int E() { return E; }


    /** Adds the undirected edge v-w to this graph. */
    public void addEdge(int v, int w) {
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }


    /** Returns the vertices adjacent to vertex <tt>v</tt>. */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    /** Returns the degree of vertex <tt>v</tt>. */
    public int degree(int v) {
        return adj[v].size();
    }

    /** Fill in this method to determine if undirected graph is connected. */
    public boolean checkConnected(Graph thisgraph, int num) 
    {
		boolean [] marked = new boolean[thisgraph.V()];
		int [] edgeTo = new int[thisgraph.V()];
		int [] distTo = new int [thisgraph.V()]; 
		Queue<Integer> thisQueue = new Queue<Integer>();
		for (int i = 0; i < thisgraph.V(); i++)
		{
			distTo[i] = Integer.MAX_VALUE;
		}
		distTo[num] = 0;
		marked[num] = true;
		thisQueue.enqueue(num);
		while (!thisQueue.isEmpty()) 
		{
			int deq = thisQueue.dequeue();
			for (int o : thisgraph.adj(deq)) 
			{
				if (!marked[o]) 
				{
					edgeTo[o] = deq;
					distTo[o] = distTo[deq] + 1;
					marked[o] = true;	
					thisQueue.enqueue(o);
				}
			}
		}
		boolean doesitconnect = true;
		for (boolean bool : marked) 
		{
			if (!bool) 
			{
				doesitconnect = false;
			}
		}
		return doesitconnect;
	}
    public boolean connected() 
    {
    	return checkConnected(this, 0);
    }

    /** 
     * The diameter of graph is the maximum distance between any pair of vertices. 
     * 
     * If a graph is not connected, then Integer.MAX_VALUE must be returned.
     * @return
     */
    public int diameter() 
    {
    		int max = Integer.MAX_VALUE;// setting the value of the diameter
    		for (int i = 0; i < this.adj.length; i++) 
    		{
    			int [] dist = checkDistance(this, i);	
    			for (int checker : dist) 
    			{
    				if (checker >max || (max == Integer.MAX_VALUE && checker != 0)) 
    				{
    					max = checker;
    				}
    			}
    		}
    	return max; // return max
    }
    
    public int[] checkDistance(Graph thisgraph, int num) 
    {
		boolean [] marked = new boolean[thisgraph.V()];
		int [] edgeTo = new int[thisgraph.V()];
		int [] distTo = new int [thisgraph.V()]; 
		Queue<Integer> thisQueue = new Queue<Integer>();
		for (int i = 0; i < thisgraph.V(); i++)
		{
			distTo[i] = Integer.MAX_VALUE;
		}
		distTo[num] = 0;
		marked[num] = true;
		thisQueue.enqueue(num);
		while (!thisQueue.isEmpty()) 
		{
			int deq = thisQueue.dequeue();
			for (int w : thisgraph.adj(deq)) 
			{
				if (!marked[w]) 
				{
					edgeTo[w] = deq;
					distTo[w] = distTo[deq] + 1;
					marked[w] = true;
					thisQueue.enqueue(w);
				}
			}
		}
		return distTo;
	}
    
    /** 
     * The status of a given vertex, v.
     * 
     * If the graph is not connected, then this method is not responsible for return value.
     */
    public int status(int v) 
    {
    	return checkStatus(this,v);
    }
    
    public int checkStatus(Graph thisgraph, int num)
    {
		int [] edgeTo = new int[thisgraph.V()];
		int [] distTo = new int [thisgraph.V()];
		int track = 0;
		boolean [] marked = new boolean[thisgraph.V()];
		Queue<Integer> newQueue = new Queue<Integer>();
		
		for (int i = 0; i < thisgraph.V(); i++)
		{
			distTo[i] = Integer.MAX_VALUE;
		}
		distTo[num] = 0;
		marked[num] = true;
		newQueue.enqueue(num);
		while (!newQueue.isEmpty()) 
		{
			int deq = newQueue.dequeue();
			for (int j : thisgraph.adj(deq)) 
			{
				if (!marked[j]) 
				{	
					edgeTo[j] = deq;	
					distTo[j] = distTo[deq] + 1;
					marked[j] = true;
					newQueue.enqueue(j);
				}
			}
		}
		for (int k = 0; k < thisgraph.V(); k++) 
		{
			if (distTo[k] != Integer.MAX_VALUE) 
			{
				track = track + distTo[k];
			}
		}
		return track;
	}
    
    /**
     * Determine if all status(v) values within the graph represent different values.
     * 
     */
    public boolean statusInjective() 
    {
    	AVL<Integer> tree1 = new AVL<Integer>();
		int lengthofadj = this.adj.length;
		for (int i = 0; i < lengthofadj; i++) 
		{
			if (tree1.contains(status(i))) 
			{
				
				return false;
			}
			else 
			{
				tree1.insert(status(i));
			}
		}
		return true;
	}
    
    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

}
