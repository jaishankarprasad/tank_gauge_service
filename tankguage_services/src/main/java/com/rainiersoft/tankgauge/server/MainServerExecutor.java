package com.rainiersoft.tankgauge.server;

public class MainServerExecutor 
{

	public static void main(String[] args)
	{
		MainServerExecutor mainServerExecutor = new MainServerExecutor();

		PollRegisterThread pollRegisterThread = new PollRegisterThread();

		RawDataProcessorThread rawDataProcessorThread = new RawDataProcessorThread();

		pollRegisterThread.start();

		rawDataProcessorThread.start();
	}

}

