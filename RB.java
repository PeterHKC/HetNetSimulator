package mySimulator;
import java.util.*;
import mySimulator.*;

public class RB
{
	public HashMap<Node, Connection> connMap = new HashMap<Node,Connection>();
	public ArrayList<Node> bsList = new ArrayList<Node>();
	public double totalSignal = 0.0;
	public double SC = 12;
	public double SY = 7;
	public double T = 0.5;
	
	RB(){}
	
	public void add(Connection conn)
	{
		this.connMap.put(conn.ue, conn);
		this.bsList.add(conn.bs);
	}
	
	public void add(ArrayList<Connection> conn)
	{
		for(int i = 0; i < conn.size(); i++)
		{
			this.connMap.put(conn.get(i).ue, conn.get(i));
			this.bsList.add(conn.get(i).bs);
		}
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
	
	
}

