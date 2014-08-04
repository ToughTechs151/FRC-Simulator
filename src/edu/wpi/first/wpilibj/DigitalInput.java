package edu.wpi.first.wpilibj;

import java.awt.Color;

public class DigitalInput extends DIODevice {
	private volatile boolean state;
	private static final Color ON = Color.GREEN;
	private static final Color OFF = Color.RED;
	
	public DigitalInput( int channel ) {
		super( channel );
		drawFontSize = 30.0f;
	}
	
	protected String getDrawValue() {
		return state ? "ON" : "OFF";
	}
	
	protected Color selectStatusColor() {
		return state ? ON : OFF;
	}
	
	public boolean get() {
		return state;
	}
	
	public void set( boolean state ) {
		this.state = state;
	}

	public void zero() {
		set( false );
	}
}
