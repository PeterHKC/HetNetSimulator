@echo On

FOR %%f IN (*.java) DO javac -d . %%f

java mySimulator.Simulator
