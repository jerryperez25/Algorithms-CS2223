package jperez2.hw4;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * All you have to do for Question 1 is rearrange the values in line 17.
 */
public class Question1 {

	public static void main(String[] args) {

		AVL<Integer, Boolean> tree = new AVL<Integer, Boolean>();
		AVL<Integer, Boolean> newavl = new AVL<Integer, Boolean>();

		// find a different order to insert the numbers from 1 to 12 such that
		// you have no rotations. You ONLY have to modify the order in which these
		// twelve numbers appears below.
		//int[] values = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		int [] values = {8, 4, 10, 2, 6, 9, 11, 1, 3, 5, 7, 12};
		//int[] values = {12, 1, 11, 2, 10, 3, 5, 6, 4, 7, 8, 9 };
		int hMax = 0;
		int RtMax = 0;

		for (int i : values) {
			tree.put(i, true); // for this question, the value is ignored....
		}
		StdOut.println("Number of rotations:" + tree.rotations);
		StdOut.println("Height of tree:" + tree.height());
		StdRandom.shuffle(values);
		StdOut.println("N\tMaxHt.\tMaxRot.");
		int n = 1;

		// validate all values are there
		for (int i = 1; i <= 4095; n++, i = (int) Math.pow(2, n) - 1) 
		{
			int[] arr = new int[i];
			for (int j = 0; j < i; j++) 
			{
				arr[j] = j + 1;
			}
			for (int k = 0; k <= 3000; k++)
			{
				newavl = new AVL<Integer, Boolean>();
				StdRandom.shuffle(arr);
				for (int h : arr) 
				{
					newavl.put(h, true);
				}
				if (hMax < newavl.height()) 
				{
					hMax = newavl.height();
				}
				if (RtMax < newavl.rotations) 
				{
					RtMax = newavl.rotations;
				}
			}
			arr = null;
			StdOut.println(i + "\t" + hMax + "\t" + RtMax);
			hMax = 0;
			RtMax = 0;
		}
		for (int i = 1; i <= 12; i++) {
			if (!tree.contains(i)) {
				StdOut.println("Error:  doesn't contain " + i);
			}
		}
	}
}
