package GA;

import java.util.ArrayList;

public class GA
{
	public int init_number;
	public int bit_number;
	public double mutation_rate;
	public double crossover_rate;
	public ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	
	public GA() throws Exception
	{
		/*
		this.init_number = 10;
		this.bit_number = 1000;
		this.crossover_rate = 0.6;
		this.mutation_rate = 0.01;
		this.initialization();
		*/
	}
	
	public GA(int init_number, int bit_number, double crossover_rate, double mutation_rate) throws Exception
	{
		this.init_number = init_number;
		this.bit_number = bit_number;
		this.mutation_rate = mutation_rate;
		this.crossover_rate = crossover_rate;
		this.initialization();
	}
	
	
	public void initialization() throws Exception
	{
		System.out.println("init_number: "+this.init_number);
		System.out.println("bit_number: "+this.bit_number);
		System.out.println("crossover_rate: "+this.crossover_rate);
		System.out.println("mutation_rate: "+this.mutation_rate);
		for(int i = 0; i < this.init_number; i++)
		{
			this.chromosomes.add(new Chromosome(this.bit_number));
		}
	}
	
	public void mutationGA()
	{
		for(int i = 0; i < this.chromosomes.size(); i++)
		{
			if(Math.random() < this.mutation_rate)
			{
				int x = (int) Math.round(Math.random()*(this.bit_number-1));
				this.chromosomes.get(i).mutation(x);
				//System.out.println("mutation: "+i+" in index:"+x);
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
				//System.out.println("crossover: "+i+" and "+partner+"\t");
				while(partner != i)
				{
					partner = (int) Math.round(Math.random()*(this.init_number-1));
				}
				temp.add(this.chromosomes.get(i).crossover(this.chromosomes.get(partner)));
				temp.add(this.chromosomes.get(partner).crossover(this.chromosomes.get(i)));
			}
		}
		this.chromosomes.addAll(temp);
	}
	
	public int findMinChromosomes()
	{
		int min = this.bit_number+1, index = 0;
		ArrayList<Integer> values = new ArrayList<Integer>();
		for(int i = 0; i < this.chromosomes.size(); i ++)
		{
			int f = this.fitness(this.chromosomes.get(i));
			values.add(f);
			if(min > f)
			{
				min = this.fitness(this.chromosomes.get(i));
				index = i;
			}
		}
		return index;
	}
	
	public void selection()
	{
		for(int i = 0; this.chromosomes.size()!=this.init_number; i++)
		{
			int delete_index = this.findMinChromosomes();
			//System.out.print("delete: "+delete_index+"\t");
			//this.chromosomes.get(delete_index).printChromosome();
			this.chromosomes.remove(delete_index);
		}
	}
	
	public void print(String args)
	{
		for(int i = 0; i < this.chromosomes.size(); i++)
		{
			if(args.equals("all"))
				this.print(this.chromosomes.get(i));
			else
				System.out.println("Value: "+this.fitness(this.chromosomes.get(i)));
		}
		System.out.println("-----size="+this.chromosomes.size()+"-----");
	}
	public void print()
	{
		for(int i = 0; i < this.chromosomes.size(); i++)
		{
			System.out.println("Value: "+ this.fitness(this.chromosomes.get(i)));
		}
		System.out.println("-----size="+this.chromosomes.size()+"-----");
	}
	
	public void startGA(int iter)
	{
		System.out.println("iterations: "+String.valueOf(iter));
		for(int i = 0; i < iter; i ++)
		{
			this.crossoverGA();
			this.mutationGA();
			this.selection();
		}
	}
	
	public void print(Chromosome ch)
	{
		for(int i = 0; i < this.bit_number; i++)
			System.out.print(ch.x[i]);
		System.out.println("\t"+this.fitness(ch));
	}
	
	public int fitness(Chromosome ch)
	{
		int fitness_value = 0;
		for (int i = 0; i < this.bit_number; i++)
			fitness_value = ch.x[i] + fitness_value;
		
		return fitness_value;
	}
	
	public static void main(String[] args) throws Exception
	{
		/*
		arg1: number of initialized chromosomes
		arg2: bitstring length
		arg3: crossover rate
		arg4: mutation rate
		arg5: number of iteration
		arg6: print style. "all": print chromosomes;
		*/
		
		if(args.length == 0)
		{
			GA ga = new GA(10,100,0.8,0.05);
			ga.startGA(100);
			ga.print("");
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

	public int fitness() {
		// TODO Auto-generated method stub
		return 0;
	}
}


