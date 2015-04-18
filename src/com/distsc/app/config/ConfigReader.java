package com.distsc.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.distsc.persistence.Persistor;
import com.distsc.util.PropertyFileParser;

public class ConfigReader
{
	static Logger logger = LoggerFactory.getLogger(ConfigReader.class);
	public static void setUp(String propertyFilePath)
	{
		try
		{
			PropertyFileParser propertFileParser = new PropertyFileParser(propertyFilePath);
			logger.debug("Reading ClusterConfigurationFile Parameter from "+propertyFilePath);
			NodeConfigReader.readAndSetUp(propertFileParser.getValue("ClusterConfigurationFile"));
			logger.debug("Reading PersistanceFileLocation Parameter from "+propertyFilePath);
			Persistor.setUp(propertFileParser.getValue("PersistanceFileLocation"));
			logger.debug("Reading MessageSizeLimit Parameter from "+propertyFilePath);
			GlobalConfiguration.setMessageLimit(Integer.parseInt(propertFileParser.getValue("MessageSizeLimit")));
			logger.debug("Reading ClientMessageDir Parameter from "+propertyFilePath);
			GlobalConfiguration.setClientMessageStorageDir(propertFileParser.getValue("ClientMessageDir"));
			logger.debug("Reading NoOfRequestWorkerThread Parameter from "+propertyFilePath);
			GlobalConfiguration.setNoRequestWorkerThreads(Integer.parseInt(propertFileParser.getValue("NoOfRequestWorkerThread")));
			logger.debug("Reading NoOfNodeDiscoveryWorkerThread Parameter from "+propertyFilePath);
			GlobalConfiguration.setNoNodeDiscoverWorkerThreads(Integer.parseInt(propertFileParser.getValue("NoOfNodeDiscoveryWorkerThread")));
			logger.debug("Reading NoClientMsgWorkerThreads Parameter from "+propertyFilePath);
			GlobalConfiguration.setNoClientMsgWorkerThreads(Integer.parseInt(propertFileParser.getValue("NoClientMsgWorkerThreads")));
			logger.debug("Reading NoRequestVoteResultListenerThreads Parameter from "+propertyFilePath);
			GlobalConfiguration.setNoRequestVoteResultListenerThreads(Integer.parseInt(propertFileParser.getValue("NoRequestVoteResultListenerThreads")));
			logger.debug("Reading NoRequestVoteResultListenerThreads Parameter from "+propertyFilePath);
			GlobalConfiguration.setNoRequestVoteResultListenerThreads(Integer.parseInt(propertFileParser.getValue("NoRequestVoteResultListenerThreads")));
			logger.debug("Reading NoLogAppendListener Parameter from "+propertyFilePath);
			GlobalConfiguration.setNoLogAppendListener(Integer.parseInt(propertFileParser.getValue("NoLogAppendListener")));
			logger.debug("Reading NoLogAppendResultListener Parameter from "+propertyFilePath);
			GlobalConfiguration.setNoLogAppendResultListener(Integer.parseInt(propertFileParser.getValue("NoLogAppendResultListener")));
		}
		catch (Exception e)
		{
			logger.error("Error while reading configuration file.");
			logger.error(e.toString());
			e.printStackTrace();
	
		}
	
	}
}
