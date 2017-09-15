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
	
	public void init()
	{
		for(int i  = 0; i < BSNumber; i++)
		{
			BS bs = new BS();
			this.bsList.add(bs);
		}
		
		for(int i  = 0; i < UENumber; i++)
		{
			UE ue = new UE(i);
			this.ueList.add(ue);
		}
		
		for(int i  = 0; i < UENumber; i++)
		{
			RB rb = new RB();
			this.rbList.add(rb);
		}
		this.setUserAssociation();
		System.out.println(this.connList.get(0).dataRate(this.rbList.get(0)));
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
		si.init();
		
	}
}