package Simulator;

import GA.Chromosome;

public class RBAllocation extends Chromosome{
	
	RBAllocation(){}
	public RBAllocation(int n)
	{
		this.bit_number = n;
		this.initialization(n);
	}
	
	void setBit(int i, int v)
	{
		this.x[i] = v;
	}
	
	@Override
	protected void initialization(int bit_number) {
		// TODO Auto-generated method stub
		this.x = new int[bit_number];
		for(int i = 0; i < this.bit_number; i++)
			this.x[i] = (int) (Math.random()*12+1);
		
	}
	@Override
	public void mutation(int index) {
		// TODO Auto-generated method stub
		int i = this.x[index];
		while((int)(Math.random()*12+1) == i);
		this.x[index] = i;
	}
	@Override
	public Object crossover(Object ch) {
		// TODO Auto-generated method stub
		RBAllocation temp = new RBAllocation(this.bit_number);
		RBAllocation parent = (RBAllocation) ch;
		int cut_point =(int) (Math.random()*this.bit_number);
		for(int i = 0; i < this.bit_number; i++)
		{
			if(i <= cut_point)
			{
				temp.setBit(i, this.x[i]);
			}
			else
				temp.setBit(i, parent.x[i]);
		}
		return temp;
	}
	@Override
	public Object crossover(Object ch, int cut_point) {
		// TODO Auto-generated method stub
		RBAllocation temp = new RBAllocation(this.bit_number);
		RBAllocation parent = (RBAllocation) ch;
		for(int i = 0; i < this.bit_number; i++)
		{
			if(i <= cut_point)
			{
				temp.setBit(i, this.x[i]);
			}
			else
				temp.setBit(i, parent.x[i]);
		}
		return temp;
	}
}
