
package com.rainiersoft.tankgauge.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.rainiersoft.tankgauge.util.PropertyLoader;

public class DBConnector
{
	final static Logger logger=Logger.getLogger(DBConnector.class);
	static String  hostname="103.92.235.45";
	//	static String  hostname="localhost";
	static String dbName="iocl";
	static String user="rainier";
	static String password="rainier123";
	static int port=3306;
	static Connection con = null;
	String dbString = null;
	static String propFile="tankgauge.properties";
	public static final String JDBCDRIVER = "com.mysql.jdbc.Driver";  
	public static final String  DBNAME="DBNAME";
	public static final String	DBUSER="DBUSER";
	public static final String	DBPASSWORD="DBPASSWORD";
	public static final String	DBPORT="DBPORT";
	public static final String	DBHOSTNAME="DBHOSTNAME";

	private static DBConnector dbCon;

	private DBConnector ()
	{
		//Avoid instantianting the instance of this DBConnector.
	}

	// Ensure always only single instance class.
	public static DBConnector getInstance ()
	{
		if(dbCon == null)			   
		{
			PropertyLoader prop = new PropertyLoader ();
			prop.loadProperties(propFile);
			dbName=prop.getValue(DBNAME);
			user=prop.getValue(DBUSER);
			password=prop.getValue(DBPASSWORD);
			hostname=prop.getValue(DBHOSTNAME);
			port=Integer.parseInt(prop.getValue(DBPORT));

			dbCon = new DBConnector();

			String dbString = "jdbc:mysql://"+hostname+":"+port+"/"+dbName;
			
			try
			{
				Class.forName(JDBCDRIVER);
				con = DriverManager.getConnection(dbString, user, password);
			}
			catch (SQLException e)
			{	 
				e.printStackTrace();
				logger.error(e);
			}
			catch (ClassNotFoundException e)
			{
				logger.error(e);
				e.printStackTrace();
			}
		}

		return dbCon;
	}



	public Connection getConnection ()
	{
		return con;
	}	

	public boolean isConnectionAlive()
	{
		try
		{
			if (con.isClosed())
				return false;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(e);
		}

		return true;
	}

	public Connection  reConnect()
	{

		if (isConnectionAlive())

			return con;

		//just ensure that connection is closed..... close all gambling connections
		try 
		{
			con.close();
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}

		String dbString = "jdbc:mysql://"+hostname+":"+port+"/"+dbName;

		try
		{
			Class.forName(JDBCDRIVER);
			con = DriverManager.getConnection(dbString, user, password);
		}
		catch (SQLException e)
		{	 
			e.printStackTrace();
			logger.error(e);
		}
		catch (ClassNotFoundException e)
		{
			logger.error(e);
			e.printStackTrace();
		}

		return con;
	}
	public void close()
	{
		try 
		{
			con.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.error(e);
		}
	}

}

