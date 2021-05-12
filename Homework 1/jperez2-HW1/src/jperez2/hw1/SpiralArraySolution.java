package jperez2.hw1;

import algs.hw1.arraysearch.SpiralArraySearch;

/**
 * Copy this class into your package, which must have USERID has its root.
 */
public class SpiralArraySolution extends SpiralArraySearch {

	/** Construct problem solution for given array. Do not modify this method. */
	public SpiralArraySolution(int[][] a) {
		super(a);
	}
	
	/** 
	 * For this homework assignment, you need to complete the implementation of this
	 * method.
	 */
	int minimum = Integer.MAX_VALUE;
	
	int maximum = Integer.MIN_VALUE;
	
	@Override
	public int[] locate(int target) 
	{
		int length = this.length(); // keep track of the length of our array
		
		if (minimum == Integer.MAX_VALUE) 
		{
			for (int row = 0; row < length; row++) // increment row
			{
				for (int column = 0; column < length; column++) // increment column
				{
					int value = inspect(row,column);
					
					// standard if tests that will set adjust the values of minimum and maximum
					if (value < minimum) 
					{
						minimum = value;
					}
					if (value > maximum) 
					{
						maximum = value;
					}
				}
			}
		}
		
		if (target < minimum) // these if tests determine where the search will go next 
		{ 
			return null; 
		}
		if (target > maximum) 
		{ 
			return null; 
		}
		
		for (int row = 0; row < length; row++) 
		{
			for (int column = 0; column < length; column++) 
			{
				if (inspect(row,column) == target) 	// inspect command that we need to find the target
				{
					return new int[] { row, column };
				}
			}
		}
		
		return null; // not at all found
	}	
	
	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		int[][] ar = SpiralArraySearch.create(13);
		new SpiralArraySolution(ar).trial();
	}
}
