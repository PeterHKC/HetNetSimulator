package GA;

public class Chromosome
{
	public int[] x = null;
	public int bit_number;
	
	Chromosome()
	{
		
	}
	
	Chromosome(int x[], int bit_number2)
	{
		this.bit_number = bit_number2;
		this.x = new int[bit_number2];
		this.x = x;
	}
	
	Chromosome(int bit_number2)
	{
		this.initialization(bit_number2);
	}
	
	public void initialization(int bit_number)
	{
		this.bit_number = bit_number;
		this.x = new int[bit_number];
		for(int i = 0; i < bit_number; i++)
		{
			x[i] = (int) Math.round(Math.random());
		}
	}
	
	public void mutation(int index, int value)
	{
		this.x[index] = value;
	}
	
	public void mutation(int index)
	{
		if(this.x[index] == 1)
			this.x[index] = 0;
		else
			this.x[index] = 1;
	}
	
	public Chromosome crossover(Chromosome ch)
	{
		Chromosome temp = new Chromosome(this.bit_number);
		int x = (int) Math.round(Math.random()*(this.bit_number-1));
		for(int i = 0; i < this.bit_number; i++)
		{
			if(i <= x)
			{
				temp.mutation(i, this.x[i]);
			}
			else
				temp.mutation(i, ch.x[i]);
		}
		return temp;
	}
}