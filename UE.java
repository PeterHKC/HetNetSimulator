package mySimulator;
import java.util.*;

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
	UE(UE ue, double power)
	{
		super.x = ue.x;
		super.y = ue.y;
		this.power = power;
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
