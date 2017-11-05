package mySimulator;
import java.util.*;
import java.io.*;

public class Simulator
{
	public final int UENumber = 2;
	public final int BSNumber = 2;
	public final int RBNumber = 1;
	ArrayList<BS> bsList = new ArrayList<BS>();
	ArrayList<RB> rbList = new ArrayList<RB>();
	ArrayList<UE> ueList = new ArrayList<UE>();
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	Simulator(){}
	
	public void run()
	{
		
		double x=50, y=50;
		//initialize BS, UE, RB
		for(int i  = 0; i < BSNumber; i++)
		{
			BS bs = new BS();
			if(i%2 == 1)
				bs.setXY(x*=-1, y);
			else
				bs.setXY(x, y*=-1);
			this.bsList.add(bs);
			//System.out.println(String.valueOf(x)+"\t"+String.valueOf(y));
		}
		
		for(int i  = 0;i < UENumber; i++)
		{
			UE ue = new UE(i);
			ue.setXY(Math.random()*500-250,Math.random()*500-250);
			//ue.setXY(45,0);
			this.ueList.add(ue);
		}
		
		for(int i  = 0; i < RBNumber; i++)
		{
			RB rb = new RB();
			this.rbList.add(rb);
		}
		
		String filename = "config1.csv";
		//this.initial(filename);
		//this.setBestCQI();
		
		//set user association. In other word, decide which bs will be assigned to which ue
		this.setUserAssociation();
		
		//set RB allocation, to manage which RB will be assigned to which connection 
		//this.setRBAllocation();
		
		//print every connection's data rate
		
		for(Connection c : connList)
		{
			c.ue.print();
			System.out.print("data rate Kbps: ");
			System.out.println(c.dataRate(rbList.get(0).getTotalSignal(c.ue)));
			System.out.print("bs to ue meter: ");
			System.out.println(c.getDistance());
			System.out.print("all signal dB: ");
			System.out.println(rbList.get(0).getTotalSignal(c.ue));
			System.out.print("signal dB: ");
			System.out.println(c.getSignal());
			System.out.print("path loss dB: ");
			System.out.println(c.pathLoss);
			System.out.print("sinr dB: ");
			System.out.println(c.SINR);
			System.out.print("efficiency bits/symbol: ");
			System.out.println(c.efficiency());
//			System.out.print("transmit power dBm: ");
//			System.out.println(c.ue.power);
		}
		
	}
	
	public void initial(String filename)
	{
		String line = "";
		try
		{
			FileReader fr = new FileReader(filename);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
			{
				line = br.readLine();
				if(line.contains("macro"))
				{
					String[] str = line.split(",");
					BS bs = new BS(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
					bs.name = str[0];
					this.bsList.add(bs);
				}
				else if(line.contains("pico"))
				{
					String[] str = line.split(",");
					BS bs = new BS(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
					bs.name = str[0];
					bs.transmitPower = 35;
					bs.antennaGain = 5;
					this.bsList.add(bs);
				}
				else if(line.contains("ue"))
				{
					String[] str = line.split(",");
					UE ue = new UE();
					ue.name = str[0];
					ue.setXY(Double.parseDouble(str[1]),Double.parseDouble(str[2]));
					this.ueList.add(ue);
				}
			}
			
			for(int i  = 0; i < UENumber; i++)
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
	public void setRBAllocation()
	{
		
	}
	public void setBestCQI()
	{
		for(UE ue : this.ueList)
		{
			Connection c = null;
			double minDist = 100000;
			for(BS bs : this.bsList)
			{
				Connection temp = new Connection(bs, ue);
				if(minDist > temp.getDistance())
				{
					c = temp;
					minDist = temp.getDistance();
				}
			}
			this.connList.add(c);
		}
		
		for(int i = 0; i < this.connList.size(); i++)
		{
			this.rbList.get(i).add(this.connList.get(i));
		}
		
		/*
		for(Connection conn : this.connList)
		{
			RB r = null;
			double minRSSI = 100000;
			for(RB rb : this.rbList)
			{
				if(minRSSI > rb.getTotalSignal(conn.ue))
				{
					r = rb;
					minRSSI = rb.getTotalSignal(conn.ue);
					if(minRSSI == 0)
						break;
				}
			}
			r.add(conn);
		}
		*/
	}
	
	public void setUserAssociation()
	{
		for(int i = 0; i < this.ueList.size(); i++)
		{
			Connection conn = new Connection(this.bsList.get(i), this.ueList.get(i));
			this.connList.add(conn);
		}
		for(int i = 0; i < this.connList.size(); i++)
			this.rbList.get(0).add(this.connList);
	}
	
	public void genData(String filename)
	{
		try
		{
			FileWriter fw = new FileWriter(filename);
			for(int i  = 0 ; i < 60; i++)
			{
				fw.write("ue,");
				fw.write(String.valueOf(Math.random()*680-340));
				fw.write(",");
				fw.write(String.valueOf(Math.random()*680-340));
				fw.write("\n");
			}
			fw.close();
		}
		catch(Exception ex)
		{
			System.out.println("IOException: in "+filename);
		}
	}
	
	public static void main(String[] arg) throws Exception
	{
		//System.out.println("fuck");
		Simulator si = new Simulator();
		si.run();
		//si.initial("config1.csv");
	}
}