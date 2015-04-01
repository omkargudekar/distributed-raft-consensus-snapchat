package com.distributedsnapchat.app;

public class GlobalConfiguration
{
	private static int clinetListenerPort=4000;

	public static int getClinetListenerPort()
	{
		return clinetListenerPort;
	}

	public static void setClinetListenerPort(int clinetListenerPort)
	{
		GlobalConfiguration.clinetListenerPort = clinetListenerPort;
	}
}
