package com.mds.serv;

import java.awt.Component;

import javax.media.Player;

public class Run {
	
	private SwingCapture sc = null;
	private Player p = null;
	
	public Run()
	{
		sc = new SwingCapture();
	}
	
	public void grabFrame()
	{
		p = sc.getPlayer();
//		sc.
	}
	
	public static void main(String[] args) {
		
		SwingCapture sc = new SwingCapture();
		Player p = sc.getPlayer();
		Component c = null;
		try {
			Thread.sleep(10000);
			c = p.getVisualComponent();
			sc.imageCapture();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
