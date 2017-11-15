package GA;

public class BitString extends Chromosome
{
	
	BitString(){}
	BitString(int n){this.initialization(n);}
	
	public void initialization(int bit_number) 
	{
		this.bit_number = bit_number;
		this.x = new int[bit_number];
		do {
		//user association
		for(int i = 0; i < 30; i++)
			x[i] = (int) Math.random()*9+1;
		//RB allocation
		for(int i = 30; i < bit_number; i++)
			x[i] = (int) Math.random()*12+1;
		}while(!isValid());
	}
	
	public boolean isValid()
	{
		int[] x = new int[12];
		for(int i = 0; i < x.length; i++) x[i] = 0;
		for(int i = 30; i < this.x.length; i++)
		{
			x[this.x[i]]++;
			if(x[this.x[i]] > 9)
				return false;
		}
		return true;
	}
	
	@Override
	public void mutation(int index) {
		// TODO Auto-generated method stub
		if(this.x[index] == 1 ) super.x[index] = (int) Math.random();
		else this.x[index] = 1;
	}
	
	public void setBit(int index, int value) {super.x[index] = value;}
	
	@Override
	public Object crossover(Object ch) {
		BitString temp = new BitString(this.bit_number);
		BitString c = (BitString) ch;
		int x = (int) Math.round(Math.random()*(this.bit_number-1));
		for(int i = 0; i < this.bit_number; i++)
		{
			if(i <= x)
			{
				temp.setBit(i, this.x[i]);
			}
			else
				temp.setBit(i, c.x[i]);
		}
		return temp;
	}

	@Override
	public Object crossover(Object ch, int cut_point) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
