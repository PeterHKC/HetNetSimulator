package Simulator;

public class dB {
	private double watt;
	private double intensity;
	dB(){}
	dB(double d) {this.watt = this.dB2Watt(d); this.intensity = d;}
	
	public void setWatt(double watt) {this.intensity = this.watt2dB(watt); this.watt = watt;}
	public void setdB(double d) {this.intensity=d; this.watt=this.dB2Watt(d);}
	public double getdB() {return this.intensity;}
	public double getWatt() {return this.watt;}
	
	public void add(dB d)
	{
		this.watt = this.watt + d.watt;
		this.intensity = this.watt2dB(this.watt);
	}
	public dB add(dB d1, dB d2)
	{
		double sumInWatt = this.dB2Watt(d1) + this.dB2Watt(d2);
		return new dB(this.watt2dB(sumInWatt));
	}
	public dB add(dB d1, double d2) 
	{
		double sumInWatt = this.dB2Watt(d1) + d2;
		return new dB(this.watt2dB(sumInWatt));
	}
	public void sub(dB d)
	{
		this.watt = this.watt - d.watt;
		this.intensity = this.watt2dB(this.watt);
	}
	public dB sub(dB d1, dB d2)
	{
		double sumInWatt = this.dB2Watt(d1) - this.dB2Watt(d2);
		return new dB(this.watt2dB(sumInWatt));
	}
	public dB sub(dB d1, double d2) 
	{
		double sumInWatt = this.dB2Watt(d1) - d2;
		return new dB(this.watt2dB(sumInWatt));
	}
	
	public dB mul(dB d1, dB d2) {dB temp = new dB(d1.intensity+d2.intensity); return temp;}
	public dB div(dB d1, dB d2) {dB temp = new dB(d1.intensity-d2.intensity); return temp;}
	public dB mul(dB d1, double d2)
	{
		double resInWatt = d1.getWatt()*d2;
		
		return new dB(this.watt2dB(resInWatt));
	}
	public dB div(dB d1, double d2) 
	{
		double resInWatt = d1.getWatt()/d2;
		
		return new dB(this.watt2dB(resInWatt));
	}
	public void mul(double d)
	{
		this.watt = this.getWatt()*d;
		this.intensity = this.watt2dB(this.watt);
	}
	public void div(double d) 
	{
		this.watt = this.getWatt()/d;
		this.intensity = this.watt2dB(this.watt);
	}
	
	public double dB2Watt(dB d) {return Math.pow(10, d.intensity/10);}
	public double dB2Watt(double d) {return Math.pow(10, d/10);}
	public double watt2dB(double watt) {return 10*Math.log10(watt);}
	
	//test function
	public static void main(String[] args)
	{
		dB d1 = new dB(46);
		dB d2 = new dB(14);
		d1.add(d2);
		dB d4 = d1.div(d1, 12);
		System.out.println(d1.getdB());//ans=14
		System.out.println(d4.getdB());//ans=35.2
	}
}
