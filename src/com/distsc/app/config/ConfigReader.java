package com.distsc.app.config;

import com.distsc.persistence.Persistor;
import com.distsc.util.PropertyFileParser;

public class ConfigReader
{
	@SuppressWarnings("finally")
	public static boolean setUp(String propertyFilePath)
	{
		boolean readSuccess = false;
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
			GlobalConfiguration.setNoDeclareCandidacyThreads(Integer.parseInt(propertFileParser.getValue("NoDeclareCandidacyThreads")));
			GlobalConfiguration.setNoRequestVoteResultListenerThreads(Integer.parseInt(propertFileParser.getValue("NoRequestVoteResultListenerThreads")));
			GlobalConfiguration.setNoRequestVoteResultListenerThreads(Integer.parseInt(propertFileParser.getValue("NoRequestVoteResultListenerThreads")));
			GlobalConfiguration.setNoLogAppendListener(Integer.parseInt(propertFileParser.getValue("NoLogAppendListener")));
			GlobalConfiguration.setNoLogAppendResultListener(Integer.parseInt(propertFileParser.getValue("NoLogAppendResultListener")));
			GlobalConfiguration.setNoHeartbeatSenderThread(Integer.parseInt(propertFileParser.getValue("NoHeartbeatSenderThread")));
			readSuccess = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return readSuccess;
		}
	}
}
