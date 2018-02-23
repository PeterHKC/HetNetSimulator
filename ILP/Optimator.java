package ILP;

import java.io.IOException;
import java.util.Arrays;

import com.anselm.plm.utilobj.LogIt;

import Jama.LUDecomposition;
import Jama.Matrix;
import Simulator.HetNet;
import Simulator.Node;
import Simulator.Util;
import lpsolve.LpSolve;
import lpsolve.LpSolveException;

public class Optimator {
	private static LpSolve problem;
	/**
	 * 求解整数规划问题
	 * @param goal			目标函数矩阵，由于LpSolve读取数组时从下标1开始读取，数据需从下标1开始填充，0-1的放前面，有上限的放后面
	 * @param stIeMatrix	不等式约束方程矩阵，由于LpSolve读取数组时从下标1开始读取，内层数据需从下标1开始填充
	 * @param stEqMatrix	等式约束方程矩阵，由于LpSolve读取数组时从下标1开始读取，内层数据需从下标1开始填充
	 * @param stIeRest		不等式约束条件矩阵，每次传入的是单个数字，不需要从1开始填充
	 * @param stEqRest		等式约束条件矩阵，每次传入的是单个数字，不需要从1开始填充
	 * @param ups			上限约束矩阵
	 */
	public static void optimate(double[] goal,double[][] stIeMatrix,double[][] stEqMatrix,
			double[] stIeRest,double[] stEqRest,double[] ups) throws LpSolveException{
		
		
		//1、创建LpSolve对象
		problem = LpSolve.makeLp(0, goal.length-1);
		//2、添加目标函数，会从下标1开始读取!下标1的参数会被忽略
		problem.setObjFn(goal);
		
		//3、循环添加不等式约束，外层循环一次代表一个不等式
		if(stIeMatrix!=null){
			for(int i=0;i<stIeMatrix.length;i++){
				//同样数组的读取会从下标1开始
				problem.addConstraint(stIeMatrix[i], LpSolve.GE, stIeRest[i]);
			}
		}
		
		//4、循环添加等式约束，外层循环一次代表一个等式
		if(stEqMatrix!=null){
			for(int i=0;i<stEqMatrix.length;i++){
				//同样数组的读取会从下标1开始
				problem.addConstraint(stEqMatrix[i], LpSolve.EQ, stEqRest[i]);
			}
		}
		
		//5、设置参数的整数约束，1代表第一个参数
		for(int i=1;i<goal.length;i++){
			problem.setInt(i, true);
		}
		
		//6、设置指定参数的上限值
		for(int i=1;i<=ups.length;i++){
			problem.setLowbo(i, ups[i-1]);
		}
		
		problem.printLp();
		//求解
		problem.solve();
	}
	/**
	 * 得到最优解
	 * @return
	 * @throws LpSolveException
	 */
	public static double getObjective() throws LpSolveException{
		if(problem!=null){
			return problem.getWorkingObjective();
		}else{
			try {
				throw new Exception("还没有进行求解！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return 0;
		}
	}
	/**
	 * 得到最优解对应的变量
	 * @return
	 * @throws LpSolveException
	 */
	public static double[] getVariables() throws LpSolveException{
		if(problem!=null){
			return problem.getPtrVariables();
		}else{
			try {
				throw new Exception("还没有进行求解！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return null;
		}
	}
	
	public static int[] userAssociation(HetNet net) throws LpSolveException
	{
		int UENumber = net.ueList.size();
		int BSNumber = net.bsList.size();
		
		double[][] s = new double[UENumber][BSNumber];
		double[] obj = new double[BSNumber*UENumber+1];
		double[][] stIeMatrix = new double[40][];
		double[] usr = new double[BSNumber*UENumber];
		
		// do user association
		int k = 1;
		obj[0] = 0;
		for(int i = 0; i < UENumber; i++)
			for(int j = 0; j < BSNumber; j++)
			{
					s[i][j] = net.ueList.get(i).pingBS(net.bsList.get(j)); //double
					obj[k] = s[i][j];
					k++;
			}
		
//		double[][] stIeMatrix = new double[1][];
//		stIeMatrix[0] = new double[]{0,1,2,3,4};
//		double[] stRest = new double[]{1};
//		optimate(new double[]{0,1,2,3,4},stIeMatrix,stIeMatrix,stRest,stRest,new double[]{1,1,5,6});
		
		
		stIeMatrix[0] = new double[obj.length];
		stIeMatrix[0][1] = 0;
		
		for(int i = 1; i < obj.length; i++)
		{
			stIeMatrix[0][i] = 1;
		}
		

		k = 0;
		for(int j = 1; j <= UENumber; j++)
		{
			k += 9;
			stIeMatrix[j] = new double[obj.length];
			stIeMatrix[j][1] = 0;
			for(int i = 1; i < stIeMatrix[j].length; i++)
			{
				if(i <= k && i > k-9)
					stIeMatrix[j][i] = 1;
				else
					stIeMatrix[j][i] = 0;
			}
		}
		
		
		
		for(int j = 31; j < BSNumber+31; j++)
		{
			stIeMatrix[j] = new double[obj.length];
			stIeMatrix[j][1] = 0;
			for(int i = 1; i < stIeMatrix[j].length; i++)
			{
				if(i % 9 == (j-31))
					stIeMatrix[j][i] = 1;
				else
					stIeMatrix[j][i] = 0;
			}
		}
		
		//stIeMatrix[1] = new double[] {0,1,-2};
		//stIeMatrix[2] = new double[] {0,1,5};
		double[] stRest = new double[]{30,1,12};
		
		LpSolve pro = LpSolve.makeLp(0, obj.length-1);
		
		pro.setObjFn(obj);
		pro.setMaxim();
		pro.addConstraint(stIeMatrix[0], LpSolve.EQ, stRest[0]);
		
		for(int j = 1; j <= UENumber; j++)
			pro.addConstraint(stIeMatrix[j], LpSolve.EQ, stRest[1]);
		for(int j = 31; j < BSNumber+31; j++)
			pro.addConstraint(stIeMatrix[j], LpSolve.LE, stRest[2]);
		
		//pro.addConstraint(stIeMatrix[1], LpSolve.LE, stRest[1]);
		//pro.addConstraint(stIeMatrix[2], LpSolve.LE, stRest[2]);
		for(int i = 0; i < obj.length-1; i++)
			pro.setBinary(i+1,true);
		
		
		pro.solve();
		
		//pro.printLp();
		usr = pro.getPtrVariables();
		return array2UserAssociation(usr);
	}
	public static double[][] RBAllocation(HetNet net) throws Exception
	{
		
		int UENumber = net.ueList.size();
		int RBNumber = net.rbList.size();
		
		int[] x = userAssociation(net);
		
		// do RB allocation
		double[] rba = new double[UENumber*UENumber];
		double[] link = new double[UENumber];
		double[] goal = new double[UENumber*UENumber+1];
		double[][] st = new double[466][UENumber*UENumber+1];
		
		for(int i = 0; i < UENumber; i++)
		{
			link[i] = net.ueList.get(i).pingBS(net.bsList.get(x[i]));
		}
		
		goal[0] = 0;
		for(int i = 1; i < goal.length; i++)
		{
			if(i % 30 == 1)
				System.out.println();
			goal[i] = link[(i-1)%UENumber]/link[(i-1)/UENumber];
			System.out.print(String.format("%2.2f\t", goal[i]));
		}
		System.out.println();
		
		for(int i = 0; i < st.length; i++)
		{
			st[i][0] = 0;
			for(int j = 1; j < UENumber*UENumber; j++)
				st[i][j] = 0;
		}
		
		for(int i = 0; i < UENumber; i++)
		{
			st[i][(UENumber+1)*i+1] = 1;
		}
		
		int row = 1, count = 30, offset=29;
		for(int i = 2; i <= 900; i++)
		{
			if(row < i%30 || i%30 == 0)
			{
				st[count][i] = 1;
				st[count][i+offset] = -1;
				//System.out.println(String.format("row=%2d: %3d=1, %3d=-1",row, i, i+offset));
				count++;
				offset+=29;
			}
			else
			{
				offset = 29;
				i+=row;
				row++;
			}
		}
		System.out.println(String.format("count=%d", count));
		st[465][0] = 0;
		for(int i = 1; i <= 900; i++)
			st[465][i] = 1;
		
		
		for(int nonzeros = 30; nonzeros <= 900 ; nonzeros+=2)
		{
			LpSolve rbp = LpSolve.makeLp(0, goal.length-1);
			rbp.setObjFn(goal);
			for(int i = 0; i < st.length; i++)
			{
				if(i < UENumber)
					rbp.addConstraint(st[i], LpSolve.EQ, 1);
				else if(i >= UENumber && i <= 464)
				{
					rbp.addConstraint(st[i], LpSolve.EQ, 0);
				}
				else if(i == 465)
				{
					rbp.addConstraint(st[i], LpSolve.GE, nonzeros);
				}
			}
			
			for(int i = 0; i < goal.length-1; i++)
				rbp.setBinary(i+1,true);
			
			rbp.solve();
			rbp.writeLp("dump");
			//rbp.printLp();
			
			rba = rbp.getPtrVariables();
			//printArray(rba,30);
			
			
			// run HetNet
			
			
			//calculate rank
			double[][] mat = new double[UENumber][UENumber];
			count = 0;
			for(int i = 0; i < UENumber; i++)
				for(int j = 0; j < UENumber; j++, count++)
					mat[i][j] = rba[count];
			Matrix A = new Matrix(mat);
			System.out.println(A.rank());
			if(A.rank() == RBNumber)
			{
				LUDecomposition lu = A.lu();
				if(lu.getU().rank() == RBNumber)
				{
					double[][] d = lu.getU().getArray();
					dumpArray(rba, 30);
					return d;
				}
			}
		}
		return null;
	}
	/*
	 * 測試
	 */
	/**
	 * 求解整数规划问题
	 * @param goal			目标函数矩阵，由于LpSolve读取数组时从下标1开始读取，数据需从下标1开始填充，0-1的放前面，有上限的放后面
	 * @param stIeMatrix	不等式约束方程矩阵，由于LpSolve读取数组时从下标1开始读取，内层数据需从下标1开始填充
	 * @param stEqMatrix	等式约束方程矩阵，由于LpSolve读取数组时从下标1开始读取，内层数据需从下标1开始填充
	 * @param stIeRest		不等式约束条件矩阵，每次传入的是单个数字，不需要从1开始填充
	 * @param stEqRest		等式约束条件矩阵，每次传入的是单个数字，不需要从1开始填充
	 * @param ups			上限约束矩阵
	 */
	public static void main(String[] args) {
		try {
			// create HetNet 
			HetNet net = new HetNet("config1.csv", 30);
			
			int [] y = {0,1,2,2,3,4,5,0,1,1,0,1,6,7,4,0,6,7,8,2,2,1,9,10,1,5,2,7,4,11};
			int [] x = userAssociation(net);
			net.userAssociation(x);
			LogIt log = new LogIt();
			log.setLogFile("userAssociation.csv");
			StringBuilder str = new StringBuilder();
			
			for(int i = 0 ; i < x.length; i++)
				str.append(String.format("%d,", x[i]));
			log.log(str);
			//double [][] d = RBAllocation(net);
			
			net.RBAllocation(y);
			//net.autoRBAllocation();
			System.out.print("throughput: ");
			System.out.println(net.getTotalThroughput());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean printArray(double[][] obj)
	{
		boolean ret = true;
		for(int i = 0; i < obj.length; i++)
		{
			for(int j = 0; j < obj[i].length; j++)
			{
				System.out.print(String.format("%2.2f\t", obj[i][j]));
				if(obj[i][j] < 0 || obj[i][j] > 1)
					ret = false;
			}
			System.out.println("");
		}
		return ret;
	}
	public static void printArray(double[] obj)
	{
		for(int i = 0; i < obj.length; i++)
		{
			System.out.print(String.format("%2.2f\t", obj[i]));
			if(i % 9 == 8)
				System.out.println("");
		}
	}
	public static void printArray(double[] obj, int n)
	{
		for(int i = 0; i < obj.length; i++)
		{
			System.out.print(String.format("%2.2f\t", obj[i]));
			if(i % n == n-1)
				System.out.println("");
		}
	}
	public static void dumpArray(double[] obj, int n) throws IOException
	{
		LogIt log = new LogIt();
		log.setLogFile("m_matrix");
		StringBuilder str = new StringBuilder("");
		str.append("m=[");
		for(int i = 0; i < obj.length; i++)
		{
			str.append(String.format("%2d\t", (int)obj[i]));
			if(i % n == n-1)
				str.append(";");
		}
		str.append("]");
		log.log(str);
	}
	public static int[] array2UserAssociation(double[] obj)
	{
		int[] x = new int[30];
		int j = 0;
		for(int i = 0; i < obj.length; i++)
		{
			if(obj[i] == 1 )
			{
				x[j] = (int) i%9;
				j++;
			}
		}
		return x;
	}
	
	public static int[] array2RBAllocation(double[] obj)
	{
		int[] x = new int[30];
		int j = 0;
		for(int i = 0; i < obj.length; i++)
		{
			if(obj[i] == 1 )
			{
				x[j] = (int) i%9;
				j++;
			}
		}
		return x;
	}
}