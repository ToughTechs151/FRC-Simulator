package org.nashua.tt151.device;

import java.awt.Color;
import java.awt.Graphics;

import org.nashua.tt151.RoboRIO.DeviceType;

public class BlankDevice extends Device {
	
	public BlankDevice( int channel ) {
		super( channel, null );
	}
	
	protected void tryChannelRequest( DeviceType type ) {}
	
	public void paintComponent( Graphics g ) {
		drawBG( g );
		drawChannel( g );
		drawStatusLight( g );
	}
	
	protected String getDrawValue() {
		return "";
	}
	
	protected Color selectStatusColor() {
		return Color.GRAY;
	}
	
	public void zero() {}
}
