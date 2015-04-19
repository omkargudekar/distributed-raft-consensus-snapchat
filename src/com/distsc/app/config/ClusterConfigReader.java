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
		
		
		logger.info("Reading ClusterConfiguration File "+filepath);
		FileReader fileReader =null;
		JSONParser jsonParser=null;
		JSONObject jsonObject=null;
		ArrayList<ClusterNode> peerClusters=new ArrayList<ClusterNode> ();
		try
		{
			fileReader = new FileReader(filepath);
			jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(fileReader);
			System.out.println(jsonObject);

			
			logger.info("Reading Adjacent Cluster Information"+filepath);
			JSONArray nodeArray= (JSONArray)  jsonObject.get("clusters");
			JSONObject nodeDetails;
			ClusterNode node=null;
			for(int counter=0;counter<nodeArray.size();counter++)
			{
				nodeDetails=(JSONObject) nodeArray.get(counter);
				node=new ClusterNode();
				node.setClusterID(nodeDetails.get("clusterName").toString());
				node.setNodeID(nodeDetails.get("nodeId").toString());
				node.setNodeIP(nodeDetails.get("host").toString());
				node.setNodePort(Integer.parseInt(nodeDetails.get("port").toString()));
				logger.info(node.toString());
				peerClusters.add(node);
				
			}
			ClusterConfiguration.setInterClusterNodes(peerClusters);
	
			
		}
		catch(Exception e)
		{	
			logger.error(e.toString());
			e.printStackTrace();
		}
	}

}
