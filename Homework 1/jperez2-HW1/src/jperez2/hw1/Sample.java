package jperez2.hw1;

public class Sample 
{
	public static void main (String[] args)
	{
		int [][] ar = new int [5][5];
		for (int row=0;row<5;row++)
		{
			for (int column=0;column<5;column++)
			{
				System.out.print(ar[row][column]);
			}
			System.out.println();
		}
		
	}
}
