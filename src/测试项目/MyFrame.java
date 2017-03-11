package 测试项目;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MyFrame extends JFrame
{

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JPanel panel;
	private JButton button1;
	private JLabel label;

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
					frame.setTitle("浩良的文件上传器");
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
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 100, 200, 67, 0 };
		gbl_contentPane.rowHeights = new int[] { 224, 31, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 113, 113, 0 };
		gbl_panel.rowHeights = new int[] { 27, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		JButton button = new JButton("\u9009\u62E9\u6587\u4EF6");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridwidth = 2;
		gbc_button.fill = GridBagConstraints.VERTICAL;
		gbc_button.gridx = 0;
		gbc_button.gridy = 0;
		panel.add(button, gbc_button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					@SuppressWarnings("resource")
					JFileChooser fd = new JFileChooser();
					fd.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fd.setMultiSelectionEnabled(true);
					fd.showOpenDialog(null);
					File[] files = fd.getSelectedFiles();
					for (File f : files)
					{
						Client client = new Client(); // 启动客户端连接
						if (f != null)
						{
							new Thread(new Runnable()
							{

								@Override
								public void run()
								{
									synchronized (files)
									{
										try
										{
											client.sendFile(f, progressBar);
										}
										catch (Exception e)
										{
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										} // 传输文件
									}
								}
							}).start();
						}
					}
					//					File f = fd.getSelectedFile();

				}
				catch (Exception e1)
				{
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});

		button1 = new JButton("\u9009\u62E9\u6587\u4EF6\u5939");
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
			}
		});
		GridBagConstraints gbc_button1 = new GridBagConstraints();
		gbc_button1.gridwidth = 2;
		gbc_button1.fill = GridBagConstraints.VERTICAL;
		gbc_button1.gridx = 0;
		gbc_button1.gridy = 2;
		panel.add(button1, gbc_button1);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("宋体", Font.PLAIN, 24));
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridwidth = 2;
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.anchor = GridBagConstraints.NORTH;
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 1;
		contentPane.add(progressBar, gbc_progressBar);

		label = new JLabel("-/-");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		contentPane.add(label, gbc_label);

	}

}
