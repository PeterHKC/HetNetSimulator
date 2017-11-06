package Simulator;

public class Node {
	protected String name = "";
	protected double x;
	protected double y;
	
	Node(){}
	Node(String name)
	{
		this.name = name;
	}
	Node(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	Node(String name, double x, double y)
	{
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public static double getDistance(Node n1, Node n2)
	{
		return Math.sqrt(Math.pow(n1.x-n2.x, 2) + Math.pow(n1.y-n2.y, 2));
	}
	
	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}
	
	void setXY(double x, double y) {this.x = x; this.y = y;}
	double getX() {return this.x;}
	double getY() {return this.y;}
}
