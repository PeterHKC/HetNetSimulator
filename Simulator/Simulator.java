package Simulator;

import GA.Chromosome;
import GA.GA;

public class Simulator extends GA{

	Simulator()throws Exception{}
	Simulator(Chromosome uea, Chromosome rba)throws Exception
	{
		HetNet net = new HetNet("config1.csv", 60);
		net.userAssociation(uea.x);
		net.RBAllocation(rba.x);
		
		double th = net.getTotalThroughput();
		System.out.println(th);
//		net.print();
	}
	public static void main(String[] args) throws Exception
	{
		int bit = 30;
		RBAllocation rba = new RBAllocation(30);
		UserAssociation uea = new UserAssociation(30);
		rba.initialization(bit);
		uea.initialization(bit);
		Simulator si = new Simulator(uea, rba);
		
	}
}
