@echo Off
cd E:\Document\½×¤å\Simulation\my

FOR %%f IN (*.java) DO javac -d . %%f

java mySimulator.Simulator

@pause