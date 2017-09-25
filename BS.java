package mySimulator;
import java.util.*;

public class BS extends Node
{
	public double transmitPower = 35.2;
	public double antennaGain = 14;
	ArrayList<Node> ueList = new ArrayList<Node>();
	
	BS()
	{
		super.x = 0;
		super.y = 0;
	}
	
	public void addUE(ArrayList<Node> ueList)
	{
		this.ueList.addAll(ueList);
	}
}
