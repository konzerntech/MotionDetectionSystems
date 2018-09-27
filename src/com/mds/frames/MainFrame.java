package com.mds.frames;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
//import javax.swing.JDirectoryChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mds.serv.AePlayWave;
import com.mds.serv.AlarmSound;
import com.mds.serv.ImageCompare;
import com.mds.serv.MDSMailer;
import com.mds.serv.SwingCapture;

public class MainFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
//	private JPanel jContentPane = null;
	private java.awt.Container c = null;
	private com.mds.serv.SwingCapture sc = null;
	private JPanel videoPanel = null;
	private Image refImg = null;
	private Image currentImg = null;
	private File saveImgDir = null;
	private ImageCompare imc = null;
	private AlarmSound as = null;
	private static boolean alarm = true;
	private AePlayWave playWave = null;
	private Thread t2 = null;
	private Thread t1 = null;
	private JButton captureButton = null;
	private static int num = 0;
	private File saveCaptureDir = null;
	private Thread t3 = null;
	private JButton imgSet = null;
	private JButton start = null;
	private JFileChooser jf = null;
//	private JDirectoryChooser jd = null;
	private Thread mainThread = null;
	private JButton stop = null;
	
	
	
	

	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(900, 550);
		this.setContentPane(getContainer());
		this.setTitle("Motion Detection System");
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
//				playerclose();
				System.exit(0);
			}
		});
		
	}
	
	private Container getContainer()
	{
		c = new Container();
		
		imgSet = new JButton();
		imgSet.setText("Set Reference Image");
		imgSet.setBounds(680,180,200,25);
		imgSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				videoPanel.setEnabled(true);
				refImg = sc.imageCapture();
//				sc.saveJPG(refImg, s)
			}
		});
		c.add(imgSet);
		
		jf = new JFileChooser();
		start = new JButton("Start Detection");
		start.setBounds(680,230,200,25);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				saveImgDir = chooseSaveImgDir().getParentFile();
				if(refImg!=null)
				{
					if(mainThread == null)
					{
						startNewThread();
//						System.out.println("MainThread Started");
					}
					else if(mainThread.isAlive())
						mainThread.resume();
				}
				else
					showDialog("Set Reference Image");
				
			}
		});
		c.add(start);
		
		stop = new JButton("Stop");
		stop.setBounds(680,280,200,25);
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try {
					
//					if(mainThread == null)
					if(mainThread.isAlive())
						mainThread.suspend();
					
				} catch(NullPointerException e1) {
					showDialog("Detection Not Started Yet");
				}
				
			}
		});
		c.add(stop);
		
		sc = new SwingCapture();
		videoPanel = sc.getPlayerPanel();
		videoPanel.setEnabled(false);
		c.add(videoPanel);

		playWave = new AePlayWave("alarm.wav");
		t2 = new Thread(playWave);
			
		
		try {
			Thread.sleep(10000);
//			refImg = sc.imageCapture();
//			sc.saveJPG(currentImg, "D:\\Backups\\Saved\\img.jpg");
//			sc.saveJPG(refImg, "c:\\RefImg.jpg");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		captureButton = new JButton();
		captureButton.setText("Capture Frame");
		captureButton.setBounds(680,330,200,25);
		captureButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					saveCaptureDir = chooseSaveImgDir();
					System.out.println(saveCaptureDir.getAbsolutePath()+saveCaptureDir.getName());
//					if(!saveCaptureDir.exists())
//						saveCaptureDir.mkdirs();
					SwingCapture.saveJPG(sc.imageCapture(),saveCaptureDir.getAbsolutePath());
					num++;
				}
			
		});
		c.add(captureButton);		
		return c;
		
	}
	
	private void startNewThread()
	{
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	private void showDialog(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}
	
	private File chooseSaveImgDir()
	{
		File file = null;
		if(jf.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) 
		{
			file = jf.getSelectedFile();
//			System.out.println(saveImgDir.getName());
		}
		return file;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i =0;
//		saveImgDir = new File("D:\\Backups\\Saved");
		if(saveImgDir==null)
		{
			chooseSaveImgDir();
		}
//		if(!saveImgDir.exists())
//			saveImgDir.mkdirs();
		while(true)
		{
			try {
//				Thread.sleep(250);
				currentImg = sc.imageCapture();
				imc = new ImageCompare(refImg,currentImg);
//				t3 = new Thread(imc);
//				t3.run();
				if(!imc.compare())
				{
					
					System.out.println("hi");
					t2.run();
					SwingCapture.saveJPG(currentImg, saveImgDir+"\\img"+i+".jpg");
					MDSMailer m = new MDSMailer(saveImgDir+"\\img"+i+".jpg");
					t3 = new Thread(m);
					t3.start();
					refImg = currentImg;
					

				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}

	
	public static void main(String[] args) {
		
		MainFrame m = new MainFrame();
		m.setVisible(true);
//		Thread t = new Thread(m);
//		t.start();
	}
	

}
