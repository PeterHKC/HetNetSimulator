package Simulator;

public class BS extends Node{
	
	private int RBNumber = 12;
	private dB transmit_power = null;
	private dB antenna_gain = null;
	private double path_loss_para1;
	private double path_loss_para2;
	private int service = 0;
	BS(){}
	/*
	 * input transmit_power and antenna_gain
	 */
	BS(double tp, double ag, double p1, double p2)
	{
		this.transmit_power = new dB(tp);
		this.antenna_gain = new dB(ag);
		path_loss_para1 = p1;
		path_loss_para2 = p2;
	}
	public boolean addService() {this.service++; if(this.service>this.RBNumber) return false; return true;}
	public int getService() {return this.service;}
	public dB getTransmitPower() {return this.transmit_power;}
	public dB getAntennaGain() {return this.antenna_gain;}
	public double pathLossModel(double distance) {return this.path_loss_para1 + this.path_loss_para2*Math.log10(distance);}
	public void reset() {this.service = 0;}
}
