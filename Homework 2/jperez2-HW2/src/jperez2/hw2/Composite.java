package jperez2.hw2;

import java.math.BigInteger;

/**
 * Homework 2, Question 3: Data Type Exercise
 * 
 * Copy this class into your USERID.hw2 package and complete.
 * 
 * Note: You should not attempt to store as a single BigInteger the value of the
 * composite number. That is, only keep track of the factor/exponents linked
 * list only.
 */
public class Composite 
{

	/**
	 * Keep track of the linked list of factor/exponents starting with this head.
	 */
	Node head;

	/**
	 * Constructs a Composite with the specified value, which may not be one, zero
	 * or negative.
	 */
	public Composite(long val) 
	{
		this(BigInteger.valueOf(val));
	}

	/**
	 * Constructs a Composite with the specified value, which may not be zero or
	 * negative.
	 * 
	 * Must handle unit case properly.
	 */
	public Composite(BigInteger val) 
	{

		if (val.compareTo(BigInteger.ZERO) <= 0) {
			throw new IllegalArgumentException("Composite must be a non-negative value.");
		}
		if (val.compareTo(BigInteger.ONE) == 0) {
			head = null;
		} else {
			Composite compo = factorize(val); // factorize value and store in new composite 
			head = compo.head; 
		}
	}

	/**
	 * Helper constructor only by use within this class, for creating a new (empty)
	 * composite object. You will find this useful in multiply.
	 * 
	 * Must be private to ensure no one outside this class calls it directly. Note
	 * that the Composite object returned is treated like the value 1.
	 */
	Composite() 
	{

	}

	/**
	 * Returns string representation as a sequence of factors with primes
	 * 
	 * 125 returns "5^3" 36 returns "2^2 * 3^2"
	 * 
	 * 1 returns "1" (special case)
	 */
	public String toString() 
	{
		
		Node headcopy = head;
		String thisstring = "";
		if (head != null) 
		{
			thisstring = thisstring + (head.factor);
			if (head.power != 1) 
			{
				thisstring = thisstring + "^" + (head.power);
			}
		}
		else if (head == null) 
		{
			thisstring = thisstring + "1";
			return thisstring;
		}
		while (headcopy.next != null) 
		{
			headcopy = headcopy.next;
			thisstring = thisstring + " * " + (headcopy.factor);
			if (headcopy.power != 1) 
			{
				thisstring = thisstring + "^" + (headcopy.power);
			}
		}
		return thisstring;
	}

/**
 * 
 * @param a
 * @param b
 * @return boolean
 * this function is meant to check to see if nodes are equal to each other
 */
	public boolean nodeEquals(Node a, Node b) 
	{

		if (a.factor == b.factor && a.power == b.power) 
		{
			return true;
		}
		if (a == null || b == null)  // checks for inputs that are null
		{ 
			
			return false;
		}

		else 
		{
			return false;
		}
	}

	/**
	 * Determine if two composite values are equal to each other.
	 */
	public boolean equals(Object o) 
	{
		boolean bool = true;
		
		if (o == null) 
		{
			return false;
		}
		if (o instanceof Composite) 
		{
			Node HeadA = ((Composite) o).head;
			Node HeadB = this.head;
			if (HeadA == null && HeadB == null) // both are composites
			{ 
				return true;
			}
			if (HeadA == null ^ HeadB == null) // one is null but not both are null
			{ 
				return false;
			}

			if (HeadA.next == null && HeadB.next == null) // only one node exists 
			{ 
				bool = nodeEquals(HeadA, HeadB);
			}

			while (HeadA.next != null && HeadB.next != null) // this goes through each node
			{ 
				if (!nodeEquals(HeadA, HeadB)) 
				{
					return false;
				}
				HeadA = HeadA.next;
				HeadB = HeadB.next;
			}

			if (HeadA.next == null ^ HeadB.next == null) // the case where one is out before the other
			{ 
				return false;
			}
		} 
		else // if it is not a composite
		{ 
			bool = false;
		}
		return bool;
	}

	/**
	 * Return value of Composite as a single BigInteger.
	 * 
	 * Necessary when adding two composite numbers a+b when gcd(a,b) != a and
	 * gcd(a,b) != b. Note this is typically the case with two random numbers.
	 * 
	 * Also useful for testing.
	 * 
	 * @return a single BigInteger value representing actual value of Composite
	 *         number.
	 */
	public BigInteger value() 
	{
		if (head == null) 
		{
			return BigInteger.ONE;
		}
		long valH = (long) Math.pow(head.factor, head.power);
		Node nextone = head;
		BigInteger thisVal = BigInteger.valueOf(valH);
		while (nextone.next != null) 
		{
			nextone = nextone.next;
			long nextVal = (long) Math.pow(nextone.factor, nextone.power);
			thisVal = thisVal.multiply(BigInteger.valueOf(nextVal));
		}
		return thisVal;
	}

	/**
	 * Determine if Composite represents a prime number.
	 * 
	 * Note: the unit composite number (i.e., 1) is not a prime number.
	 * 
	 * See https://en.wikipedia.org/wiki/Prime_number#Primality_of_one
	 */
	public boolean isPrime() 
	{
		boolean isitPrime = false;
		int Factors = 0;
		if (head == null) // checks that the head is null, if it is then it has to be false
		{
			return false;
		}
		if (Factors < 2) // checks if the factor is less than 2 then it must be true 
		{
			isitPrime = true;
		}
		if (head.next == null)	// checks if the next head is null
		{
			if (head.power > 1) // if the power of that next head is greater than one then we are returning false
			{
				return false;
			}
		}
		while (head.next != null) 	// while the next head is not null then we must set the first head to the next head and increase factors for however long the while loop runs
		{
			head = head.next;
			Factors++;
			if (Factors == 2) // once factors hits 2 then we will return false
			{
				return false;
			}
		}
		return isitPrime;
	}

	/**
	 * Determine if Composite represents the unit number 1.
	 */
	public boolean isUnit() 
	{
		if (head == null) 
		{
			return true;
		} else 
		{
			return (value() == BigInteger.valueOf(1));
		}
	}

	/**
	 * Computes sum of two composite numbers.
	 * 
	 * For full credit, performance of code must be directly proportional to N and M
	 * (where N is number of factors in 'this' and M is number of factors in comp)
	 * PLUS the extra cost of factoring the BigInteger (this + comp)/gcd(this,comp).
	 * 
	 * In other words, for full credit, your code must attempt to do the following:
	 * a) Find a common factor to both and then add together the remaining terms.
	 * 
	 * (2^3 * 3^2 * 5 * 7) + (2^2 * 5^2 * 11) = 2520 + 1100
	 * 
	 * In the above, the common factor is (2^2 * 5) which is extracted, leaving
	 * behind the remainder of this (2 * 3^2 * 7) and the remainder of comp (5 *
	 * 11).
	 * 
	 * (2^2 * 5) * ( 2 * 3^2 * 7 + 5*11) = 20 * ( 126 + 55 )
	 * 
	 * The following logic can be used to reflect the result above. That is, once
	 * you are able to compute the 'common' Composite number, you multiply it by the
	 * Composite value which is the result of adding the two remainders together,
	 * each converted first to a BigInteger.
	 * 
	 * common.multiply(factorize(remainderComp.value().add(remainder.value())));
	 * 
	 * @param comp
	 * @return
	 */
	public Composite add(Composite comp) 
	{
		BigInteger computeval = comp.value();
		BigInteger thisval = this.value();
		Composite gcd = gcd(comp);
		BigInteger remainderC = computeval.divide(gcd.value());
		BigInteger remainder = thisval.divide(gcd.value());
		Composite sum = new Composite();
		sum = gcd.multiply(factorize(remainderC.add(remainder)));
		return sum;
	}

	/**
	 * Computes product of two composite numbers.
	 * 
	 * Resulting code must be O(N + M) where N is the number of factors in self and
	 * M is the number of factors in comp.
	 * 
	 * Simply returns 'this' when asked to multiply by 1 (the unit Composite
	 * number). Alternatively, if the unit composite number is asked to be
	 * multiplied by another composite number, then that number is simply returned.
	 * 
	 * @param comp
	 * @return
	 */
	public Composite multiply(Composite comp) 
	{

		if (this.equals(comp)) 
		{ 
			Composite thisresult = new Composite();
			thisresult.head = new Node(head.factor, head.power * 2);
			Node NextNode = head.next;
			Node currrent = thisresult.head;
			while (NextNode != null) // we are only doing this for as long as the next node is not null
			{
				currrent.next = new Node(0, 0);
				currrent = currrent.next;
				currrent.factor = NextNode.factor; // gets the next node and multiplies by 2
				currrent.power = NextNode.power * 2; 
				NextNode = NextNode.next;
			}

			return thisresult;
		}

		if (comp.head == null)  // null cases
		{
			return this;
		}
		if (this.head == null) 
		{
			return comp;
		}

// we need to go through each linked list and get the factors 
		while (comp.head != null) 
		{
			long compareFactor = comp.head.factor;
			long currentFactor = head.factor;
			if (compareFactor == currentFactor) 
			{
				head.power = head.power + comp.head.power; // if the factors are the same then we will add the powers
				comp.head = comp.head.next; 
				return multiply(comp); // recursive call
			}
			if (compareFactor < currentFactor) // if we are less, then we will insert it at the beginning of head
			{ 
				Node newH = new Node(compareFactor, comp.head.power);
				newH.next = head;
				head = newH;
				comp.head = comp.head.next;
			}
			if (compareFactor > currentFactor) 
			{
				Node nextone = head;
				Node previousone = nextone;
				while (compareFactor > currentFactor && nextone.next != null) // compare is greater than current and next is not null
				{
					previousone = nextone;
					nextone = nextone.next;
					currentFactor = nextone.factor;
				}
				if (nextone.next == null) // null case
				{ 
					nextone.next = comp.head;
					return this;
				}
				if (compareFactor == currentFactor) // equal power
				{ 
					nextone.power = nextone.power + comp.head.power; // move exponent
					comp.head = comp.head.next;
					return multiply(comp);
				}
				if (compareFactor < currentFactor) // this is if we could not find a matching factor
				{ 
					Node insert = new Node(compareFactor, comp.head.power);
					insert.next = head.next;
					previousone.next = insert;
					comp.head = comp.head.next;
					return multiply(comp);
				}
			}

		}

		return this;
	}

/**
 * 
 * @param a
 * @param b
 * @return BigInteger
 * this is a helper function for GCD!
 */
	public BigInteger GCDHelp(BigInteger a, BigInteger b) {
		if (b == BigInteger.ZERO) {
			return a;
		} else {
			return GCDHelp(b, a.mod(b));
		}
	}

	/**
	 * Computes greatest common divisor with given composite number.
	 * 
	 * https://en.wikipedia.org/wiki/Greatest_common_divisor
	 * 
	 * Resulting code must be O(N + M) where N is the number of factors in self and
	 * M is the number of factors in comp.
	 * 
	 * The greatest common divisor of (comp,1) is 1
	 * 
	 * When there is no common divisor (other than the value 1) this method returns
	 * a unit composite number.
	 * 
	 * @param comp other number to compute gcd
	 * @return the greatest common divisor between this and comp.
	 */
	public Composite gcd(Composite comp) 
	{
		Node HeadA = head;
		Node HeadB = comp.head;

		if (HeadA == null || HeadB == null) 
		{ // if either are unit composite
			return new Composite();
		} 
		else 
		{
			BigInteger thisvalue = this.value();
			BigInteger compvalue = comp.value();
			BigInteger gcd = GCDHelp(thisvalue, compvalue);
			return new Composite(gcd);
		}
	}

	/**
	 * Computes greatest common divisor with given composite number.
	 * 
	 * https://en.wikipedia.org/wiki/Least_common_multiple
	 * 
	 * Resulting code must be O(N + M) where N is the number of factors in self and
	 * M is the number of factors in comp.
	 * 
	 * The least common multiple of (comp,1) is comp.
	 * 
	 * @param comp other number to compute gcd
	 * @return the greatest common divisor between this and comp.
	 */
	public Composite lcm(Composite comp) 
	{
		if (head == null && comp.head == null)  // if both heads are null
		{ 
			return new Composite();
		} 
		else if (head == null) // if only the head is null
		{
			return comp;
		} 
		else if (comp.head == null) // if the comp head is null
		{
			return this;
		} 
		else 
		{
			BigInteger lcm = this.value().multiply(comp.value().divide(gcd(comp).value()));
			return new Composite(lcm);
		}
	}

	/** Helper value for factorize. */
	public final static BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);

	/**
	 * Return Composite number with linked list of factors in ascending order.
	 * 
	 * @param num
	 * @return
	 */
	public static Composite factorize(BigInteger num)
	{
		BigInteger twoval = BigInteger.ZERO;
		BigInteger workNum = num; // we don't want to modify num later in the program by overwriting it 
		BigInteger modulo = TWO;
		Node starthead = null;
		long TotalVal = twoval.longValue();
		long WorkVal = workNum.longValue();
		long ModuloVal = modulo.longValue();
		while (twoval.compareTo(num) < 0) 
		{
			if (workNum.mod(modulo) == BigInteger.ZERO) // if num mod 2 equals 0
			{
				workNum = workNum.divide(modulo);
				WorkVal = workNum.longValue();
				ModuloVal = modulo.longValue();
				if (starthead == null) // if our starting node head is null 
				{
					starthead = new Node(ModuloVal, 1);
					twoval = twoval.add(modulo);
					TotalVal = twoval.longValue();
				} 
				else 
				{
					if (starthead.factor == ModuloVal) // if our starting node factor is long value 2
					{
						starthead.power++;
						twoval = twoval.multiply(modulo);
						TotalVal = twoval.longValue();
					} 
					else 
					{
						Node current = starthead.next;
						while (true) 
						{
							boolean newNode = false;
							if (current == null) 
							{
								current = new Node(ModuloVal, 1);
								twoval = twoval.multiply(modulo);
								TotalVal = twoval.longValue();
								newNode = true;
							} 
							else if (current.factor == ModuloVal) 
							{
								current.power++;
								twoval = twoval.multiply(BigInteger.valueOf(current.factor));
								TotalVal = twoval.longValue();
								break;
							}
							else if ((current.next == null) && (current.factor != ModuloVal)) 
							{
								current = new Node(ModuloVal, 1);
								twoval = twoval.multiply(modulo);
								TotalVal = twoval.longValue();
								newNode = true;
							}
							if (newNode) 
							{
								Node lasthead = starthead;
								while (lasthead.next != null) 
								{
									lasthead = lasthead.next;
								}
								lasthead.next = current;
								break;
							}
							current = current.next;
						}
					}
				}

			} 
			else if (workNum.mod(modulo) != BigInteger.ZERO) 
			{
				modulo = modulo.add(BigInteger.ONE);
				ModuloVal = modulo.longValue();
			}
		}

		Composite newcomp = new Composite();
		newcomp.head = starthead;
		return newcomp;
	}

}