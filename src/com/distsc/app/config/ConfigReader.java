package com.distsc.app.config;

import com.distsc.persistence.Persistor;
import com.distsc.util.PropertyFileParser;

public class ConfigReader
{
	public static void setUp(String propertyFilePath)
	{
		try
		{
			PropertyFileParser propertFileParser = new PropertyFileParser(propertyFilePath);
			ClusterConfigReader.readAndSetUp(propertFileParser.getValue("ClusterConfigurationFile"));
			Persistor.setUp(propertFileParser.getValue("PersistanceFileLocation"));
			GlobalConfiguration.setMessageLimit(Integer.parseInt(propertFileParser.getValue("MessageSizeLimit")));
			GlobalConfiguration.setClientMessageStorageDir(propertFileParser.getValue("ClientMessageDir"));
			GlobalConfiguration.setNoRequestWorkerThreads(Integer.parseInt(propertFileParser.getValue("NoOfRequestWorkerThread")));
			GlobalConfiguration.setNoNodeDiscoverWorkerThreads(Integer.parseInt(propertFileParser.getValue("NoOfNodeDiscoveryWorkerThread")));
			GlobalConfiguration.setNoClientMsgWorkerThreads(Integer.parseInt(propertFileParser.getValue("NoClientMsgWorkerThreads")));
			GlobalConfiguration.setNoRequestVoteResultListenerThreads(Integer.parseInt(propertFileParser.getValue("NoRequestVoteResultListenerThreads")));
			GlobalConfiguration.setNoRequestVoteResultListenerThreads(Integer.parseInt(propertFileParser.getValue("NoRequestVoteResultListenerThreads")));
			GlobalConfiguration.setNoLogAppendListener(Integer.parseInt(propertFileParser.getValue("NoLogAppendListener")));
			GlobalConfiguration.setNoLogAppendResultListener(Integer.parseInt(propertFileParser.getValue("NoLogAppendResultListener")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
	}
}
