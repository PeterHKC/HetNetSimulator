package mySimulator;
import java.util.*;
import mySimulator.*;

public class BS extends Node
{
	ArrayList<UE> ueList = new ArrayList<UE>();
	
	BS()
	{
		super.x = 10;
		super.y = 10;
		super.transmitPower = 46;
		super.antennaGain = 14;
	}
	
	public void addUE(ArrayList<UE> ueList)
	{
		this.ueList.addAll(ueList);
	}
}
