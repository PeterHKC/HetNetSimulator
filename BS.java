package mySimulator;
import java.util.*;

public class BS extends Node
{
	public double transmitPower = 46;
	public double antennaGain = 14;
	ArrayList<Node> ueList = new ArrayList<Node>();
	
	BS()
	{
		super.x = 0;
		super.y = 0;
	}
	BS(double x, double y)
	{
		super.x = x;
		super.y = y;
	}
	
	public void addUE(ArrayList<Node> ueList)
	{
		this.ueList.addAll(ueList);
	}
}
