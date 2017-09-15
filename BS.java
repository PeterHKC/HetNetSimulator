package mySimulator;
import java.util.*;
import mySimulator.*;

public class BS extends Node
{
	ArrayList<Node> ueList = new ArrayList<Node>();
	
	BS()
	{
		super.x = 10;
		super.y = 10;
		super.transmitPower = 46;
		super.antennaGain = 14;
	}
	
	public void addUE(ArrayList<Node> ueList)
	{
		this.ueList.addAll(ueList);
	}
}
