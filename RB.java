package mySimulator;
import java.util.*;
import mySimulator.*;

public class RB
{
	//ue, conn
	public HashMap<Node, Connection> connMap = new HashMap<Node,Connection>();
	public ArrayList<Node> bsList = new ArrayList<Node>();

	
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
		double t = 0.0;
		for(int i = 0; i < this.bsList.size(); i++)
		{
			Connection conn = new Connection(this.bsList.get(i),ue);
			t = t + Math.exp(conn.getSignal());
		}
		return Math.log(t);
	}
}

