package Simulator;

import java.util.ArrayList;

public class RB 
{
	/*
	 * RSSI represents the total received wide-band power by UE
	 */
	dB RSSI = new dB(0);
	private ArrayList<UE> ueList = new ArrayList<UE>();
	
	RB(){}
	
	public void addUE(UE ue)
	{
		this.RSSI.add(ue.getRSRP());
		this.ueList.add(ue);
	}
	
	public ArrayList<UE> getUEList()
	{
		return this.ueList;
	}
}
