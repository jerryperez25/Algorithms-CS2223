package jperez2.hw4;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Homework 4: Data Type Exercise
 * 
 * Copy this class into your USERID.hw4 package and complete.
 * 
 * Each composite number is to be represented as a binary tree of prime factors (with value as power).
 * 
 * Yes, the Composite Problem is Back!
 */
public class Composite 
{
 
	/**
	 * Keep track of the AVL tree of factor/exponents based at this root.
	 * 
	 * Each key is a BigInteger factor; each value is a power of that factor
	 */
	AVL<BigInteger, Integer> tree = new AVL<BigInteger, Integer>();    
	
	/**
	 * Constructs a Composite with the specified value, which may not be one, zero or negative.
	 */
	public Composite(long val) 
	{
		this (BigInteger.valueOf(val));
	} 
	
	/**
	 * Constructs a Composite from the given Pair<BigInteger,Integer> powers.
	 * 
	 * Useful to be able to create a Composite object from pre-existing factors and exponents 
	 */
	public Composite(Iterable<Pair<BigInteger,Integer>> factors) 
	{
		for (Pair<BigInteger,Integer> pair : factors) 
		{
			tree.put(pair.key, pair.value);
		}
	}

	public Composite ()
	{
		
	}
	/** Helper value for factorize. */
	public final static BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);

	/**
	 * Constructs a Composite with the specified value, which may not be zero, one or negative.
	 * 
	 */
	public Composite(BigInteger val) 
	{
		if (val.compareTo(BigInteger.ZERO) <= 0) 
		{
			throw new IllegalArgumentException ("Composite must be a non-negative value.");
		}
		if (val.compareTo(BigInteger.ONE) == 0) 
		{
			throw new IllegalArgumentException ("Composite cannot be one.");
		}
		BigInteger temporaryVal = val;
        BigInteger dividisible = TWO;
        boolean isitDivisible = false;
        while (dividisible.multiply(dividisible).compareTo(val) <= 0) 
        {
            if (temporaryVal.mod(dividisible).compareTo(BigInteger.ZERO) == 0) 
            {
                int PoW = 0;
                while (temporaryVal.mod(dividisible).compareTo(BigInteger.ZERO) == 0) 
                {
                    PoW++;
                    temporaryVal = temporaryVal.divide(dividisible);
                    isitDivisible = true;
                }
                tree.put(dividisible, PoW);
            }
            dividisible = dividisible.add(BigInteger.ONE);
        }
        if (!isitDivisible) 
        {
            tree.put(val, 1);
        }
        tree.put(temporaryVal, 1);
	}


	/**
	 * Returns string representation as a sequence of factors with primes
	 * 
	 * 125 returns "5^3"
	 * 36 returns "2^2 * 3^2"
	 * 
	 * Note that spaces appear between the * and the other factors
	 */
	public String toString() 
	{
		String thisStr = "";
        boolean isitAfter = false;

        for (Pair<BigInteger, Integer> thisPair : tree.pairs()) 
        {
            if(thisPair.key.compareTo(BigInteger.ONE) > 0) 
            {
            	if (thisPair.key.compareTo(BigInteger.ZERO) < 0) 
            	{
            		
            	} 
            	else if (thisPair.value == 0) 
            	{

            	} 
            	else if (thisPair.value == 1) 
            	{
            		if (!isitAfter)
            		{
            			isitAfter = true;
            		}
                else
                {
                   thisStr += " * ";
                }
               thisStr += thisPair.key.toString();
            } 
            else 
            {
                if (!isitAfter)
                {
                    isitAfter = true;
                }
                else
                {
                    thisStr += " * ";
                }
                thisStr += thisPair.key.toString() + "^" + thisPair.value;
            }
        }
        
    }
       return thisStr;
}

	/**
	 * Determine if two composite values are equal to each other.
	 * 
	 * Hint: Since you can't know the structure of the respective AVL trees, you should
	 * get their respective pairs as an iterator
	 */
	public boolean equals (Object o) 
	{
		if (o == null) 
		{
			return false; 
		}
		
		if (o instanceof Composite) 
		{
			Composite thisone = (Composite) o;
			
			if(this.value().equals(thisone.value())) 
			 {
				 return true;
	         }
			 else
			 {
				 return false;
			 }
		}
		return false;
	}
	
	/** 
	 * Return value of Composite as a single BigInteger.
	 * 
	 * Useful for testing.
	 * 
	 * @return  a single BigInteger value representing actual value of Composite.
	 */
	public BigInteger value() 
	{
	        BigInteger thiskey = BigInteger.ONE;
	        BigInteger power = BigInteger.valueOf(1);
	        BigInteger total = BigInteger.valueOf(1);
	        int value = 0;
	        
	        for(Pair<BigInteger, Integer> a : tree.pairs()) 
	        {
	            if(a == null) 
	            {
	                return total;
	            }
	            value = a.value;
	            thiskey = a.key;
	            power = thiskey.pow(value);
	            total = total.multiply(power);
	        }
	        return total;
	}
	
	/** 
	 * Determine if Composite represents a prime number.
	 * 
	 * See https://en.wikipedia.org/wiki/Prime_number#Primality_of_one
	 */
	public boolean isPrime() 
	{
		BigInteger two = new BigInteger("2");
        
        if(!this.value().isProbablePrime(1))
        {
            return false;
        }  
        if(!two.equals(this.value()) && BigInteger.ZERO.equals(this.value().remainder(two)))
        {
                   return false;
        }
            return true; 
	}

	/** 
	 * Determine whether composite is simply divisible by prime number factor.
	 * 
	 * You can assume factor is a prime number. If it is not, then this method is 
	 * not responsible for the result. 
	 */
	public boolean divisibleBy(long factor)
	{
		return divisibleBy(BigInteger.valueOf(factor));
	}
	
	/** 
	 * Determine whether composite is simply divisible by prime number factor.
	 * 
	 * You can assume factor is a prime number. If it is not, then this method is 
	 * not responsible for the result. 
	 */
	public boolean divisibleBy(BigInteger factor) 
	{
		if (!factor.isProbablePrime(25)) 
		{
			throw new IllegalArgumentException ("Factor is not prime:" + factor);
		}
		if(this.value().remainder(factor).equals(BigInteger.ZERO)) 
		{
            return true;
        }
      return false;
	}
	
	/**
	 * Computes product of two composite numbers.
	 * 
	 * @param comp
	 * @return
	 */
	public Composite multiply(Composite comp) 
	{
		AVL<BigInteger, Integer> storeproduct= new AVL<BigInteger, Integer>(); 
		for (Pair<BigInteger, Integer> thisone : tree.pairs()) 
		{
			storeproduct.put(thisone.key, thisone.value);
		}
		
		for (Pair<BigInteger, Integer> thisone2 : comp.tree.pairs()) 
		{
			if (storeproduct.get(thisone2.key) != null) 
			{
				storeproduct.put(thisone2.key, storeproduct.get(thisone2.key) + thisone2.value);
			}
			else 
			{
				storeproduct.put(thisone2.key, thisone2.value);
			}
		}	
	Composite total = new Composite(2); // making new composite and swapping trees
	total.tree = storeproduct;
	return total;
}

	/**
	 * Computes greatest common divisor with given composite number.
	 * 
	 * https://en.wikipedia.org/wiki/Greatest_common_divisor
	 * 
	 * When there is no common divisor (other than the value 1) this method returns null
	 * 
	 * @param comp    other number to compute gcd
	 * @return the greatest common divisor between this and comp.
	 */
	public Composite gcd(Composite comp) 
	{
		
		AVL<BigInteger, Integer> TreeofGCD = new AVL<BigInteger, Integer>(); 
		HashMap<BigInteger, Integer> hasher = new HashMap<>();
		for (Pair<BigInteger, Integer> onepair : tree.pairs()) 
		{
			hasher.put(onepair.key, onepair.value);
		}
		for (Pair<BigInteger, Integer> anotherpair : comp.tree.pairs()) 
		{
			Integer thisint = hasher.get(anotherpair.key);
			if (thisint != null ) 
			{
				int power = thisint;
				if (anotherpair.value < thisint) 
				{
					power = anotherpair.value;
				}
				TreeofGCD.put(anotherpair.key, power);
			}
		}
		if (TreeofGCD.isEmpty()) 
		{ 
			return null;
		}
		Composite gcd = new Composite(2);  
		gcd.tree = TreeofGCD;
		return gcd;
	}
	
	/**
	 * Computes least common multiple with given composite number.
	 * 
	 * https://en.wikipedia.org/wiki/Least_common_multiple
	 * 
	 * @param comp    other number to compute gcd
	 * @return the greatest common divisor between this and comp.
	 */
	public Composite lcm(Composite comp) 
	{
	        if( tree.isEmpty() ||comp.tree.isEmpty()) 
	        {
	            return null;
	        }
	        
	        Composite lcm = new Composite();
	        
	        for (Pair<BigInteger, Integer> thisone : tree.pairs())
	        {
	            for(Pair<BigInteger, Integer> comparable : comp.tree.pairs()) 
	            {
	                if(comparable.key.equals(thisone.key)) 
	                {
	                    lcm.tree.put(comparable.key, Math.max(comparable.value, thisone.value));
	                }
	                else if(comparable.key.compareTo(thisone.key) > 0) 
	                {
	                    lcm.tree.put(comparable.key, comparable.value);
	                }
	                else 
	                {
	                    lcm.tree.put(thisone.key, thisone.value);
	                }
	            }
	        }
	        return lcm;
		}
	
	/**
	 * Return Composite number with linked list of factors in ascending order. 
	 *  
	 * @param num
	 */
	public static Composite factorize(BigInteger num) 
	{
		return new Composite(num);
	}
}
