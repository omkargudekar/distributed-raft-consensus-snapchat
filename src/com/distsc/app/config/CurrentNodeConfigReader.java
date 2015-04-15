package com.distsc.app.config;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.distsc.beans.Node;

public class CurrentNodeConfigReader
{
	
	
	public static void readAndSetUp(String filepath)
	{
		FileReader fileReader =null;
		JSONParser jsonParser=null;
		JSONObject jsonObject=null;
		Node currentNode=null;
		try
		{
			fileReader = new FileReader(filepath);
			jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(fileReader);
			System.out.println(jsonObject);

			JSONObject nodes = (JSONObject) jsonObject.get("currentNode");
			nodes = (JSONObject) jsonObject.get("currentNode");
			currentNode=new Node();
			currentNode.setNodeID(nodes.get("id").toString());
			currentNode.setNodeIP(nodes.get("host").toString());
			currentNode.setNodePort(Integer.parseInt(nodes.get("port").toString()));
			GlobalConfiguration.setCurrentNode(currentNode);
			
			
		}
		catch(Exception e)
		{	
			System.out.println(e);
			
		}
		

	}

}
