package jperez2.hw1;

import algs.hw1.arraysearch.BandedArraySearch;

/**
 * Copy this class into your package, which must have USERID has its root.
 */
public class BandedArraySolution extends BandedArraySearch {

	/** Construct problem solution for given array. Do not modify this method. */
	public BandedArraySolution(int[][] a) {
		super(a);
	}
	
	/** 
	 * For this homework assignment, you need to complete the implementation of this
	 * method.
	 */
	
	int minimum = Integer.MAX_VALUE; // stores the minimum value in the array in minimum
	int maximum = Integer.MIN_VALUE; // stores the maximum value in the array in maximum
	
	@Override
	public int[] locate(int target) 
	{
		int length = this.length();
		
		if (minimum == Integer.MAX_VALUE) // if our minimum is equal to the maximum value 
		{
			for (int row = 0; row < length; row++) // will increase the row by 1 up until just before it reaches the length of the array
			{
				for (int column = 0; column < length; column++) // we will do the same for column
				{
					int value = inspect(row,column);	// value is going to be the inspect value of that 
					
					if (value < minimum) // if our value is greater than the minimum 
					{
						minimum = value; // then we're going to set the minimum equal to the value 
					}
					if (value > maximum) // same goes for if the value is greater than the maximum
					{
						maximum = value;
					}
				}
			}
		}
		
		if (target < minimum) // if our target is less than the minimum value then null
		{ 
			return null; 
		}
		if (target > maximum) // same if its greater 
		{ 
			return null; 
		}
		
		for (int row = 0; row < length; row++) 
		{
			for (int column = 0; column < length; column++) 
			{
				if (inspect(row,column) == target) 
				{
					return new int[] { row, column };
				}
			}
		}
		
		return null;  // if its not found at all
		// couldn't get this one or the spiral to reach the goal, I ran out of time :(
	}	
	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		int[][] ar = BandedArraySearch.create(13);
		new BandedArraySolution(ar).trial();
	}
}
