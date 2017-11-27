package Genetic;

import java.util.ArrayList;
import java.util.Vector;

import com.anselm.plm.utilobj.LogIt;

public class Solution 
{
	final private int BSNumber = 9;
	final private int RBNumber = 12;
	
	public ArrayList<Vector<Integer>> choose = new ArrayList<Vector<Integer>>();
	private int map[][];
	private int number = 30;
	
	Solution()
	{
		this.map = new int[this.BSNumber][this.RBNumber];
		
		//initialize data structure
		this.initial(this.number);
	}
	Solution(int n)
	{
		this.map = new int[this.BSNumber][this.RBNumber];
		this.initial(n);
	}
	
	public void initial(int number)
	{
		this.number = number;
		for(int i = 0; i < BSNumber; i++)
			for(int j = 0; j < RBNumber; j++)
				this.map[i][j] = -1;
		for(int i = 0; i < number;)
		{
			int BSUsedNumber = 0;
			int x = (int) (Math.random()*BSNumber);
			int y = (int) (Math.random()*RBNumber);
			if(this.map[x][y] == -1 || BSUsedNumber > -3)
			{
				Vector<Integer> v = new Vector<Integer>(2);
				int[] ch = new int[2];
				ch[0] = x;
				ch[1] = y;
				v.add(x);
				v.add(y);
				this.map[x][y] = i;
				this.choose.add(v);
				i++;
			}
		}
	}
	
	public static void printMap(Solution sol)
	{
		for(int i = 0; i < sol.map.length; i++)
		{
			for(int j = 0; j < sol.map[i].length; j++)
			{
				String str = String.format("%2d", sol.map[i][j]);
				System.out.print(str+" ");
			}
			System.out.print("\n");
		}
	}
	
	public static void printChoose(Solution sol)
	{
		for(Vector<Integer> vec : sol.choose)
		{
			System.out.println(vec);
		}
	}
	
	
	
	public int[] getUserAssociation()
	{
		int x[] = null;
		x = new int[this.number];
		for(int i = 0; i < this.choose.size(); i++)
			x[i] = this.choose.get(i).get(0);
		return x;
	}
	public int[] getRBAllocation()
	{
		int x[] = null;
		x = new int[this.number];
		for(int i = 0; i < this.choose.size(); i++)
			x[i] = this.choose.get(i).get(1);
		return x;
	}
	
	public static Solution crossover(Solution sol, int index)
	{
		
		
		return null;
	}
	
	public static void main(String[] args)
	{
		LogIt log = new LogIt();
		
		Solution sol = new Solution();
		Solution.printMap(sol);
		Solution.printChoose(sol);
//		for(int i : sol.getUserAssociation())
//			log.log(i);
//		for(int i : sol.getRBAllocation())
//			log.log(i);
	}
}
