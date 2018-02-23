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
		this.initialization();
	}
	Genetic(int in, int bn, double mr, double cr)
	{
		this.init_number = in;
		this.bit_number = bn;
		this.mutation_rate = mr;
		this.crossover_rate =cr;
		this.initialization();
	}
	public void initialization()
	{
		for(int i = 0; i < this.init_number; i++)
		{
			Solution_2 sol = new Solution_2(this.bit_number);
			chromosomes.add(sol);
		}
	}
	
	public void crossover()
	{
		for(int i = 0; i < this.init_number; i++)
		{
			if(Math.random()<this.crossover_rate)
			{
				//System.out.print("c");
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
				//System.out.print("m");
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
			//System.out.print("d");
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
		//System.out.println(this.chromosomes.get(max_index).fitness());
		//System.out.println(this.chromosomes.get(max_index).isValid());
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
	
	public static double[] runGA(int iter, int in, int bn, double mr, double cr, int sk) throws Exception
	{
		int times = 30;
		int skip = sk;
		double[] x = new double[iter/skip];
		for(double i : x)
			i = 0;
		//version++;
		Genetic ga = new Genetic(in, bn, mr, cr);
		System.out.println(",init_number: ,"+ga.init_number);
		System.out.println(",bit_number: ,"+ga.bit_number);
		System.out.println(",crossover_rate: ,"+ga.crossover_rate);
		System.out.println(",mutation_rate: ,"+ga.mutation_rate);
//		log.setLogFile(outfile
//				+String.valueOf((ga.init_number))+"_"
//				+String.valueOf((ga.bit_number))+"_"
//				+String.valueOf((ga.crossover_rate))+"_"
//				+String.valueOf((ga.mutation_rate))+"_"
//				+String.valueOf(version)+".csv");
		for(int j = 0; j < times; j++)
			for(int i = 0; i < iter; i+=skip)
			{
				ga.crossover();
				ga.mutation();
				ga.selection();
				//ga.printChromosomesFitness();
				//log.log(ga.fitness());
				System.out.println(ga.fitness());
				x[i/skip] += ga.fitness();
			}
		for(int i = 0; i < x.length; i++)
			x[i] = x[i]/times;
		return x;
	}
	
	//default args: 100 30 30 m c 
	public static void main(String[] args) throws Exception 
	{
		// TODO Auto-generated method stub
		//double i=0.5;
		String customizedName = "mutation_";
		
		//int times = 1;
			//System.out.print(j);
		for(double j = 0.2; j <= 1; j+=0.2)
		{
			LogIt log = new LogIt();
			log.setLogFile("result5/"+customizedName+String.valueOf((int)(j*10))+".csv");
			log.log("in,bn,mr,cr");
			log.log("30,30,"+String.valueOf((int)(j*10))+",i");
			StringBuilder index = new StringBuilder("");
			for(int i = 0; i <= 100; i++)
				index.append(","+String.valueOf(i));
			log.log(index);
			
			for(double i = 0.2; i <= 1; i+=0.2)
			{
				//m c
				double x[] = runGA(100, 30, 30, j, i, 1);
				StringBuilder str = new StringBuilder(String.valueOf(i)+",");
				for(double k : x)
				{
					str.append(String.valueOf(k)
							+",");
				}
				log.log(str);
			}
			log.close();
		}
//		for(int j = 0 ; j < 1; j++)
//		{
//			version = j;
//			//System.out.print(j);
//			for(int i = 10; i < 50; i+=10)
//				runGA(100, 30, 30, 0.8, 0.8, "test");
//		}
		
//		for(int i = 30; i <= 100; i++)
//		{
//			Genetic ga = new Genetic(i);
//			ga.runGA(100);
//		}
		
		/*test HetNet*/
//		LogIt log = new LogIt();
//		Genetic ga = new Genetic(1, 30, 0.8, 0.5);
//		for(int i : ga.chromosomes.get(0).getUserAssociation())
//			System.out.print(String.format("%2d", i)+" ");
//		System.out.println();
//		for(int i : ga.chromosomes.get(0).getRBAllocation())
//			System.out.print(String.format("%2d", i)+" ");
//		System.out.println(ga.fitness());
		
//		Solution_2.printMap(ga.chromosomes.get(0));
//		Solution_2.printChoose(ga.chromosomes.get(0));
//		log.log(ga.fitness());
	}

}
