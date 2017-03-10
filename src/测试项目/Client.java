package ������Ŀ;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JProgressBar;

public class Client extends Socket
{

	private static final String SERVER_IP = "www.davidzhao.cn"; // �����IP
	private static final int SERVER_PORT = 8899; // ����˶˿�

	private Socket client;

	private FileInputStream fis;

	private DataOutputStream dos;

	/**
	 * ���캯��<br/>
	 * ���������������
	 * 
	 * @throws Exception
	 */
	public Client() throws Exception
	{
		super(SERVER_IP, SERVER_PORT);
		this.client = Client.this;
		System.out.println("Cliect[port:" + client.getLocalPort() + "] �ɹ����ӷ����");
	}

	/**
	 * �����˴����ļ�
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

				// �ļ����ͳ���
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();

				// ��ʼ�����ļ�
//				System.out.println("======== ��ʼ�����ļ� ========");
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
								progressBar.setString("�ļ���"+file.getName()+"����ɹ�");
							}
						}
					}).start();
					
//					System.out.print("| " + (100 * progress / file.length()) + "% |\n");
				}
//				System.out.println("======== �ļ�����ɹ� ========");
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
	 * ���
	 * 
	 * @param args
	 */
//	public static void main(String[] args)
//	{
//		try
//		{
//			Client client = new Client(); // �����ͻ�������
//			client.sendFile(); // �����ļ�
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
}