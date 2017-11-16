package Simulator;

import java.util.ArrayList;

import com.anselm.plm.utilobj.LogIt;

import GA.Chromosome;
import GA.GA;

public class Simulator{

	public ArrayList<Chromosome> ueaList = new ArrayList<Chromosome>();
	public ArrayList<Chromosome> rbaList = new ArrayList<Chromosome>();
	
	public int init_number;
	public int bit_number;
	public double mutation_rate;
	public double crossover_rate;
	
	
	Simulator()throws Exception{}
	Simulator(int init_number, int bit_number, double crossover_rate, double mutation_rate) throws Exception
	{
		this.init_number = init_number;
		this.bit_number = bit_number;
		this.mutation_rate = mutation_rate;
		this.crossover_rate = crossover_rate;
	}
	
	public void initialization(ArrayList<Chromosome> uea, ArrayList<Chromosome> rba) throws Exception
	{
		if(uea.size() != this.init_number || uea.size() != rba.size())
		{
			System.out.println("invalid init number");
			System.exit(0);
		}
		for(Chromosome ch : uea)
		{
			if(ch.bit_number != this.bit_number)
			{
				System.out.println("invalid bit number");
				System.exit(0);
			}
		}
		for(Chromosome ch : rba)
		{
			if(ch.bit_number != this.bit_number)
			{
				System.out.println("invalid bit number");
				System.exit(0);
			}
		}
		this.ueaList.addAll(uea);
		this.rbaList.addAll(rba);
	}
	
	
	public void mutation()
	{
		
		for(int i = 0; i < this.rbaList.size(); i++)
		{
			if(Math.random() < this.mutation_rate)
			{
				int x = (int) (Math.random()*(this.bit_number));
//				System.out.println(x);
				this.rbaList.get(i).mutation(x);
				this.ueaList.get(i).mutation(x);
				int count = 0;
				while(!this.isValid(this.ueaList.get(i), this.rbaList.get(i)))
				{
					count++;
					if(count == 12)
						break;
					this.rbaList.get(i).mutation(x);
					this.ueaList.get(i).mutation(x);
				}
//				System.out.println("mutation: "+i+" in index:"+x);
			}
		}
		//System.out.print("m");
	}
	
	public void fitness() throws Exception
	{
		
		for(int i = 0; i < this.ueaList.size(); i++)
		{
			HetNet net = new HetNet("config1.csv", 60);
			boolean u = net.userAssociation(this.ueaList.get(i).x);
			boolean r = net.RBAllocation(this.rbaList.get(i).x);
			
			double th = net.getTotalThroughput();
			if(u == false || r == false)
				th = 0;
			this.ueaList.get(i).setFitness(th);
			this.rbaList.get(i).setFitness(th);
		}
	}
	public void crossover()
	{
		
		ArrayList<Chromosome> ueatemp = new ArrayList<Chromosome>();
		ArrayList<Chromosome> rbatemp = new ArrayList<Chromosome>();
		for(int i = 0; i < this.ueaList.size(); i++)
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
				
				RBAllocation rba = (RBAllocation) this.rbaList.get(i).crossover(this.rbaList.get(partner),cut);
				UserAssociation uea = (UserAssociation) this.ueaList.get(i).crossover(this.ueaList.get(partner),cut);
				int count = 0;
				while(!this.isValid(uea,rba))
				{
					//System.out.print("c1");
					count++;
					if(count==this.bit_number)
						break;
					cut = (int) Math.round(Math.random()*(this.bit_number-1));
					rba = (RBAllocation) this.rbaList.get(i).crossover(this.rbaList.get(partner),cut);
					uea = (UserAssociation) this.ueaList.get(i).crossover(this.ueaList.get(partner),cut);
				}
				rbatemp.add(rba);
				ueatemp.add(uea);
				count = 0;
				while(!this.isValid(uea,rba))
				{
					//System.out.print("c2");
					count++;
					if(count==this.bit_number)
						break;
					cut = (int) Math.round(Math.random()*(this.bit_number-1));
					rba = (RBAllocation) this.rbaList.get(partner).crossover(this.rbaList.get(i),cut);
					uea = (UserAssociation) this.ueaList.get(partner).crossover(this.ueaList.get(i),cut);
				}
				if(count!=30)
				{
					rbatemp.add(rba);
					ueatemp.add(uea);
				}
			}
		}
		this.ueaList.addAll(ueatemp);
		this.rbaList.addAll(rbatemp);
		//System.out.print("c");
	}
	
	public void selection()
	{
		while(this.ueaList.size()!=this.init_number)
		{
			int delete_index = 0;
			double min = 100000;
			for(int i = 0; i < this.ueaList.size(); i++)
			{
				double m = this.ueaList.get(i).fitness();
				if(min >= m)
				{
					delete_index = i;
					min = m;
				}
			}
			//System.out.println("delete: "+String.valueOf(delete_index)+" fitness: "+String.valueOf(min)+" size: "+String.valueOf(this.chromosomes.size()));
			this.ueaList.remove(delete_index);
			this.rbaList.remove(delete_index);
		}
	}
	
	public static double run(int in, int bn, double cr, double mr, int iter) throws Exception
	{
		/*
		 * arg1: initialization number
		 * arg2: UE number (bit number)
		 * arg3: crossover rate
		 * arg4: mutation rate
		 * arg5: iter number
		 */
		ArrayList<Chromosome> rba = new ArrayList<Chromosome>();
		ArrayList<Chromosome> uea = new ArrayList<Chromosome>();
		Simulator si = new Simulator(in,bn,cr,mr);
		
		for(int i = 0; i < in; i++)//10 init_number
		{
			RBAllocation rba1 = new RBAllocation(bn);
			UserAssociation uea1 = new UserAssociation(bn);
			while(!Simulator.isValid(uea1, rba1))
			{
				rba1 = new RBAllocation(bn);
				uea1 = new UserAssociation(bn);
			}
			rba.add(rba1);//30 bits
			uea.add(uea1);
		}
		
		si.initialization(uea, rba);
		si.fitness();
		//si.print();
		for(int i = 0; i < 10; i++) System.out.print("=");
		System.out.println("");
		for(int i = 0; i < iter; i++)
		{
			System.out.print(".");
			if(i%50 == 49)
				System.out.println();
			si.crossover();
			si.mutation();
			si.fitness();
			si.selection();
		}
		//si.print();
		double th = si.value();
		System.out.println(th);
		return th;
	}
	public double value()
	{
		double th = 0;
		for(Chromosome rba : this.rbaList)
			if(th < rba.fitness())
				th=rba.fitness();
		return th;
	}
	public void print()
	{
		System.out.println("");
		for(Chromosome rba : this.rbaList)
		{
			System.out.println(rba.fitness());
		}
	}
	
	public static boolean isValid(Chromosome uea, Chromosome rba)
	{
		int[] x = new int[uea.x.length];
		for(int i = 0; i < x.length; i++) 
			x[i] = 0;
		
		for(int i = 0; i < uea.x.length; i++)
		{
			x[uea.x[i]-1]++;
			if(x[uea.x[i]-1] > 12)
			{
				System.out.println(String.valueOf(uea.x[i])+"over BS load");
				return false;
			}
			for(int j = i+1; j < uea.x.length; j++)
				if(uea.x[i] == uea.x[j] && rba.x[i] == rba.x[j])
				{
					System.out.println(String.valueOf(i)+"and"+String.valueOf(j));
					return false;
				}
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception
	{
		for(int j = 20; j <= 100; j+=20)
		{
			LogIt log = new LogIt();
			log.setLogFile("fig4_mutation_"+String.valueOf(j)+".csv");
			log.log("fig4_mutation_"+String.valueOf(j));
			for(int i = 20; i <= 500; i+=20)
			{
				log.log(run(10,30,0.8,0.2,i));
			}
		}
	}
}
