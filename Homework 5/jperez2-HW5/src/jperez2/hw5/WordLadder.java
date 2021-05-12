package jperez2.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import algs.days.day12.SeparateChainingHashST;

// Note that the Day18 implementation of AVL removes <Key,Value> and only uses <Key>
import algs.days.day18.AVL;

/**
 * Modify this class for problem 1 on HW5.
 */
public class WordLadder {

	/**
	 * Represent the mapping of (uniqueID, 4-letter word)
	 */
	static SeparateChainingHashST<String,Integer> table = new SeparateChainingHashST<String,Integer>();
	static SeparateChainingHashST<Integer,String> reverse = new SeparateChainingHashST<Integer,String>();

	/**
	 * Determine if the two same-sized words are off by just a single character.
	 */
	public static boolean offByOne(String w1, String w2) 
	{
		int commonLetters = 0;
		for (int i=0; i<w1.length(); i++) 
		{
			// this is meant to check to see if characters are equal
			if ((w1.charAt(i)) == (w2.charAt(i))) 
			{ 
				commonLetters++;
			}
		}
		if (commonLetters == 3) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public static Iterable<Integer> shortestPathTo(int thisone,int thatone, int[]edgeTo) 
	{
        if (! hasPathTo(thisone, marked))
        {
            return null;
        }
        Stack<Integer> thispath = new Stack<Integer>();
        for (int i = thisone; i != thatone; i = edgeTo[i]) 
        {
        	thispath.push(i);
        }
        return thispath;
    }
	public static boolean hasPathTo(int thisone, boolean[] marked) 
	{
		return marked[thisone];
	}
	public static boolean[]marked;


	/**
	 * Main method to execute.
	 * 
	 * From console, enter the start and end of the word ladder.
	 */
	public static void main(String[] args) throws FileNotFoundException 
	{

		// Use this to contain all four-letter words that you find in dictionary
		AVL<String> avl = new AVL<String>();
		int j = 0;
		// create a graph where each node represents a four-letter word.
		// Also construct avl tree of all four-letter words.
		// Note: you will have to copy this file into your project to access it, unless you
		// are already writing your code within the SedgewickAlgorithms4ed project.
		Scanner sc = new Scanner(new File ("words.english.txt"));
		while (sc.hasNext()) 
		{
			String s = sc.next();
			if (s.length() == 4) 
			{
				avl.insert(s); 
				table.put(s, j);
				reverse.put(j, s);
				j++;
			}
		}
		sc.close();

		// now construct graph, where each node represents a word, and an edge exists between 
		// two nodes if their respective words are off by a single letter. Hint: use the
		// keys() method provided by the AVL tree.
		Graph theseWords = new Graph(j);
		for (int i = 0; i < j; i++) {
			String word = reverse.get(i);
			for (String str : avl.keys()) 
			{
				if (offByOne(word, str)) 
				{
					theseWords.addEdge(table.get(word), table.get(str));
				}
			}
		}

		StdOut.println("Enter word to start from (all in lower case):");
		String start = StdIn.readString().toLowerCase();
		StdOut.println("Enter word to end at (all in lower case):");
		String end = StdIn.readString().toLowerCase();

		// need to validate that these are both actual four-letter words in the dictionary
		if (!avl.contains(start)) {
			StdOut.println (start + " is not a valid word in the dictionary.");
			System.exit(-1);
		}
		if (!avl.contains(end)) {
			StdOut.println (end + " is not a valid word in the dictionary.");
			System.exit(-1);
		}

		// Once both words are known to exist in the dictionary, then create a search
		// that finds shortest distance (should it exist) between start and end.
		// be sure to output the words in the word ladder, IN ORDER, from the start to end.

		BreadthFirstPaths thisisit = new BreadthFirstPaths(theseWords, table.get(start));
		if (thisisit.hasPathTo(table.get(end))) 
		{
			for (int i : thisisit.pathTo(table.get(end))) 
			{
				StdOut.println(reverse.get(i));
			}
		}
		else 
		{
			
		}
	}
}
