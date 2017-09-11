package mySimulator;
import java.util.*;
import mySimulator.*;

public class RB
{
	public ArrayList<Connection> connList = new ArrayList<Connection>();
	public ArrayList<Node> bsList = new ArrayList<Node>();
	public double totalSignal = 0.0;
	public double SC = 12;
	public double SY = 7;
	public double T = 0.5;
	
	RB(){}
	
	public void add(Connection conn)
	{
		this.connList.add(conn);
		this.bsList.add(conn.bs);
	}
	
	public void add(ArrayList<Connection> conn)
	{
		this.connList.addAll(conn);
		for(int i = 0; i < conn.size(); i++)
			this.bsList.add(conn.get(i).bs);
	}
	
	public double getTotalSignal(Node ue)
	{
		for(int i = 0; i < this.bsList.size(); i++)
		{
			Connection conn = new Connection(this.bsList.get(i),ue);
			this.totalSignal = this.totalSignal + conn.getSignal();
		}
		return this.totalSignal;
	}
	
	public double dataRate(Connection conn)
	{
		conn.setSINR(this.getTotalSignal(conn.ue));
		return (conn.efficiency()*this.SC*this.SY)/this.T;
	}
}

