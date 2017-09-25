package mySimulator;
import java.util.*;

public class Connection
{
	public BS bs;
	public UE ue;
	public double dis;
	
	public double pathLoss;
	public double N0 = 0;
	public double SINR;
	
	public double SC = 12;
	public double SY = 14;
	public double T = 0.001;
	
	Connection(){}
	
	Connection(BS bs, UE ue)
	{
		this.bs = bs;
		this.ue = ue;
		this.pathLoss = this.pathLossModel(this.getDistance()/1000);
	}
	
	private double pathLossModel(double d)
	{
		// km
		return 128.7 + 37.6*Math.log10(d);
	}
	
	public double getDistance()
	{
		this.dis = Math.sqrt(Math.pow(this.bs.x-this.ue.x, 2)+Math.pow(this.bs.y-this.ue.y, 2));
		
		return this.dis;
	}
	
	public double getSignal()
	{
		return this.bs.antennaGain+this.bs.transmitPower-this.pathLoss;
		//return 10*Math.log10(Math.pow(10,(this.bs.transmitPower + this.bs.antennaGain)/10)-Math.pow(10,this.pathLoss/10));
	}
	
	public double dataRate(double totalSignal)
	{
		this.SINR = this.getSignal()-10*Math.log10(Math.pow(10,totalSignal/10)-Math.pow(10,this.getSignal()/10));
		
		return (this.efficiency()*this.SC*this.SY)/this.T/1024;
	}
	
	public double efficiency()
	{
		double r = 1000.0, res = 0.0;
		double omit = this.SINR;
		
		try
		{
			SINRToEfficiencyMap effMap = new SINRToEfficiencyMap();
			HashMap<Double,Double> efficiencyMap = effMap.efficiencyMap;
			for(double k : efficiencyMap.keySet())
			{
				if(omit > k && omit - k < r)
				{
					r = omit - k;
					res = k;
				}
				else if(omit <= -6.5)
				{
					return efficiencyMap.get(-6.5);
				}
				else if(omit >= 17.6)
				{
					return efficiencyMap.get(17.6);
				}
				else if(omit == k)
					return efficiencyMap.get(k);
			}
			
			return efficiencyMap.get(res);
			
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			System.out.println("IOException: SINR threshold table");
			return -1;
		}
		
	}
}