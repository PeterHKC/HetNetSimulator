package GA;

public abstract class Chromosome
{
	public int[] x = null;
	public int bit_number;
	
	protected Chromosome()
	{
		
	}
	
	public Chromosome(int x[], int bit_number)
	{
		this.bit_number = bit_number;
		this.x = new int[bit_number];
		this.x = x;
	}
	
	public Chromosome(int bit_number) {
	}
	
	public void printChromosome()
	{
		for(int i = 0; i < this.bit_number; i++)
			System.out.print(x[i]);
		System.out.println("\t"+this.fitness());
	}
	
	protected abstract void initialization(int bit_number);
	public abstract void mutation(int index);
	public abstract Object crossover(Object ch);
	public abstract int fitness();
	public abstract void setFitness();
	public abstract Object crossover(Object ch, int cut_point);
}