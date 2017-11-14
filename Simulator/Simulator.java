package Simulator;

import GA.GA;

public class Simulator extends GA{

	Simulator()throws Exception{}
	Simulator(int a[], int b[])throws Exception
	{
		HetNet net = new HetNet();
		net.userAssociation(a);
		net.RBAllocation(b);
		//System.out.println(net.run());
		net.print();
	}
	public static void main(String[] args) throws Exception
	{
		RBAllocation rba = new RBAllocation(30);
		UserAssociation uea = new UserAssociation(30);
		Simulator si = new Simulator(uea.x, rba.x);
	}
}
