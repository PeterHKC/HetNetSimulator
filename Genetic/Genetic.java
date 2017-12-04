package Genetic;

import java.util.ArrayList;

import com.anselm.plm.utilobj.LogIt;

import GA.Chromosome;

public class Genetic 
{	
	public static int version = 0; 
	public int init_number = 10;
	public int bit_number = 30;
	public double mutation_rate = 0.3;
	public double crossover_rate = 0.8;
	public ArrayList<Solution_2> chromosomes = new ArrayList<Solution_2>();
	
	Genetic(){}
	Genetic(int n)
	{
		//input bit number
		this.bit_number = n;
		this.initialization(n);
	}
	Genetic(int in, int bn, double mr, double cr)
	{
		this.init_number = in;
		this.bit_number = bn;
		this.mutation_rate = mr;
		this.crossover_rate =cr;
		this.initialization(bn);
	}
	public void initialization(int n)
	{
		for(int i = 0; i < this.init_number; i++)
		{
			Solution_2 sol = new Solution_2(n);
			chromosomes.add(sol);
		}
	}
	
	public void crossover()
	{
		for(int i = 0; i < this.init_number; i++)
		{
			if(Math.random()<this.crossover_rate)
			{
				int partner = (int)(Math.random()*this.init_number);
				int ue = (int)(Math.random()*this.bit_number);
				this.chromosomes.add(this.chromosomes.get(i).crossover(this.chromosomes.get(partner), ue));
				this.chromosomes.add(this.chromosomes.get(partner).crossover(this.chromosomes.get(i), ue));
			}
		}
	}
	
	public void mutation()
	{
		for(int i = 0; i < this.init_number; i++)
		{
			if(Math.random() < this.mutation_rate)
			{
				this.chromosomes.get(i).Mutation((int)(Math.random()*this.bit_number));
				System.out.print("m");
			}
		}
	}
	
	public void selection() throws Exception
	{
		while(this.chromosomes.size() != this.init_number)
		{
			int delete_index = -1;
			double min = 9487946;
			for(int i = 0; i < this.chromosomes.size(); i++)
			{
				double m = this.chromosomes.get(i).fitness();
				if(min >= m)
				{
					delete_index = i;
					min = m;
				}
			}
			this.chromosomes.remove(delete_index);
			System.out.print("d");
			//log.log("delete: "+String.valueOf(delete_index)+" value: "+String.valueOf(min));
		}
		System.out.println();
	}
	
	public double fitness() throws Exception
	{
		int max_index = -1;
		double max = 0;
		for(int i = 0; i < this.chromosomes.size(); i++)
		{
			double m = this.chromosomes.get(i).fitness();
			if(max <= m)
			{
				max_index = i;
				max = m;
			}
		}
		return max;
	}
	
	public void printChromosomesFitness() throws Exception
	{
		System.out.println("==================================");
		for(Solution_2 sol : this.chromosomes)
		{
			System.out.print(sol.fitness());
			System.out.println(" ");
		}
	}
	
	public static void runGA(int iter, int in, int bn, double mr, double cr) throws Exception
	{
		//version++;
		LogIt log = new LogIt();
		Genetic ga = new Genetic(in, bn, mr, cr);
		System.out.println("init_number: "+ga.init_number);
		System.out.println("bit_number: "+ga.bit_number);
		System.out.println("crossover_rate: "+ga.crossover_rate);
		System.out.println("mutation_rate: "+ga.mutation_rate);
		log.setLogFile("GAvsBestCQI_"+String.valueOf((int)(ga.mutation_rate*10))+"_"+String.valueOf(version)+".csv");
		
		for(int i = 0; i < iter; i++)
		{
			ga.crossover();
			ga.mutation();
			ga.selection();
			//ga.printChromosomesFitness();
			log.log(ga.fitness());
		}
		log.close();
	}
	
	public static void main(String[] args) throws Exception 
	{
		// TODO Auto-generated method stub
		//double i=0.5;
		for(int j = 0 ; j < 10; j++)
		{
			version = j;
			for(double i = 0.2; i < 1; i+=0.2)
				runGA(100, 30, 10, i, 0.5);
		}
//		for(int i = 30; i <= 100; i++)
//		{
//			Genetic ga = new Genetic(i);
//			ga.runGA(100);
//		}
//		Genetic ga = new Genetic(30);
//		ga.runGA(1);
//		Solution_2.printMap(ga.chromosomes.get(0));
//		Solution_2.printChoose(ga.chromosomes.get(0));
//		log.log(ga.fitness());
	}

}
