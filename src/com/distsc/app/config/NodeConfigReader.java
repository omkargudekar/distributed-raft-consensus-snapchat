package com.distsc.app.config;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.distsc.beans.Node;

public class NodeConfigReader
{
	
	static Logger logger = LoggerFactory.getLogger(NodeConfigReader.class);

	public static void readAndSetUp(String filepath)
	{
		
		
		logger.info("Reading NodeConfiguration File "+filepath);
		FileReader fileReader =null;
		JSONParser jsonParser=null;
		JSONObject jsonObject=null;
		ArrayList<Node> peers=new ArrayList<Node> ();
		try
		{
			fileReader = new FileReader(filepath);
			jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(fileReader);
			logger.info(jsonObject.toString());

			
			logger.info("Reading Adjacent Nodes Information"+filepath);
			JSONObject nodes = (JSONObject) jsonObject.get("nodes");
			JSONArray nodeArray= (JSONArray)  nodes.get("node");
			JSONObject nodeDetails;
			Node node=null;
			for(int counter=0;counter<nodeArray.size();counter++)
			{
				nodeDetails=(JSONObject) nodeArray.get(counter);
				node=new Node();
				node.setNodeID(nodeDetails.get("id").toString());
				node.setNodeIP(nodeDetails.get("host").toString());
				node.setNodePort(Integer.parseInt(nodeDetails.get("port").toString()));
				peers.add(node);
				
			}
			GlobalConfiguration.setNodes(peers);
			
			
			logger.info("Reading Current Node Information"+filepath);
			nodes = (JSONObject) jsonObject.get("currentNode");
			nodes = (JSONObject) jsonObject.get("currentNode");
			node=new Node();
			node.setNodeID(nodes.get("id").toString());
			node.setNodeIP(nodes.get("host").toString());
			node.setNodePort(Integer.parseInt(nodes.get("port").toString()));
			GlobalConfiguration.setCurrentNode(node);
			logger.info("NodeConfiguration Read Complete "+filepath);

			
		}
		catch(Exception e)
		{	
			logger.error("Error while reading node configuration file.Terminating Program");
			e.printStackTrace();
			System.exit(0);

		}
	}

}
