@echo Off
cd E:\Document\�פ�\Simulation\my

FOR %%f IN (*.java) DO javac -d . %%f

java mySimulator.Simulator

@pause