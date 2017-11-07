package Simulator;

import java.util.ArrayList;

public class RB 
{
	/*
	 * RSSI represents the total received wide-band power by UE
	 */
	dB RSSI = new dB(0);
	private ArrayList<UE> ueList = new ArrayList<UE>();
	private ArrayList<BS> bsList = new ArrayList<BS>();
	RB(){}
	
	public void addUE(UE ue)
	{
		this.RSSI.add(ue.getRSRP());
		this.ueList.add(ue);
		this.bsList.add(ue.getBS());
	}
	
	public ArrayList<BS> getBSList() {return this.bsList;}
	public ArrayList<UE> getUEList() {return this.ueList;}
	public void reset() {this.ueList.clear();this.bsList.clear();}
}
