package mySimulator;
import java.util.*;
import mySimulator.*;

public class Simulator
{
	public final int UENumber = 3;
	public final int BSNumber = 1;
	public final int RBNumber = 1;
	ArrayList<BS> bsList = new ArrayList<BS>();
	ArrayList<RB> rbList = new ArrayList<RB>();
	ArrayList<UE> ueList = new ArrayList<UE>();
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	Simulator(){}
	
	public void run()
	{
		//initialize BS, UE, RB
		for(int i  = 0; i < BSNumber; i++)
		{
			BS bs = new BS();
			this.bsList.add(bs);
		}
		
		for(int i  = 0; i < UENumber; i++)
		{
			UE ue = new UE(i);
			ue.setXY(Math.random()*100,Math.random()*100);
			this.ueList.add(ue);
		}
		
		for(int i  = 0; i < UENumber; i++)
		{
			RB rb = new RB();
			this.rbList.add(rb);
		}
		
		//set user association. In other word, decide which bs will be assigned to which ue
		this.setUserAssociation();
		
		//print every connection's data rate
		for(Connection c : connList)
		{
			c.ue.print();
			System.out.print("data rate:");
			System.out.println(c.dataRate(rbList.get(0).getTotalSignal(c.ue)));
			System.out.print("interference signal: ");
			System.out.println(rbList.get(0).getTotalSignal(c.ue));
			System.out.print("signal: ");
			System.out.println(c.getSignal());
			System.out.print("sinr: ");
			System.out.println(c.SINR);
			System.out.print("efficiency: ");
			System.out.println(c.efficiency());
		}
	}
	
	public void setRBAllocation()
	{
		
	}
	
	public void setUserAssociation()
	{
		for(int i = 0; i < this.ueList.size(); i++)
		{
			Connection conn = new Connection(this.bsList.get(0), this.ueList.get(i));
			this.connList.add(conn);
		}
		this.rbList.get(0).add(this.connList);
	}
	
	public static void main(String[] arg) throws Exception
	{
		//System.out.println("fuck");
		Simulator si = new Simulator();
		si.run();
	}
}