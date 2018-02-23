package Simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import com.anselm.plm.utilobj.LogIt;

public class Util {
	/*
	 * the HetNet setting
	 */
	public static final double SC = 12;
	public static final double SY = 14;
	public static final double T = 0.001;
	public static final double N0 = 0;
	
	public static String tableName = "efficiency.txt";
	public static HashMap<Double,Double> efficiencyMap = new HashMap<Double,Double>();
	/*
	 * get sinr table
	 */
	public static void SINRToEfficiencyMap(String filename)
	{
		String threshold = "";
		String efficiency = "";
		//String filename = "efficiency.txt";
		try
		{
			FileReader fr = new FileReader(filename);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
			{
				//only two lines
				if(threshold == "")
					threshold = br.readLine();
				else
					efficiency = br.readLine();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//System.out.println("IOException: in "+filename);
		}
		
		String del = "[a-zA-Z ()/]+";
		String[] thresholdArray = threshold.split(del);
		String[] efficiencyArray = efficiency.split(del);
		
		for(int i = 1; i < thresholdArray.length; i++)
		{
			efficiencyMap.put(Double.parseDouble(thresholdArray[i]),Double.parseDouble(efficiencyArray[i]));
		}
	}
	
	public static double efficiency(dB sinr)
	{
		double r = 1000.0, res = 0.0;
		double omit = sinr.getdB();
		
		try
		{
			SINRToEfficiencyMap(tableName);
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
			ex.printStackTrace();
			//System.out.println("IOException: SINR threshold table");
			return -1;
		}
		
	}
	
	/*
	 * generate UE data
	 */
	public static void genUEData(String filename, int n)
	{
		try
		{
			LogIt log = new LogIt();
			log.setLogFile(filename);
			for(int i = 0; i < n; i++)
			{
				log.log("ue"+String.valueOf(i)+","
						+String.valueOf(Math.random()*1200-600)
						+","+String.valueOf(Math.random()*1200-600));
			}
			log.close();
		}
		catch(Exception ex)
		{
			System.out.println("IOException: in "+filename);
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		genUEData("ue.csv", 30);
	}
}
