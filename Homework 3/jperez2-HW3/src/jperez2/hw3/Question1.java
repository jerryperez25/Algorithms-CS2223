package jperez2.hw3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * This is the template code for question 1.
 *
 * Be sure to Explain whether the empirical results support the proposition.
 *
 */
public class Question1 
{
	public static void main(String[] args) 
	{
				
				StdOut.println("N\tMaxComp\tMaxExch");
				for (int n = 16; n <= 512; n = n* 2) 
				{
					int comps = 0;
					int exch = 0;
					
					Comparable[] arr = new Comparable[n];
					
					for (int t = 0; t < 100; t++) 
					{
						for(int i=0; i<n; i++)
						{
							arr[i] = StdRandom.uniform();
						}
						Heap.constructHeap(arr);
						exch = Heap.exchanges;
						comps = Heap.comparisons;
					}
					System.out.println(n + "\t" + comps + "\t" + exch);
				}
		// for N in 16 .. 512
		
		//   for each N, do T=100 trials you want to keep track of 
		//       what you believe to be the MOST number of exch invocations
		//       and most number of less invocations
		
		//       compute a random array of N uniform doubles
		
		//   Make sure you output for each N the maximum values you saw
		//   in a table like...
		//
		//       N   MaxComp    MaxExch
		//       16  22         8
		//     .....
	}
}
