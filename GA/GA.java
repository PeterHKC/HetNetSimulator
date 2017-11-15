package GA;

import java.util.*;

import com.anselm.plm.utilobj.LogIt;

import Simulator.RBAllocation;
import Simulator.UserAssociation;

public class GA
{
	static LogIt log = new LogIt();
	public int init_number;
	public int bit_number;
	public double mutation_rate;
	public double crossover_rate;
	public ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	
	public GA() throws Exception
	{/*
		this.init_number = 10;
		this.bit_number = 1000;
		this.crossover_rate = 0.6;
		this.mutation_rate = 0.01;
		this.initialization();*/
	}
	
	public GA(int init_number, int bit_number, double crossover_rate, double mutation_rate) throws Exception
	{
		this.init_number = init_number;
		this.bit_number = bit_number;
		this.mutation_rate = mutation_rate;
		this.crossover_rate = crossover_rate;
	}
	
	
	public void initialization(ArrayList<Chromosome> chs) throws Exception
	{
		if(chs.size() != this.init_number)
		{
			System.out.println("invalid init number");
			System.exit(0);
		}
		for(Chromosome ch : chs)
		{
			if(ch.bit_number != this.bit_number)
			{
				System.out.println("invalid bit number");
				System.exit(0);
			}
		}
		this.chromosomes.addAll(chs);
	}
	
	public void mutationGA()
	{
		//System.out.println("fuck");
		for(int i = 0; i < this.chromosomes.size(); i++)
		{
			if(Math.random() < this.mutation_rate)
			{
				int x = (int) (Math.random()*(this.bit_number));
//				System.out.println(x);
				this.chromosomes.get(i).mutation(x);
//				System.out.println("mutation: "+i+" in index:"+x);
			}
		}
	}
	
	public void crossoverGA()
	{
		ArrayList<Chromosome> temp = new ArrayList<Chromosome>();
		for(int i = 0; i < this.chromosomes.size(); i++)
		{
			if(Math.random() < this.crossover_rate)
			{
				int partner = (int) Math.round(Math.random()*(this.init_number-1));
				while(partner == i)
				{
					partner = (int) Math.round(Math.random()*(this.init_number-1));
				}
//				System.out.println("crossover: "+i+" and "+partner+"\t");
				int cut = (int) Math.round(Math.random()*(this.bit_number-1));
				temp.add((Chromosome) this.chromosomes.get(i).crossover(this.chromosomes.get(partner),cut));
				temp.add((Chromosome) this.chromosomes.get(partner).crossover(this.chromosomes.get(partner),cut));
			}
		}
		this.chromosomes.addAll(temp);
	}
	
	public void selection()
	{
		while(this.chromosomes.size()!=this.init_number)
		{
			int delete_index = 0;
			double min = 100000;
			for(int i = 0; i < this.chromosomes.size(); i++)
			{
				double m = this.chromosomes.get(i).fitness();
				if(min >= m)
				{
					delete_index = i;
					min = m;
				}
			}
			//System.out.println("delete: "+String.valueOf(delete_index)+" fitness: "+String.valueOf(min)+" size: "+String.valueOf(this.chromosomes.size()));
			this.chromosomes.remove(delete_index);
		}
	}
	
	public void print(String args)
	{
		for(int i = 0; i < this.chromosomes.size(); i++)
		{
			if(args.equals("all"))
				this.chromosomes.get(i).printChromosome();
			else
				System.out.println("Value: "+this.chromosomes.get(i).fitness());
		}
		System.out.println("-----size="+this.chromosomes.size()+"-----");
	}
	public void print()
	{
		for(int i = 0; i < this.chromosomes.size(); i++)
		{
			System.out.println("Value: "+this.chromosomes.get(i).fitness());
		}
		System.out.println("-----size="+this.chromosomes.size()+"-----");
	}
	
	public void startGA(int iter)
	{
		System.out.println("init_number: "+this.init_number);
		System.out.println("bit_number: "+this.bit_number);
		System.out.println("crossover_rate: "+this.crossover_rate);
		System.out.println("mutation_rate: "+this.mutation_rate);
		
		//int x = this.findMinChromosomes();
		//this.chromosomes.get(x).printChromosome();
		//this.chromosomes.get(0).crossover(this.chromosomes.get(1)).printChromosome();
		this.print("all");
		
		for(int i = 0; i < iter; i ++)
		{
//			System.out.println("====i="+i+"====");
			this.crossoverGA();
			this.mutationGA();
			//this.print();
			this.selection();
			//this.print("all");
//			System.out.println("================");
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		log.setLogFile("GA.log");
		/*
		arg1: number of initialized chromosomes
		arg2: bit-string length
		arg3: crossover rate
		arg4: mutation rate
		arg5: number of iteration
		arg6: print style. "all": print chromosomes;
		*/
		
		if(args.length == 0)
		{
			ArrayList<Chromosome> rba = new ArrayList<Chromosome>();
			ArrayList<Chromosome> uea = new ArrayList<Chromosome>();
			GA ga_rba = new GA(10,30,0.8,0.05);
			GA ga_uea = new GA(10,30,0.8,0.05);
			for(int i = 0; i < 10; i++)
			{
				rba.add((Chromosome)new RBAllocation(30));
				uea.add((Chromosome)new UserAssociation(30));
			}
			ga_rba.initialization(rba);
			ga_uea.initialization(uea);
			ga_rba.startGA(20000);
			ga_uea.startGA(20000);
			ga_rba.print("all");
			ga_uea.print("all");
		}
		else if(args.length == 6 || args.length == 5)
		{
			System.out.println("iterations: "+args[4]);
			GA ga = new GA(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Double.valueOf(args[2]),Double.valueOf(args[3]));
			ga.startGA(Integer.parseInt(args[4]));
			if(args.length == 6)
				ga.print(args[5]);
			else
				ga.print();
		}
		else
			System.out.println("Usage: GA.java: line:138");
	}
}


