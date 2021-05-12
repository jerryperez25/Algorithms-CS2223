package jperez2.hw5;

import algs.days.day19.Graph;
import edu.princeton.cs.algs4.*;

/**
 * Compute the BreadthFirst distance and DepthFirst distance for each vertex from the initial vertex 0.
 * Call these bfsDistTo[v] and dsfDistTo[v]. 
 * 
 * Observe that bfsDistTo[v] is always smaller than or equal to dfsDistTo[v].
 * Now define excess[v] = dfsDistTo[v] - bfsDistTo[v]. This assignment asks you to compute the
 * sum total of excess[v] for all vertices in the graph G.
 * 
 * Note that it is possible that some vertices are unreachable from s, and thus the dfsDistTo[v] and
 * bfsDistTo[v] would both be INFINITY. 
 */
public class SearchCompare 
{
	
	public static int excess(Graph G, int s) 
	{
		boolean [] breadthfirstMarked = new boolean [G.V()];
		boolean [] depthfirstMarked = new boolean [G.V()];
		int [] breadthfirstDistTo = new int [G.V()];
		int [] breadthfirstEdgeTo = new int [G.V()];
		int [] depthfirstDistTo = new int [G.V()];
		int [] depthfirstEdgeTo = new int [G.V()];
		int excess = 0;
		breadthFirst(G, s, breadthfirstMarked, breadthfirstDistTo, breadthfirstEdgeTo);
		depthFirst(G, s, depthfirstMarked, depthfirstEdgeTo);
		for (int i = 0; i<G.V(); i++) 
		{
			depthfirstDistTo[i] = depthFirstDistTo(G, s, i, depthfirstMarked, depthfirstEdgeTo);
		}
	
	for (int j = 0; j < G.V(); j++) 
	{		
		excess = excess + (depthfirstDistTo[j] - breadthfirstDistTo[j]);
	}

	return excess;
}
	
	public static void depthFirst(Graph G, int s, boolean [] depthfirstMarked, int [] depthfirstEdgeTo) 
	{
		depthfirstMarked[s] = true;
		for (int inties : G.adj(s))
			if (!depthfirstMarked[inties])
			{
				depthfirstEdgeTo[inties] = s;
				depthFirst(G, inties, depthfirstMarked, depthfirstEdgeTo);
			}
	}
	
	public static int depthFirstDistTo(Graph G, int s, int thisone, boolean[] depthfirstMarked, int[] depthfirstEdgeTo) 
	{
		int dis = -1;
		if (!depthfirstMarked[thisone]) 
		{ 
			return Integer.MAX_VALUE; 
		}
		Stack<Integer> pathStack = new Stack<Integer>();
	    for (int i = thisone; i != s; i = depthfirstEdgeTo[i])
	    {
	    	pathStack.push(i);
	    }
	    pathStack.push(s);
	    while (!pathStack.isEmpty()) 
	    {
	    	pathStack.pop();
	    	dis++;
	    }
	    
	    return dis;
	}

	
	public static void breadthFirst(Graph G, int s, boolean [] breadthfirstMarked, int [] breadthfirstDistTo, int [] breadthfirstEdgeTo)
	{
		Queue<Integer> thisqueue = new Queue<Integer>();
		for (int i = 0; i < G.V(); i++) 
		{
			breadthfirstDistTo[i] = Integer.MAX_VALUE;
		}
		breadthfirstMarked[s] = true;
		breadthfirstDistTo[s] = 0;
		thisqueue.enqueue(s);
		while (!thisqueue.isEmpty()) 
		{
			int deq = thisqueue.dequeue();
			for (int inties : G.adj(deq)) 
			{	
				if (!breadthfirstMarked[inties]) 
				{	
					breadthfirstEdgeTo[inties] = deq;
					breadthfirstDistTo[inties] = breadthfirstDistTo[deq] + 1;
					breadthfirstMarked[inties] = true;
					thisqueue.enqueue(inties);
				}
			}
		}
	}

	public static void main(String[] args) {
		String input;
		if (args.length != 0) {
			input = args[0];
		} else {
			input = "tinyG.txt";
		}
		In in = new In(input);
		Graph g = new Graph(in);

		// Compute and report Excess on tinyG.txt by default
		System.out.println("Excess:" + SearchCompare.excess(g, 0));
		
		for (int N = 4; N <= 1024; N *= 2) {
			System.out.print(N + "\t" );
			Graph gr = new Graph(N);
				
			// every possible edge exists with probability p
			for (int i = 0; i < N-1; i++) {
				for (int j = i+1; j < N; j++) {
					gr.addEdge(i, j);
				}
			}
			
			System.out.println(SearchCompare.excess(gr, 0) + "\t");
			
		}
	}

}