package mySimulator;
import java.util.*;
import mySimulator.*;

public class UE extends Node
{
	ArrayList<BS> bsList = new ArrayList<BS>();
	
	UE(){}
	
	UE(int id)
	{
		super.x = 1.0;
		super.y = 1.0;
		super.id = id;
	}
	
	public void addBS(ArrayList<BS> bsList)
	{
		this.bsList.addAll(bsList);
	}
}
