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
	
	Node(){}
	Node(double x, double y)
	{
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
		this.id = this.hashCode();
		System.out.print("node: "+Integer.toHexString(this.hashCode())+" in ("+String.valueOf(this.x)+", "+String.valueOf(this.y)+")\t");
	}
}