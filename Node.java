package mySimulator;
import java.util.*;
import mySimulator.*;

public class Node
{
	public int id;
	public double x;
	public double y;
	// dB
	public double transmitPower;
	public double antennaGain;
	
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
		System.out.print("node: "+Integer.toHexString(this.hashCode())+" in ("+String.valueOf(this.x)+", "+String.valueOf(this.y)+")\t");
	}
}