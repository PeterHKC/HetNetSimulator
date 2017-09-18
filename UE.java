package mySimulator;
import java.util.*;
import mySimulator.*;

public class UE extends Node
{
	ArrayList<Node> bsList = new ArrayList<Node>();
	double power = 0;
	
	UE(){}
	
	UE(int id)
	{
		super.x = 1.0+id;
		super.y = 1.0+id;
		super.id = id;
	}
	
	public void setPower(double power)
	{
		this.power = power;
	}
	
	public void addBS(ArrayList<Node> bsList)
	{
		this.bsList.addAll(bsList);
	}
}
