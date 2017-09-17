package mySimulator;
import java.util.*;
import java.io.*;
import mySimulator.*;

public class SINRToEfficiencyMap
{
	public HashMap<Double,Double> efficiencyMap = new HashMap<Double,Double>();
	
	SINRToEfficiencyMap()
	{
		String threshold = "";
		String efficiency = "";
		String filename = "efficiency.txt";
		try
		{
			FileReader fr = new FileReader(filename);
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
			System.out.println("IOException: in "+filename);
		}
		
		String del = "[a-zA-Z ()/]+";
		String[] thresholdArray = threshold.split(del);
		String[] efficiencyArray = efficiency.split(del);
		
		for(int i = 1; i < thresholdArray.length; i++)
		{
			this.efficiencyMap.put(Double.parseDouble(thresholdArray[i]),Double.parseDouble(efficiencyArray[i]));
		}
			
	}
}