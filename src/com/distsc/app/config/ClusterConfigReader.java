package com.distsc.app.config;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.distsc.beans.Node;

public class ClusterConfigReader
{
	
	
	public static void readAndSetUp(String filepath)
	{
		FileReader fileReader =null;
		JSONParser jsonParser=null;
		JSONObject jsonObject=null;
		ArrayList<Node> peers=new ArrayList<Node> ();
		try
		{
			fileReader = new FileReader(filepath);
			jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(fileReader);
			System.out.println(jsonObject);

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
			
			
		}
		catch(Exception e)
		{	
			System.out.println(e);
			
		}
		

	}

}
