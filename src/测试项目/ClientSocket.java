package 测试项目;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientSocket
{

	private String ip;

	private int port;

	private Socket socket = null;

	DataOutputStream dos = null;

	DataInputStream getMessageStream = null;

	public ClientSocket(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}

	/** 
	 * 创建socket连接 
	 *  
	 * @throws Exception 
	 *             exception 
	 */
	public void CreateConnection() throws Exception
	{

		try
		{
			socket = new Socket(ip, port);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (socket != null)
				socket.close();
			throw e;
		}
		finally
		{
		}
	}

	// 发送消息  
	public void sendMessage(String sendMessage) throws Exception
	{
		try
		{
			dos = new DataOutputStream(socket.getOutputStream());
			if (sendMessage.equals("Windows"))
			{
				dos.writeByte(0x1);
				dos.flush();
				return;
			}
			if (sendMessage.equals("Unix"))
			{
				dos.writeByte(0x2);
				dos.flush();
				return;
			}
			if (sendMessage.equals("Linux"))
			{
				dos.writeByte(0x3);
				dos.flush();
			}
			else
			{
				dos.writeUTF(sendMessage);
				dos.flush();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (dos != null)
				dos.close();
			throw e;
		}
		finally
		{
		}
	}

	// 接受消息  
	public DataInputStream getMessageStream() throws Exception
	{
		try
		{
			getMessageStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			return getMessageStream;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (getMessageStream != null)
				getMessageStream.close();
			throw e;
		}
		finally
		{
		}
	}

	// 关闭连接  
	public void shutDownConnection()
	{
		try
		{
			if (dos != null)
				dos.close();
			if (getMessageStream != null)
				getMessageStream.close();
			if (socket != null)
				socket.close();
		}
		catch (Exception e)
		{
		}
	}
}