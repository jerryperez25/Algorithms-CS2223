package jperez2.hw2;

import edu.princeton.cs.algs4.StdOut;

/**
 * This cousin to BinarySearch attempts to locate a value by dividing the array into "thirds"
 * and checking within each third for the target value.
 */
public class TertiarySearch 
{

/** Record the number of inspections. */
static int numInspections = 0;
static int numInspectionspb = 0;
/**
* Request tertiary array search on the given collection and return whether value was found.
* 
* After this function completes, the static value numInspections records the total number
* of array inspections needed.
* 
* Note numInspections is reset each time this function is called.
* 
* DO NOT MODIFY THIS METHOD.
* 
* @param collection
* @param target
* @return
*/
public static boolean find (int[] collection, int target) 
{
	numInspections = 0;
	return find(collection, target, 0, collection.length - 1);
}
/**
* Same as above function - but for binary search (for comparison bonus question 1.2)
*/
public static boolean binaryFinder(int[] collection, int target) 
{
	numInspectionspb = 0;
	return binarySearchForBonus(collection, target);
}
/**
* Given an array of ascending values, subdvide into "thirds" and attempt to locate
* recursively using a Tertiary Array Search.
* 
* You do not need to modify this method. You should not call this method directly.
* Rather call find(collection, target).
*  
* @param collection    ascending order collection
* @param target        target to be sought
* @param lo            low end of range within which search proceeds (inclusive)
* @param hi            high end of range within which searhc proceeds (inclusive)
*/
static boolean find (int[] collection, int target, int lo, int hi) 
{
	while (lo <= hi) 
	{
		int length = (hi-lo)/3;
		int left = lo + length;
		int RC = collection[left] - target;
		numInspections++;
		if (RC > 0) 
		{
			return find (collection, target, lo, left-1);         // lower third
		}
		else if (RC < 0) 
		{
			int right = left + length + 1;
			RC = collection[right] - target;
			numInspections++;
			if (RC > 0) 
			{
				return find(collection, target, left+1, right-1); // middle third
			} 
			else if (RC < 0) 
			{
				return find(collection, target, right+1, hi);     // upper third
			} 
			else 
			{
				return true;                                      // found at right
			}
		} 
		else
		{
			return true;                                          // found at original left
		}
	}
	return false;
}
/**
* Binary search function for comparison to TertiarySearch
* @param collection
ascending order collection
* @param target
target to be sought
*/
static boolean binarySearchForBonus (int[] collection, int target) 
{
	int low = 0;
	int high = collection.length-1;
	
	while (low <= high) 
	{
		int mid = (low+high)/2;
		int rc = collection[mid] - target;
		numInspectionspb++;
		if (rc < 0) 
		{
			low = mid+1;
		}
		else if (rc > 0) 
		{
			high = mid-1;
		} 
		else
		{
			return true;
		}
	}
	return false;
}

	public static void main(String[] args) 
	{
		StdOut.println("N\tMax\tTotal\tFull Counts");
		int total = 0;
		int maximum = -1;
		String count = "";
		for (int n = 1; n <= 20; n++) 
		{								// creates n arrays with 0 to n-1 for their data
			int [] arr1 = new int [n];
			for (int i = 0; i<n; i++) 
			{
				arr1[i] = i;
			}
			for (int j = 0; j<arr1.length; j++) 
			{
				find(arr1, j); 		// numInspections is reset at start of this find
				total += numInspections;
				
				if (maximum < numInspections) 
				{
					maximum = numInspections;
				}
				if (j == 0) 
				{
					count = count + numInspections;
				}
				else if (j > 0) 
				{
					count = count + "," + numInspections;
				}
			}
			System.out.println(n + "\t" + maximum + "\t" + total + "\t" + count);
			maximum = -1;
			total = 0;
			count = "";
		}
		// here goes nothing 
		
		StdOut.println("\nBeginning of Bonus Problem 1.2:");
		StdOut.println("N" + "\t" + "Tertiary" + "\t" + "Binary");
		
		int howlong; 
		int Taverage = 0;
		int Baverage = 0;
		int terminate = 0;
		int EndBaverage = 0;
		int [] arr = null;
		for (int k=1; k<=10; k++)
		{ 
			howlong = (int) Math.pow(3, k);
			arr = new int [howlong];
			for (int i=0; i<howlong; i++) 
			{
				arr[i] = i;
				Taverage = 0;
				Baverage = 0;
			}
			for (int j=0; j<howlong; j++) 
			{ // getting of # of inspections for each method
				find(arr, j); 
				binarySearchForBonus(arr, j);
				Baverage += numInspectionspb;
				Taverage += numInspections;
			}
			Taverage = Taverage/(howlong-1);
			Baverage = Baverage/(howlong-1);
			terminate += Taverage;
			EndBaverage += Baverage;
			StdOut.println("3^" + k + "\t" + Taverage + "\t\t" + Baverage);
		}
		StdOut.println("\nSum of average number of inspections: ");
		StdOut.println("TertiarySearch: " + terminate + "\t" + "Binary: " + EndBaverage);
	}
}
