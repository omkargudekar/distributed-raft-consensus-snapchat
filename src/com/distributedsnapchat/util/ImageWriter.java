package com.distributedsnapchat.util;

import java.io.FileOutputStream;
import java.io.IOException;

import com.distributedsnapchat.app.GlobalConfiguration;

import com.distributedsnapchat.communication.protobuf.NodeMessageProto;

public class ImageWriter
{

	public void storeImage(NodeMessageProto.ClientMessage message)
	{
		FileOutputStream fileOuputStream = null;
		try
		{
			byte[] bFile = message.getImageBits().toByteArray();
			fileOuputStream = new FileOutputStream(GlobalConfiguration.getClientMessageStorageDir() + message.getFileName() + ".png");
			fileOuputStream.write(bFile);

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				fileOuputStream.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
