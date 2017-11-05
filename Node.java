package mySimulator;

public class Node
{
	public int id;
	public double x;
	public double y;
	public String name;
	
	Node(){this.id = this.hashCode();}
	Node(double x, double y)
	{
		this.id = this.hashCode();
		this.x = x;
		this.y = y;
	}
	
	public void setXY(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void print()
	{
		for(int i = 0; i < 30; i++)
		System.out.print("-");
		System.out.println("\nnode: "+this.name+" in ("+String.valueOf(this.x)+", "+String.valueOf(this.y)+")\t");
	}
}