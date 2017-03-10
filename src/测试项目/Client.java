package 测试项目;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JProgressBar;

public class Client extends Socket
{

	private static final String SERVER_IP = "www.davidzhao.cn"; // 服务端IP
	private static final int SERVER_PORT = 8899; // 服务端端口

	private Socket client;

	private FileInputStream fis;

	private DataOutputStream dos;

	/**
	 * 构造函数<br/>
	 * 与服务器建立连接
	 * 
	 * @throws Exception
	 */
	public Client() throws Exception
	{
		super(SERVER_IP, SERVER_PORT);
		this.client = Client.this;
		System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
	}

	/**
	 * 向服务端传输文件
	 * 
	 * @throws Exception
	 */
	public void sendFile(File file,JProgressBar progressBar) throws Exception
	{
//		progressBar = new JProgressBar();
		try
		{
//			File file = new File(path);
			if (file.exists())
			{
				fis = new FileInputStream(file);
				dos = new DataOutputStream(client.getOutputStream());

				// 文件名和长度
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();

				// 开始传输文件
//				System.out.println("======== 开始传输文件 ========");
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1)
				{
					dos.write(bytes, 0, length);
					dos.flush();
					progress += length;
					final long final_progress = progress;
					long fileLength = file.length();
					int x = (int) (100 * progress / file.length());
//					System.out.println(x);
//					progressBar.setStringPainted(true);
					
					new Thread(new Runnable()
					{
						
						@Override
						public void run()
						{
							progressBar.setValue(x);
							progressBar.setString((int)(100 * final_progress / fileLength) + "%");
							if (final_progress==fileLength)
							{
								progressBar.setString("文件："+file.getName()+"传输成功");
							}
						}
					}).start();
					
//					System.out.print("| " + (100 * progress / file.length()) + "% |\n");
				}
//				System.out.println("======== 文件传输成功 ========");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fis != null)
				fis.close();
			if (dos != null)
				dos.close();
			client.close();
		}
	}

	/**
	 * 入口
	 * 
	 * @param args
	 */
//	public static void main(String[] args)
//	{
//		try
//		{
//			Client client = new Client(); // 启动客户端连接
//			client.sendFile(); // 传输文件
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
}