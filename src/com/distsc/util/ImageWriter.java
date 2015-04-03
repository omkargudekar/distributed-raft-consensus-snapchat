package com.distsc.util;

import java.io.FileOutputStream;
import java.io.IOException;

import com.distsc.app.GlobalConfiguration;
import com.distsc.comm.protobuf.NodeMessageProto.Message;

public class ImageWriter
{

	public void storeImage(Message message)
	{
		FileOutputStream fileOuputStream = null;
		try
		{
			byte[] bFile = message.getImageBits().toByteArray();
			fileOuputStream = new FileOutputStream(GlobalConfiguration.getClientMessageStorageDir() + message.getFileName());
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