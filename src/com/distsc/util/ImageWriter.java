package com.distsc.util;

import java.io.FileOutputStream;
import java.io.IOException;

import com.distsc.app.config.GlobalConfiguration;
import com.distsc.comm.protobuf.MessageProto.Request;

public class ImageWriter
{
	
	

	public void storeImage(Request message)
	{
		FileOutputStream fileOuputStream = null;
		try
		{
			byte[] bFile = message.getPayload().getClientMessage().getSenderMsgImageBytes().toByteArray();
			fileOuputStream = new FileOutputStream(GlobalConfiguration.getClientMessageStorageDir() + message.getPayload().getClientMessage().getSenderMsgImageName());
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
