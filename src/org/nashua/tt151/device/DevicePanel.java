package org.nashua.tt151.device;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;

import javax.swing.JPanel;

import org.nashua.tt151.RoboRIO;
import org.nashua.tt151.RoboRIO.DeviceType;

public class DevicePanel extends JPanel {
	private DeviceType displayType;
	private int numDevices;
	
	public DevicePanel( DeviceType type, int maxSlots ) {
		this.displayType = type;
		this.numDevices = maxSlots;
		setBackground( Color.GRAY.darker().darker().darker().darker() );
		setPreferredSize( new Dimension( Device.WIDTH, Device.HEIGHT * numDevices ) );
		setSize( getPreferredSize() );
	}
	
	public void paintComponent( Graphics g ) {
		g.setColor( getBackground() );
		g.fillRect( 0, 0, getWidth(), getHeight() );
		RoboRIO r = RoboRIO.getInstance();
		int y = 0;
		for ( int i = 0; i < numDevices; i++ ) {
			r.getDevice( displayType, i ).paintComponent( g.create( 0, y, Device.WIDTH, Device.HEIGHT ) );
			y += Device.HEIGHT;
		}
		
		Graphics2D g2d = (Graphics2D) g;
		Paint oldPaint = g2d.getPaint();
		g2d.setPaint( new LinearGradientPaint( 0, 0, getWidth(), 0, new float[] { 0.0f, 0.5f, 1.0f }, new Color[] { getBackground(), Color.WHITE, getBackground() } ) );
		y = 0;
		for ( int i = 0; i < numDevices - 1; i++ ) {
			y += Device.HEIGHT;
			g2d.drawLine( 0, y, getWidth(), y );
		}
		g2d.setPaint( oldPaint );
	}
	
}
