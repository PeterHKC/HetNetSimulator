package mySimulator;
import java.util.*;
import mySimulator.*;

public class RB
{
	public double activePower = 35.2;
	
	//ue, conn
	public HashMap<UE, Connection> connMap = new HashMap<UE,Connection>();
	public ArrayList<BS> bsList = new ArrayList<BS>();
	public double totalDistance = 0;
	
	RB(){}
	
	public void add(Connection conn)
	{
		this.connMap.put(conn.ue, conn);
		this.bsList.add(conn.bs);
		this.totalDistance = this.totalDistance + conn.getDistance();
	}
	
	public void add(ArrayList<Connection> conn)
	{
		for(int i = 0; i < conn.size(); i++)
		{
			this.connMap.put(conn.get(i).ue, conn.get(i));
			if(!bsList.contains(conn.get(i).bs))
				this.bsList.add(conn.get(i).bs);
			this.totalDistance = this.totalDistance + conn.get(i).getDistance();
		}
		for(Connection c : conn)
		{
			c.ue.setPower(c.bs.transmitPower*(c.getDistance()/this.totalDistance));
		}
	}
	
	public double getTotalSignal(UE ue)
	{
		
		double t = 0.0;
		for(int i = 0; i < this.bsList.size(); i++)
		{
			UE temp = new UE(ue, this.bsList.get(i).transmitPower-ue.power);
			temp.power = this.bsList.get(i).transmitPower;
			Connection conn = new Connection(this.bsList.get(i),temp);
			t = t + Math.exp(conn.getSignal());
		}
		
		return Math.log(t);
	}
}

