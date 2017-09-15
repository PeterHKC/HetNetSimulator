@echo Off

FOR %%f IN (*.java) DO javac -d . %%f

java mySimulator.Simulator

@pause