package mySimulator;

public class util
{
	static double value2dB(double value)
	{
		return Math.log10(value)*10;
	}
	static double dB2Value(double value)
	{
		return Math.pow(10,(value/10));
	}
	static double subdB(double x, double y)
	{
		x = dB2Value(x);
		y = dB2Value(y);
		return value2dB(x-y);
	}
	static double adddB(double x, double y)
	{
		x = dB2Value(x);
		y = dB2Value(y);
		return value2dB(x+y);
	}
	//test
	public static void main(String[] args)
	{
		System.out.println(dB2Value(35.2));
		System.out.println(subdB(32,30));
	}
}