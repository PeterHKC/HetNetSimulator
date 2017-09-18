@echo On

cd D:\Document\GitHub\HetNetSimulator
D:
FOR %%f IN (*.java) DO javac -d . %%f

java mySimulator.Simulator

@pause