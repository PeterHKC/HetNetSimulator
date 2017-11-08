package Simulator;

import GA.GA;

public class Simulator extends GA{

	Simulator()throws Exception{}
	Simulator(int a[], int b[])throws Exception
	{
		HetNet net = new HetNet();
		net.userAssociation(a);
		net.RBAllocation(b);
		System.out.println(net.run());
	}
}
