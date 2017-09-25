package mySimulator;
import java.util.*;

public class Simulator
{
	public final int UENumber = 2;
	public final int BSNumber = 2;
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
			bs.setXY(Math.random()*250+45,Math.random()*250+45);
			this.bsList.add(bs);
		}
		
		for(int i  = 0; i < UENumber; i++)
		{
			UE ue = new UE(i);
			ue.setXY(Math.random()*250+45,Math.random()*250+45);
			//ue.setXY(45,0);
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
			System.out.print("data rate Kbps: ");
			System.out.println(c.dataRate(rbList.get(0).getTotalSignal(c.ue)));
			System.out.print("bs to ue meter: ");
			System.out.println(c.getDistance());
			System.out.print("all signal dB: ");
			System.out.println(rbList.get(0).getTotalSignal(c.ue));
			System.out.print("signal dB: ");
			System.out.println(c.getSignal());
//			System.out.print("path loss dB: ");
//			System.out.println(c.pathLoss);
			System.out.print("sinr dB: ");
			System.out.println(c.SINR);
//			System.out.print("efficiency bits/symbol: ");
//			System.out.println(c.efficiency());
//			System.out.print("transmit power dBm: ");
//			System.out.println(c.ue.power);
		}
	}
	
	public void setRBAllocation()
	{
		
	}
	
	public void setUserAssociation()
	{
		for(int i = 0; i < this.ueList.size(); i++)
		{
			Connection conn = new Connection(this.bsList.get(i), this.ueList.get(i));
			this.connList.add(conn);
		}
		for(int i = 0; i < this.connList.size(); i++)
			this.rbList.get(0).add(this.connList);
	}
	
	public static void main(String[] arg) throws Exception
	{
		//System.out.println("fuck");
		Simulator si = new Simulator();
		si.run();
	}
}