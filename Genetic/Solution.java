package Genetic;

import java.util.HashMap;
import java.util.Vector;

import com.anselm.plm.utilobj.LogIt;

public class Solution 
{
	/*
	 * data structure
	 * 
	 * map: int[][]
	 *   01........RB
	 * 01[][][][][][]
	 * 02[][][][][][]
	 * .............
	 * .............
	 * BS[]........[]
	 * 
	 * choose: HashMap, key: UE, value: <BS,RB>
	 */
	final private int BSNumber = 9;
	final private int RBNumber = 12;
	
	public HashMap<Integer,Vector<Integer>> choose = new HashMap<Integer,Vector<Integer>>();
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
			int x = (int) (Math.random()*BSNumber);
			int y = (int) (Math.random()*RBNumber);
			if(this.map[x][y] == -1)
			{
				Vector<Integer> v = new Vector<Integer>(2);
				int[] ch = new int[2];
				ch[0] = x;
				ch[1] = y;
				v.add(x);
				v.add(y);
				this.map[x][y] = i;
				//this.choose.put(i, v);
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
		for(int i : sol.choose.keySet())
		{
			System.out.println(String.valueOf(i)+": "+sol.choose.get(i));
		}
	}
	
	public boolean setMap(int i, int j, int ue)
	{
		this.map[i][j] = ue;
		return true;
	}
	
	public boolean setChoose()
	{
		//this.choose.clear();
		HashMap<Integer,Vector<Integer>> temp = new HashMap<Integer,Vector<Integer>>();
		int count = 0;
		for(int i = 0; i < this.BSNumber; i++)
		{
			for(int j = 0; j < this.RBNumber; j++)
			{
				count+=this.map[i][j];
				if(this.map[i][j] == -1)
					continue;
				
				Vector<Integer> v = new Vector<Integer>(2);
				if(temp.containsKey(this.map[i][j]))
				{
					System.out.print("debug");
					return false;
				}
				else
				{
					v.add(i);v.add(j);
					temp.put(this.map[i][j], v);
				}
			}
		}
		//check map is valid
		if(count != (0+this.number-1)*this.number/2-this.BSNumber*this.RBNumber+this.number)
		{
			System.out.println(count);
			System.out.println((0+this.number-1)*this.number/2-this.BSNumber*this.RBNumber+this.number);
			return false;
		}
		this.choose.clear();
		this.choose.putAll(temp);
		return true;
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
	
	public Solution crossover(Solution sol, int index)
	{
		Solution newsol = new Solution();
		for(int i = 0; i < this.number; i++)
		{
			
		}
		
		return null;
	}
	
	public boolean mutation(int index)
	{
		for(int i = 0; i < this.BSNumber; i++)
		{
			for(int j = 0; j <this.RBNumber; j++)
			{
				if(this.map[i][j] == index)
				{
					this.map[i][j] = -1;
					this.recover(index, i, j);
					return true;
				}
			}
		}
		return false;
	}
	
	private void recover(int ue,int bs, int rb)
	{
		for(int i = 0; ; i+=90)
		{
			int offset = i/360;
			double x = bs + Math.sin(Math.toRadians(i)) + offset;
			double y = rb + Math.cos(Math.toRadians(i)) + offset;
			if(this.map[(int)x][(int)y] == -1)
			{
				this.map[(int)x][(int)y] = ue;
			}
		}
	}
	
	public static void main(String[] args)
	{
		LogIt log = new LogIt();
		
		Solution sol = new Solution();
		Solution.printMap(sol);
		log.log(sol.setChoose());
		Solution.printChoose(sol);
//		for(int i : sol.getUserAssociation())
//			log.log(i);
//		for(int i : sol.getRBAllocation())
//			log.log(i);
	}
}
