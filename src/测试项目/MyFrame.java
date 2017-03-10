package ������Ŀ;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MyFrame extends JFrame
{

	private JPanel contentPane;
	private JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MyFrame frame = new MyFrame();
					frame.setTitle("�������ļ��ϴ���");
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame()
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("����", Font.PLAIN, 24));
		contentPane.add(progressBar, BorderLayout.SOUTH);
		JButton button = new JButton("\u9009\u62E9\u6587\u4EF6");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					@SuppressWarnings("resource")
					JFileChooser fd = new JFileChooser();
					//fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
					fd.showOpenDialog(null);
					Client client = new Client(); // �����ͻ�������
					File f = fd.getSelectedFile();
					if (f != null)
					{
						new Thread(new Runnable()
						{
							
							@Override
							public void run()
							{
								try
								{
									client.sendFile(f,progressBar);
								}
								catch (Exception e)
								{
									// TODO �Զ����ɵ� catch ��
									e.printStackTrace();
								} // �����ļ�
							}
						}).start();
					}
					
				}
				catch (Exception e1)
				{
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(button, BorderLayout.CENTER);

	}

}
