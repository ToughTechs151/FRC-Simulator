package org.nashua.tt151.device;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;

import javax.swing.JPanel;

import org.nashua.tt151.RoboRIO;
import org.nashua.tt151.RoboRIO.DeviceType;

public abstract class Device extends JPanel {
	protected final int channel;
	public static final int WIDTH = 100;
	public static final int HEIGHT = 60;
	protected static final int STATUS_WIDTH = WIDTH / 6;
	protected String name = "";
	protected float drawFontSize = 22.0f;
	
	public Device( int channel, DeviceType type ) {
		this.channel = channel;
		setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
		setSize( getPreferredSize() );
		setBackground( Color.GRAY.darker().darker() );
		tryChannelRequest( type );
	}
	
	public int getChannel() {
		return channel;
	}
	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	protected void tryChannelRequest( DeviceType type ) {
		if ( !RoboRIO.getInstance().requestDeviceChannel( this, type ) ) {
			System.err.println( type.getName() + " channel " + channel + " unavailable, Device will not work!" );
		}
	}
	
	public void paintComponent(Graphics g) {
		drawBG( g );
		drawValue( g );
		drawChannel( g );
		drawName( g );
		drawStatusLight( g );
	}
	
	protected void drawBG(Graphics g) {
		g.setColor( getBackground() );
		g.fillRect( 0, 0, getWidth(), getHeight() );
	}
	
	protected abstract String getDrawValue();
	
	protected void drawValue(Graphics g) {
		g.setColor( Color.WHITE );
		Font oldFont = g.getFont();
		g.setFont( g.getFont().deriveFont( drawFontSize ) );
		FontMetrics fm = g.getFontMetrics();
		String val = getDrawValue();
		g.drawString( val, ( getWidth() + STATUS_WIDTH ) / 2 - fm.stringWidth( val ) / 2, getHeight() / 2 + fm.getAscent() / 2 );
		g.setFont( oldFont );
	}
	
	protected void drawChannel( Graphics g ) {
		g.setColor( Color.WHITE );
		FontMetrics fm = g.getFontMetrics();
		String s = "" + channel;
		g.drawString( s, WIDTH - fm.stringWidth( s ), fm.getAscent() );
	}
	
	protected void drawName( Graphics g ) {
		g.setColor( Color.WHITE );
		FontMetrics fm = g.getFontMetrics();
		g.drawString( name, STATUS_WIDTH, fm.getAscent() );
	}

	protected abstract Color selectStatusColor();
	
	protected void drawStatusLight( Graphics g ) {
		g.setColor( Color.GRAY.darker() );
		g.fillRect( 0, 0, STATUS_WIDTH, getHeight() );
		Graphics2D g2d = (Graphics2D) g;
		Paint oldPaint = g2d.getPaint();
		g2d.setPaint( new LinearGradientPaint( 0, 0, 0, getHeight(), new float[] { 0.0f, 0.5f, 1.0f }, new Color[] { g.getColor(), selectStatusColor(), g.getColor() } ) );
		g2d.fillRect( 0, 0, STATUS_WIDTH, getHeight() );
		g2d.setPaint( oldPaint );
	}
	
	public abstract void zero();
}
