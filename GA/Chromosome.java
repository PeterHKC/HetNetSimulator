package GA;

public abstract class Chromosome
{
	public int[] x = null;
	public int bit_number;
	
	Chromosome()
	{
	}
	
	Chromosome(int x[], int bit_number)
	{
		this.bit_number = bit_number;
		this.x = new int[bit_number];
		this.x = x;
	}
	
	Chromosome(int bit_number)
	{
		this.initialization(bit_number);
	}
	
	public void printChromosome()
	{
		for(int i = 0; i < this.bit_number; i++)
			System.out.print(x[i]);
		System.out.println("\t"+this.fitness());
	}
	
	private void initialization(int bit_number)
	{
		this.bit_number = bit_number;
		this.x = new int[bit_number];
		for(int i = 0; i < bit_number; i++)
		{
			x[i] = (int) Math.round(Math.random());
		}
	}
	
	public abstract void mutation(int index);
	public abstract Object crossover(Object ch);
	public abstract int fitness();
}