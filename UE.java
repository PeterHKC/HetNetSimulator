package mySimulator;
import java.util.*;
import mySimulator.*;

public class UE extends Node
{
	ArrayList<Node> bsList = new ArrayList<Node>();
	
	UE(){}
	
	UE(int id)
	{
		super.x = 1.0+id;
		super.y = 1.0+id;
		super.id = id;
	}
	
	public void addBS(ArrayList<Node> bsList)
	{
		this.bsList.addAll(bsList);
	}
}
