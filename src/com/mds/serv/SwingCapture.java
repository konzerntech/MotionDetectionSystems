package com.mds.serv;

import javax.swing.*;
//import javax.swing.event.*;
import java.io.*;

import javax.media.*;
import javax.media.format.*;
import javax.media.util.*;
import javax.media.control.*;
//import javax.media.protocol.*;
//import java.util.*;
import java.awt.*;
import java.awt.image.*;
import com.sun.image.codec.jpeg.*;
 
public class SwingCapture
{
	public static Player p = null;
	public CaptureDeviceInfo di = null;
	public MediaLocator ml = null;
	public JButton capture = null;
	public Buffer buf = null;
	public Image img = null;
	public VideoFormat vf = null;
	public BufferToImage btoi = null;
	public JPanel panel = null;

  
	public JPanel getPlayerPanel() 
	{
		if(panel == null)
		{
			panel = new JPanel();
			panel.setBounds(10, 10, 400, 300);
			panel.setLayout(null);
			Component comp;
			p = getPlayer();
			if ((comp = p.getVisualComponent()) != null)
			{
//				comp.setBounds(120, 100, 400, 300);
				comp.setSize(400,300);
				panel.add(comp);
			}
			

		}
		return panel;
 	}
 
	public Player getPlayer()
	{
		Player player = null;
		String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
		di = CaptureDeviceManager.getDevice(str2);
		ml = di.getLocator();
		try {
			player = Manager.createRealizedPlayer(ml);
			player.start();
		} catch (NoPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotRealizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return player;

	}
  
  
	
	public static void playerclose() 
	{
		p.close();
		p.deallocate();
	}
	
	public Image imageCapture()
	{
		// Grab a frame
		FrameGrabbingControl fgc = (FrameGrabbingControl)
		p.getControl("javax.media.control.FrameGrabbingControl");
		buf = fgc.grabFrame();
  
		// Convert it to an image
		btoi = new BufferToImage((VideoFormat)buf.getFormat());
		img = btoi.createImage(buf);
  
		return img;
		
	}
  
 
	public static void saveJPG(Image img, String s)
	{
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, null, null);
		
		FileOutputStream out = null;
		try
		{ 
			out = new FileOutputStream(s); 
		}
		catch (java.io.FileNotFoundException io)
		{ 
			System.out.println("File Not Found"); 
		}
    
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		param.setQuality(0.5f,false);
		encoder.setJPEGEncodeParam(param);
    
		try 
		{ 
			encoder.encode(bi); 
			out.close(); 
		}
		catch (java.io.IOException io) 
		{
			System.out.println("IOException"); 
		}
	}
  
}

