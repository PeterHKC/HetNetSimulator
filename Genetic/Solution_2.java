package Genetic;

import java.util.HashMap;
import java.util.Vector;

import com.anselm.plm.utilobj.LogIt;

import Simulator.HetNet;

public class Solution_2 {

	private int BSNumber = 9;
	private int RBNumber = 12;
	private int UENumber = 100;
	
	public HashMap<Integer,Vector<Integer>> choose = new HashMap<Integer,Vector<Integer>>();
	private int map[][];
	
	
	Solution_2(){}
	Solution_2(int n)
	{
		this.UENumber = n;
		this.map = this.genMap();
		this.setChoose();
	}
	Solution_2(HashMap<Integer,Vector<Integer>> choose)
	{
		this.choose.putAll(choose);
		this.setMap();
	}
	Solution_2(int map[][])
	{
		this.map = map;
		this.setChoose();
	}
	
	private int[][] genMap()
	{
		
		this.initMap();
		for(int i = 0; i < this.UENumber;)
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
				i++;
			}
		}
		return map;
	}
	
	private void initMap()
	{
		this.map = new int[this.BSNumber][this.RBNumber];
		for(int i = 0; i < this.BSNumber; i++)
			for(int j = 0; j < this.RBNumber; j++)
				this.map[i][j] = -1;
	}
	
	private boolean setMap()
	{
		this.initMap();
		for(int i : this.choose.keySet())
		{
			int x = this.choose.get(i).get(0);
			int y = this.choose.get(i).get(1);
			if(this.map[x][y] == -1)
				this.map[x][y] = i;
			else
			{
				this.recover(i, x, y);
			}
		}
		return true;
	}
	private boolean setChoose()
	{
		for(int i = 0; i < this.BSNumber; i++)
		{
			for(int j = 0; j < this.RBNumber; j++)
			{
				if(this.map[i][j] == -1)
					continue;
				
				Vector<Integer> v = new Vector<Integer>(2);
				if(this.choose.containsKey(this.map[i][j]))
				{
					System.out.print("error: HashMap: duplicate key");
					return false;
				}
				else
				{
					v.add(i);v.add(j);
					this.choose.put(this.map[i][j], v);
				}
			}
		}
		return true;
	}
	
	public boolean isValid()
	{
		int count = 0;
		for(int i = 0; i < this.BSNumber; i++)
			for(int j = 0; j < this.RBNumber; j++)
				count+=this.map[i][j];
		//check map is valid
		if(count != (0+this.UENumber-1)*this.UENumber/2-this.BSNumber*this.RBNumber+this.UENumber)
		{
			System.out.print("error: "+String.valueOf(count)+" ");
			System.out.println((0+this.UENumber-1)*this.UENumber/2-this.BSNumber*this.RBNumber+this.UENumber);
			return false;
		}
		return true;
	}
	public boolean Mutation(int ue)
	{
		for(int i : this.choose.keySet())
		{
			if(i == ue)
			{
				this.map[this.choose.get(i).get(0)][this.choose.get(i).get(1)] = -1;
				boolean ret =this.recover(i, this.choose.get(i).get(0), this.choose.get(i).get(1));
				if(ret == false)
					return false;
			}
		}
		return true;
	}
	
	public Solution_2 crossover(Solution_2 sol, int ue)
	{
		HashMap<Integer,Vector<Integer>> temp = new HashMap<Integer,Vector<Integer>>();
		for(int i : this.choose.keySet())
		{
			Vector<Integer> v = new Vector<Integer>(2);
			if(i > ue)
			{
				v.add(sol.choose.get(i).get(0));
				v.add(sol.choose.get(i).get(1));
				temp.put(i, v);
			}
			else
			{
				v.add(this.choose.get(i).get(0));
				v.add(this.choose.get(i).get(1));
				temp.put(i, v);
			}	
		}
		return new Solution_2(temp);
	}
	
	private boolean recover(int ue,int bs, int rb)
	{
		if(bs >= BSNumber)
			bs = (int) (Math.random()*BSNumber);
		if(rb >= RBNumber)
			rb = (int) (Math.random()*RBNumber);
		int finish = 0;
		for(int i = 0; true; i+=90)
		{
			int offset = i/360;
			double x = bs + Math.sin(Math.toRadians(i)) + offset;
			double y = rb + Math.cos(Math.toRadians(i)) + offset;
			if(x >= this.BSNumber || y >= this.RBNumber || x < 0 || y < 0)
			{
				finish++;
				if(finish > 4)
					return false;
				continue;
			}
			if(this.map[(int)x][(int)y] == -1)
			{
				this.map[(int)x][(int)y] = ue;
				this.choose.get(ue).clear();
				Vector<Integer> v = new Vector<Integer>();
				v.add((int)x);
				v.add((int)y);
				this.choose.put(ue, v);
				return true;
			}
		}
	}
	
	public int[] getUserAssociation()
	{
		int x[] = null;
		x = new int[this.UENumber];
		for(int i = 0; i < this.choose.size(); i++)
			x[i] = this.choose.get(i).get(0);
		return x;
	}
	public int[] getRBAllocation()
	{
		int x[] = null;
		x = new int[this.UENumber];
		for(int i = 0; i < this.choose.size(); i++)
			x[i] = this.choose.get(i).get(1);
		return x;
	}
	
	public static void printMap(Solution_2 sol)
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
	public static void printChoose(Solution_2 sol)
	{
		for(int i : sol.choose.keySet())
		{
			System.out.println(String.valueOf(i)+": "+sol.choose.get(i));
		}
	}
	
	public double fitness() throws Exception
	{
		HetNet net = new HetNet("config1.csv", this.UENumber);
		boolean ret = net.userAssociation(this.getUserAssociation());
		boolean ret2 = net.RBAllocation(this.getRBAllocation());
		double th = net.getTotalThroughput();
		//net.print();
		return th;
	}
	
	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		LogIt log = new LogIt();
		Solution_2 sol = new Solution_2(30);
		Solution_2.printChoose(sol);
		Solution_2.printMap(sol);
		
		Solution_2 sol2 = new Solution_2(30);
		Solution_2.printChoose(sol2);
		Solution_2.printMap(sol2);
		
		Solution_2 sol3 = sol.crossover(sol2, 12);
		Solution_2.printChoose(sol3);
		Solution_2.printMap(sol3);
		
//		for(int i : sol.getRBAllocation())
//			log.log(i);
		
		log.log(sol3.fitness());
		
		//test mutation
//		sol.Mutation(12);
//		Solution_2.printChoose(sol);
//		Solution_2.printMap(sol);
//		log.log(sol.isValid());
	}

}
