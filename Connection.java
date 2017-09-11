package mySimulator;
import java.util.*;
import mySimulator.*;

public class Connection
{
	public Node bs;
	public Node ue;
	public double dis;
	
	public double pathLoss;
	public double N0 = 9;
	public double SINR;
	
	Connection(){}
	Connection(Node bs, Node ue)
	{
		this.bs = bs;
		this.ue = ue;
		this.pathLoss = this.pathLossModel(this.getDistance()/1000);
	}
	
	private double pathLossModel(double d)
	{
		// km
		return 140.7 + 36.7*Math.log(d);
	}
	
	public double getDistance()
	{
		this.dis = Math.sqrt(Math.pow(this.bs.x-this.ue.x, 2)+Math.pow(this.bs.y-this.ue.y, 2));
		
		return this.dis;
	}
	
	public double getSignal()
	{
		return this.bs.transmitPower + this.bs.antennaGain - this.pathLoss;
	}
	
	public void setSINR(double totalSignal)
	{
		this.SINR = this.getSignal() - totalSignal - N0;
	}
	
	public double efficiency()
	{
		double r = 0.0;
		double omit = this.SINR;
		
		try
		{
			SINRToEfficiencyMap efflist = new SINRToEfficiencyMap();
			boolean state = false;
			double dis = Math.abs(omit - efflist.efficiencyList.get(0));
			
			for(int i = 2; i < efflist.efficiencyList.size(); i = i + 2)
			{
				if(dis > (omit - efflist.efficiencyList.get(i)))
					state = true;
				else if ((omit - efflist.efficiencyList.get(i)) == efflist.efficiencyList.get(i))
					return efflist.efficiencyList.get(i);
				else if (state == true)
					return efflist.efficiencyList.get(i-2);
				dis = Math.abs(omit - efflist.efficiencyList.get(i));
				//System.out.println("..."+String.valueOf(i));
			}
			return efflist.efficiencyList.get(efflist.efficiencyList.size()-1);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			System.out.println("IOException: SINR threshold table");
		}
		return -1;
	}
}