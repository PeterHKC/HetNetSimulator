package Simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.anselm.plm.utilobj.LogIt;

import GA.Chromosome;
import GA.GA;

public class HetNet
{
	static LogIt log = new LogIt();
	
	public ArrayList<BS> bsList = new ArrayList<BS>();
	public ArrayList<RB> rbList = new ArrayList<RB>();
	public ArrayList<UE> ueList = new ArrayList<UE>();
	public int UENumber;
	public int BSNumber;
	public int RBNumber = 12;
	private int[] a,b;
	
	HetNet() throws Exception{}
	HetNet(int n)throws Exception{this.initial("config1.csv", n);}
	public HetNet(String filename, int n)throws Exception{this.initial(filename, n);}
	
	public double getTotalThroughput()
	{
		double th = 0;
		//this.BestCQI();
		for(RB rb : this.rbList)
		{
			for(UE ue : rb.getUEList())
			{
				ue.setRSSI(rb);
				//System.out.println(ue.getRSSI().getdB());
				ue.calDataRate(rb.RSSI);
			}
		}
		for(UE ue : this.ueList)
		{
			th+=ue.getDataRate();
			//ue.print();
			//System.out.println(ue.getDataRate());
		}
		/*
		if(this.isValid() == false)
			return 0;
			*/
		return th;
	}
	
	public void print()
	{
		System.out.print("UE number: ");
		System.out.println(this.UENumber);
		System.out.print("BS number: ");
		System.out.println(this.BSNumber);
		System.out.print("RB number: ");
		System.out.println(this.RBNumber);
		System.out.print("system total throughput: ");
		System.out.println(this.getTotalThroughput());
		for(UE ue:this.ueList)
			ue.print();
	}
	
	public void reset()
	{
		for(BS bs:this.bsList)
			bs.reset();
		for(UE ue:this.ueList)
			ue.reset();
		for(RB rb:this.rbList)
			rb.reset();
	}
	
	public boolean userAssociation(int x[])
	{
		this.UENumber = x.length;
		this.a=new int[x.length];
		
		for(int i = 0; i < x.length; i++)
		{
			this.a[i]=x[i];
			this.ueList.get(i).addBS(this.bsList.get(x[i]));
			if(this.bsList.get(x[i]).getService() > 12) return false;
		}
		return true;
	}
	
	public boolean RBAllocation(int x[])
	{
		this.UENumber = x.length;
		this.b=new int[x.length];
		for(int i = 0; i < x.length; i++)
		{
			this.b[i]=x[i];
			boolean res = this.rbList.get(x[i]).addUE(this.ueList.get(i));
			if(res == false)
				return false;
		}
		return true;
	}
	
	public void autoRBAllocation()
	{
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
				else if(this.rbList.get(i).RSSI.getdB() < minRSSI && this.rbList.get(i).getBSList().contains(ue.getBS()) == false)
				{
					temp = i;
					minRSSI = this.rbList.get(i).RSSI.getdB();
				}
			}
			this.rbList.get(temp).addUE(ue);
			//ue.setRSSI(this.rbList.get(temp).RSSI);
		}
	}
	
	public void BestCQI()
	{
		for(UE ue : this.ueList)
		{
			double maxRSRP = 0;
			int temp = 0;
			for(int i = 0; i < this.bsList.size(); i++)
			{
				if(ue.pingBS(this.bsList.get(i)) > maxRSRP && this.bsList.get(i).getService() <= 12)
				{
					temp = i;
					maxRSRP = ue.pingBS(this.bsList.get(i));
				}
			}
			ue.addBS(this.bsList.get(temp));
			this.bsList.get(temp).addService();
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
				else if(this.rbList.get(i).RSSI.getdB() < minRSSI && this.rbList.get(i).getBSList().contains(ue.getBS()) == false)
				{
					temp = i;
					minRSSI = this.rbList.get(i).RSSI.getdB();
				}
			}
			this.rbList.get(temp).addUE(ue);
			//ue.setRSSI(this.rbList.get(temp).RSSI);
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
		ue1.setRSSI(rb);
		ue1.print();
		ue2.setRSSI(rb);
		ue2.print();
	}
	
	public void initial(String filename, int n)
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
			while(this.UENumber < n)
			{
				UE ue = new UE();
				ue.setName(String.valueOf(this.UENumber++));
				ue.setXY(Math.random()*1200-600, Math.random()*1200-600);
				this.ueList.add(ue);
			}
			for(int i  = 0; i < RBNumber; i++)
			{
				RB rb = new RB();
				rb.name = "RB"+String.valueOf(i);
				this.rbList.add(rb);
			}
			
		}
		catch (Exception ex)
		{
			System.out.println("IOException: in "+filename);
		}
		
	}
	
	//not use
	private boolean isValid()
	{
		int[] bs_service = new int[9];
		for(int i : bs_service) i=0;
		for(int a:this.a)
		{
			bs_service[a]++;
			if(bs_service[a]>12) return false;
		}
		for(int i = 0; i < 30; i++)
		{
			for(int j = 0; j < 30; j++)
			{
				if(i != j && this.a[i] == this.a[j] && this.b[j] == this.b[i])
				{
					return false;
				}
			}
		}
		return true;
	}
	/*
	 * arg1: configure file name
	 * arg2: random UE number
	 */
	public static void run(String filename, int n) throws Exception
	{
		int iter = 1;
		double th = 0;
		for(int i = 0; i < iter; i++)
		{
			HetNet si = new HetNet(filename, n);
			si.BestCQI();
			th+=si.getTotalThroughput();
		}
		log.log(th/iter);
	}
	public static void main(String[] args) throws Exception
	{
		String conf1 = "config1.csv";
		String conf2 = "config2.csv";
		
//		HetNet si = new HetNet(conf2, 1);
//		si.BestCQI();
//		//si.print();
//		log.log(si.getTotalThroughput());
		
		log.setLogFile("result1.csv");
		log.log(conf1);
		run(conf1, 30);
		
//		for(int i = 30; i <= 100; i++)
//		{
//			run(conf1, i);
//		}
//		log.log(conf2);
//		for(int i = 30; i <= 100; i++)
//		{
//			run(conf2, i);
//		}
//		log.close();
	}
}
