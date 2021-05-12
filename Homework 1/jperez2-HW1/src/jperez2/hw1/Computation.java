package jperez2.hw1;

import edu.princeton.cs.algs4.*;

/**
 * Copy this class into your USERID.hw1 package 
 */
public class Computation {

	/**
	 * Return a stack of prime factors, with larger factors towards the top of the stack,
	 * and smaller factors at the bottom.
	 * 
	 * In fact, the resulting stack will be the factors of n in reverse order.
	 * 
	 * @param n    integer to be factored
	 * @return     stack of factors, where the factors appear in reverse sorted order (largest on top).
	 */
	static Stack<Long> factorize(long n) 
	{
		long divider = 2;
		
		Stack<Long> thisone = new Stack<Long> ();
		while (divider<=n)
		{
			long division;
			division = n%divider;
			if (division == 0)
			{
				thisone.push(divider);
				n = n/divider;
			}
			else
				divider = divider+1;
		}
		System.out.println(thisone); 
		return thisone;
	}
	
	/**
	 * Given a stack of numbers, representing the prime factors of a number n, return
	 * true if the number n is a perfect square.
	 * 
	 * This method may change the contents of the stack
	 * 
	 * @param factors   a Stack of factors (in reverse order) as produced by factorize.
	 * @return          True if the factors represents a number that is a square; false otherwise.
	 */
	static boolean isSquare(Stack<Long> factors) 
	{
		Long thisisit = factors.pop();
		
		for(Long val : factors) 
		{
			thisisit *= val;
		}
		double val = Math.sqrt(thisisit);
		
		if(Math.floor(val)== val) 
		{
			return true;
		}
		else 
			return false;
	}
	
	public static void main(String[] args) {
		// Read token. push if operator.
		StdOut.println("Enter a positive integer:");
		String s = StdIn.readString();

		try {
			long val = Long.valueOf(s);
			
			Stack<Long> factors = factorize(val);
			if (isSquare(factors)) {
				System.out.println(val + " is a perfect square.");
			} else {
				System.out.println(val + " is NOT a perfect square.");
			}
			
		} catch (Exception e) {
			System.out.println(s + " is not an integer.");
		}
	}
}
