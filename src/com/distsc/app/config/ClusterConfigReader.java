package com.distsc.app.config;

import java.io.FileReader;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distsc.intercluster.server.ClusterNode;

public class ClusterConfigReader
{

	static Logger logger = LoggerFactory.getLogger(ClusterConfigReader.class);

	public static void readAndSetUp(String filepath)
	{

		logger.info("Reading ClusterConfiguration File " + filepath);
		FileReader fileReader = null;
		JSONParser jsonParser = null;
		JSONObject jsonObject = null;
		ArrayList<ClusterNode> peerClusters = new ArrayList<ClusterNode>();
		try
		{
			fileReader = new FileReader(filepath);
			jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(fileReader);
			logger.info(jsonObject.toString());

			logger.info("Reading Adjacent Cluster Information" + filepath);
			JSONObject clusters = (JSONObject) jsonObject.get("clusters");
			Object[] clusterKeySet=clusters.keySet().toArray();
			ClusterNode node = null;
			
			for (int clusterCount = 0; clusterCount < clusters.size(); clusterCount++)
			{
				node=new ClusterNode();
				node.setClusterID(Integer.toString(clusterCount));	
				JSONObject clusterObj=(JSONObject) clusters.get(clusterKeySet[0].toString());
				node.setClusterName(clusterObj.get("clusterName").toString());
		

				JSONArray nodeArray = (JSONArray) clusterObj.get("clusterNodes");
				JSONObject nodeDetails;
				for (int counter = 0; counter < nodeArray.size(); counter++)
				{
					nodeDetails = (JSONObject) nodeArray.get(counter);
					node.setNodeID(nodeDetails.get("nodeId").toString());
					node.setNodeIP(nodeDetails.get("host").toString());
					node.setNodePort(Integer.parseInt(nodeDetails.get("port").toString()));
					logger.info("InterCluster Node Object Added"+node.toString());
					peerClusters.add(node);

				}
			}
			ClusterConfiguration.setInterClusterNodes(peerClusters);

		}
		catch (Exception e)
		{
			logger.error(e.toString());
			logger.error("Error while reading cluster configuration file.Terminating Program");
			e.printStackTrace();
		}
	}

}
