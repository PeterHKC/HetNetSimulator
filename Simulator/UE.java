package Simulator;

public class UE extends Node{
	
	private double dataRate;
	private double distance;
	/*
	 * RSRP is the linear average of the downlink reference signals across the channel bandwidth 
	 */
	private dB RSRP = null, RSSI = null, RSRQ = null;
	private BS bs = null;
	private RB rb = null;
	UE(){}
	public double pingBS(BS bs)
	{
		if(bs.getService() == 12)
			return 0;
		double dis = Node.getDistance(this, bs);
		double pathloss = bs.pathLossModel(dis);
		dB temp = bs.getAntennaGain().mul(bs.getAntennaGain(), bs.getTransmitPower());
		dB RSRP = temp.div(temp, pathloss);
		return RSRP.getWatt();
	}
	public void addBS(BS bs)
	{
		this.distance = Node.getDistance(this, bs);
		double pathloss = bs.pathLossModel(this.distance);
		dB temp = bs.getAntennaGain().mul(bs.getAntennaGain(), bs.getTransmitPower());
		this.RSRP = temp.div(temp, pathloss);
		bs.addService();
		this.bs = bs;
	}
	
	public RB getRB() {return this.rb;}
	public dB getRSRP() {return this.RSRP;}
	public dB getRSSI() {return this.RSSI;}
	public dB getRSRQ() {return this.RSRQ;}
	public BS getBS() {return this.bs;}
	public void setRSSI(RB rb) {this.rb = rb; this.RSSI = rb.RSSI;this.RSSI.sub(this.RSRP);}
	public void calDataRate(dB RSSI)
	{
		this.RSSI = RSSI;
		this.RSRQ = RSSI.div(this.RSRP,RSSI);
		this.dataRate = (Util.efficiency(this.RSRQ)*Util.SC*Util.SY)/Util.T/1024;
	}
	public double getDataRate()
	{
		return this.dataRate;
	}
	public void print()
	{
		for(int i = 0; i < 30; i++)
			System.out.print("-");
		System.out.print("UE: ");
		System.out.println(this.name);
		System.out.print("data rate (Kbps): ");
		System.out.println(this.getDataRate());
		System.out.print("bs to ue (meter): ");
		System.out.println(this.distance);
		System.out.print("RSSI (dB): ");
		System.out.println(this.RSSI.getdB());
		System.out.print("RSRP (dB): ");
		System.out.println(this.RSRP.getdB());
		System.out.print("path loss (dB): ");
		System.out.println(this.bs.pathLossModel(this.distance));
		System.out.print("SINR (dB): ");
		System.out.println(this.RSRQ.getdB());
		System.out.print("efficiency (bits/symbol): ");
		System.out.println(Util.efficiency(this.RSRQ));
		System.out.print("assgined by BS: ");
		System.out.println(this.bs.name);
		System.out.print("assgined by RB: ");
		System.out.println(this.rb.name);
		for(int i = 0; i < 50; i++)
			System.out.print("-");
		System.out.println("-");
	}
	public void reset() {this.bs=null;}
}
