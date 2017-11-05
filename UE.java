package Simulator;

public class UE extends Node{
	
	private double dataRate;
	private double distance;
	/*
	 * RSRP is the linear average of the downlink reference signals across the channel bandwidth 
	 */
	private dB RSRP = null, RSSI = null, RSRQ = null;
	private BS bs = null;
	UE(){}
	public void addBS(BS bs)
	{
		this.distance = Node.getDistance(this, bs);
		double pathloss = bs.pathLossModel(this.distance);
		dB temp = bs.getAntennaGain().mul(bs.getAntennaGain(), bs.getTransmitPower());
		this.RSRP = temp.div(temp, pathloss);
		this.bs = bs;
	}
	
	public dB getRSRP() {return this.RSRP;}
	public dB getRSSI() {return this.RSSI;}
	public dB getRSRQ() {return this.RSRQ;}
	public BS getBS() {return this.bs;}
	public void setRSSI(dB RSSI) {this.RSSI = RSSI;this.RSSI.sub(this.RSRP);}
	public double getDataRate(dB RSSI)
	{
		this.RSSI = RSSI;
		this.RSRQ = RSSI.div(this.RSRP,RSSI);
		this.dataRate = (Util.efficiency(this.RSRQ)*Util.SC*Util.SY)/Util.T/1024;
		return this.dataRate;
	}
	
	public void print()
	{
		System.out.print("UE: ");
		System.out.println(this.name);
		System.out.print("data rate (Kbps): ");
		System.out.println(this.getDataRate(this.RSSI));
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
		System.out.print("BS assgined by: ");
		System.out.println(this.bs.name);
	}
}
