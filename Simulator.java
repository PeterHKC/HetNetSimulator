package Simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Simulator {

	int[] a = new int[30];
	int[] b = new int[30];
	ArrayList<BS> bsList = new ArrayList<BS>();
	ArrayList<RB> rbList = new ArrayList<RB>();
	ArrayList<UE> ueList = new ArrayList<UE>();
	public int UENumber;
	public int BSNumber;
	public int RBNumber = 12;
	
	Simulator(){}
	
	public void run()
	{
		this.initial("config1.csv");
		this.setBestCQI();
		
		for(RB rb : this.rbList)
		{
			for(UE ue : rb.getUEList())
			{
				ue.setRSSI(rb.RSSI);
			}
		}
		
		for(UE ue : this.ueList)
		{
			ue.print();
		}
	}
	public void setBestCQI()
	{
		for(UE ue : this.ueList)
		{
			double minDistance = 100000;
			int temp = 0;
			for(int i = 0; i < this.bsList.size(); i++)
			{
				if(Node.getDistance(ue, this.bsList.get(i)) < minDistance)
				{
					temp = i;
					minDistance = Node.getDistance(ue, this.bsList.get(i));
				}
			}
			
			ue.addBS(this.bsList.get(temp));
		}
		
		for(UE ue : this.ueList)
		{
			double minRSSI = 10000;
			int temp = 0;
			for(int i = 0; i < this.rbList.size(); i++)
			{
				if(this.rbList.get(i).RSSI.getdB() == 0)
				{
					this.rbList.get(i).addUE(ue);
					break;
				}
				else if(this.rbList.get(i).RSSI.getdB() < minRSSI)
				{
					temp = i;
					minRSSI = this.rbList.get(i).RSSI.getdB();
				}
			}
			this.rbList.get(temp).addUE(ue);
		}
	}
	
	public void sampleRun()
	{
		/*
		 * create BS
		 * arg1: transmit power (in dB)
		 * arg2: antenna gain (in dB)
		 * arg3: path loss parameter (p1)
		 * arg4: path loss parameter (p2)
		 * note: path loss model:  p1 + p2 * log10(d) where d is the distance between UE and BS
		 */
		BS bs = new BS(35.2,14,128.7,37.6);
		bs.setXY(0, 0);
		
		/*
		 * create UE
		 * 1.construct
		 * 2. set X, Y position
		 * 3. select BS
		 */
		UE ue1 = new UE();
		UE ue2 = new UE();
		ue1.setXY(100, 100);
		ue2.setXY(13, 100);
		ue1.addBS(bs);
		ue2.addBS(bs);
		
		/*
		 * create RB
		 * 1.select UE to be assigned
		 */
		RB rb = new RB();
		rb.addUE(ue1);
		rb.addUE(ue2);
		
		/*
		 * setting RB
		 * note: you must set RSSI for each UE before calculate UE's throughput and so on
		 */
		ue1.setRSSI(rb.RSSI);
		ue1.print();
		ue2.setRSSI(rb.RSSI);
		ue2.print();
	}
	
	public void initial(String filename)
	{
		String line = "";
		try
		{
			this.UENumber=0;
			this.BSNumber=0;
			this.RBNumber=12;
			FileReader fr = new FileReader(filename);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
			{
				line = br.readLine();
				if(line.contains("macro"))
				{
					String[] str = line.split(",");
					BS bs = new BS(35.2, 14, 128.1, 35.2);
					bs.setXY(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
					bs.name = str[0];
					this.bsList.add(bs);
					this.BSNumber++;
				}
				else if(line.contains("pico"))
				{
					String[] str = line.split(",");
					BS bs = new BS(24.2, 5, 140.7, 36.7);
					bs.setXY(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
					bs.setName(str[0]);
					this.bsList.add(bs);
					this.BSNumber++;
				}
				else if(line.contains("ue"))
				{
					String[] str = line.split(",");
					UE ue = new UE();
					ue.setName(str[0]);
					ue.setXY(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
					this.ueList.add(ue);
					this.UENumber++;
				}
			}
			
			for(int i  = 0; i < RBNumber; i++)
			{
				RB rb = new RB();
				this.rbList.add(rb);
			}
			
		}
		catch (Exception ex)
		{
			System.out.println("IOException: in "+filename);
		}
		
	}
	
	public static void main(String[] args)
	{
		Simulator si = new Simulator();
		si.run();
	}
}
