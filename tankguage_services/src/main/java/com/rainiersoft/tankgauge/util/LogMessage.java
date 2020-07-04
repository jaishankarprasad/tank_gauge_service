package com.rainiersoft.tankgauge.util;

public class LogMessage
{
	static StringBuffer console = new StringBuffer ();
	
	private static LogMessage log;
	
	private LogMessage ()
	{
		
	}
	
	public static LogMessage getInstance ()
	{
		   if(log == null)
		   {
			   log = new LogMessage();			   
		   }
		   
		   return log;
	}
				
	public static void message (String msg)
	{
		java.util.Date dt = new java.util.Date ();
		console.append(dt.toString());
		console.append(":");
		console.append(msg);
		console.append("\n");
	}

	public static StringBuffer getMessage ()
	{
		return console;
	}
	
	
}
