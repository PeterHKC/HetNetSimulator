package mySimulator;
import java.util.*;

public class RB
{
	public double activePower;
	
	//ue, conn
	public HashMap<UE, Connection> connMap = new HashMap<UE,Connection>();
	public ArrayList<BS> bsList = new ArrayList<BS>();
	public double totalDistance = 0;
	
	RB(){}
	
	public void add(Connection conn)
	{
		this.activePower = Math.log10(Math.pow(10,conn.bs.transmitPower/10)/12)*10;
		conn.ue.setPower(this.activePower);
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
			c.ue.setPower(this.activePower);
			//c.ue.setPower(10*Math.log10(Math.pow(10,c.bs.transmitPower/10))*(c.getDistance()/this.totalDistance));
		}
	}
	
	public double getTotalSignal(UE ue)
	{
		if(this.bsList.size() == 0)
		{
			return 0;
		}
		double t = 0.0;
		for(int i = 0; i < this.bsList.size(); i++)
		{
			//UE temp = new UE(ue, this.bsList.get(i).transmitPower-ue.power);
			UE temp = new UE(ue, this.activePower);
			Connection conn = new Connection(this.bsList.get(i),temp);
			t = t + Math.pow(10,conn.getSignal()/10);
		}
		
		return Math.log10(t)*10;
	}
}

